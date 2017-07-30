package luckyfish.programs.minepet.scheduler.v1_0R0;

import luckyfish.programs.minepet.scheduler.RunnableTask;
import luckyfish.programs.minepet.scheduler.Scheduler;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 调度器实现
 * 管理并运行每个任务
 */
public final class SchedulerImpl implements Scheduler, Runnable {
	private final Timer timer;                                          // 时间管理
	private final List<ScheduledTask> taskList = new LinkedList<>();    // 任务列表

	private static final Logger logger = Logger.getLogger("Pet scheduler");

	private SchedulerImpl(int targetTPS) {
		timer = new Timer(targetTPS);

		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	// 当有一个新任务的时候
	public void addTask(RunnableTask target, boolean isAsync) {
		this.addTask(target, isAsync, 0);
	}

	@Override
	public void addTask(RunnableTask target, boolean isAsync, int delay) {
		this.addTask(target, isAsync, delay, (int)Double.NaN);
	}

	@Override
	public void addTask(RunnableTask target, boolean isAsync, int delay, int interval) {
		synchronized (this.taskList) {
			taskList.add(new ScheduledTask(target, isAsync, delay, interval));
		}
	}

	@Override
	public void addTask(RunnableTask target) {
		this.addTask(target, false);
	}

	@Override
	public void addTask(RunnableTask target, int delay) {
		this.addTask(target, false, delay);
	}

	@Override
	public void addTask(RunnableTask target, int delay, int interval) {
		this.addTask(target, false, delay, interval);
	}


	@Override
	public final void run() {
		while (true) {
			if (timer.isTicked() || timer.getSkippedTick() != 0) {
				synchronized (this.taskList) {
					Iterator<ScheduledTask> taskIterator = taskList.iterator();
					while (taskIterator.hasNext()) {
						ScheduledTask task = taskIterator.next();
						if (task.getNextDelay() == 0) {
							Runnable taskToRun = () -> {
								try {
									task.getTarget().run();
								} catch (Throwable throwable) {
									logger.error("Error executing task! ", throwable);
								}
							};
							if (task.isAsync()) {
								Thread t = new Thread(taskToRun);
								t.start();
							} else {
								taskToRun.run();
							}
							if (Double.isNaN(task.interval)) {              // If it asked remove it after it ran
								taskIterator.remove();
							} else {
								task.setNextDelay(task.getInterval());
							}
						} else {
							task.setNextDelay(task.getNextDelay() - 1);
						}
					}
				}
			}
		}
	}

	// Timer
	// tick controlling scheduler
	private static final class Timer implements Runnable {
		private int targetTPS;
		private long lastTickedTime = System.currentTimeMillis();

		private int skippedTick = 0;
		private boolean isTicked = false;

		private boolean isTickRequested = false;

		Timer(int targetTPS) {
			this.targetTPS = targetTPS;
			Thread t = new Thread(this);
			t.setDaemon(true);  // we can't lag when exiting
			t.start();
		}
		@Override
		public final void run() {
			if (!isTickRequested && isTicked) {
				this.skippedTick ++;
			}
			isTickRequested = false;

			int tpsInterval = 1000 / targetTPS;
			while (true) {
				this.isTicked = System.currentTimeMillis() >= lastTickedTime + tpsInterval;
				try {
					Thread.sleep(1);
				} catch (InterruptedException ignored) {}
			}
		}

		final boolean isTicked() {
			this.isTickRequested = true;
			return isTicked;
		}
		final int getSkippedTick() {
			int skippedTick = this.skippedTick;
			this.skippedTick = 0;
			return skippedTick;
		}
	}
	// Task
	// the Task to run
	private static final class ScheduledTask {
		private final RunnableTask target;          // What can i do?
		private final boolean isAsync;              // Is me run as async thread?

		private final int delay;                    // When can i do after create?
		private final int interval;                 // When can i do next time?

		private int nextDelay;                      // When can i run?


		ScheduledTask(RunnableTask target, boolean isAsync, int delay, int interval) {
			this.target = target;
			this.isAsync = isAsync;
			this.delay = delay;
			this.interval = interval;
			this.nextDelay = delay;
		}

		RunnableTask getTarget() {
			return target;
		}

		boolean isAsync() {
			return isAsync;
		}

		int getInterval() {
			return interval;
		}

		int getNextDelay() {
			return nextDelay;
		}

		void setNextDelay(int nextDelay) {
			this.nextDelay = nextDelay;
		}

		public String toString() {
			return "Scheduled task:{" + "target=" + target.toString() + ",isAsync=" + isAsync + ",delay=" + delay + (Double.isNaN(interval) ? ",one-time-task=true" : ",interval=" + interval) + ",nextDelay=" + nextDelay + "}";
		}
	}

	private static final HashMap<Integer, Scheduler> schedulers = new HashMap<>();
	public static Scheduler getScheduler(int tps) {
		if (!schedulers.containsKey(tps)) {
			schedulers.put(tps, new SchedulerImpl(tps));
		}
		return schedulers.get(tps);
	}
}
