package luckyfish.programs.minepet.scheduler;

import luckyfish.programs.minepet.scheduler.v1_0R0.SchedulerImpl;

/**
 * 调度器
 */
public interface Scheduler {
	/**
	 * 添加一个一次性任务
	 * @param target        要运行的玩意
	 * @param isAsync       是不是异步执行？
	 */
	void addTask(RunnableTask target, boolean isAsync);

	/**
	 * 添加一个一次性任务
	 * @param target        要执行的玩意
	 * @param isAsync       是不是异步执行？
	 * @param delay         什么时候开始执行？
	 */
	void addTask(RunnableTask target, boolean isAsync, int delay);

	/**
	 * 添加一个定时任务
	 * @param target        要执行的玩意
	 * @param isAsync       是不是异步执行？
	 * @param delay         什么时候开始执行？
	 * @param interval      每隔几刻运行一次？
	 */
	void addTask(RunnableTask target, boolean isAsync, int delay, int interval);

	/**
	 * 添加一个一次性同步任务
	 * @param target        要执行的玩意
	 */
	void addTask(RunnableTask target);

	/**
	 * 添加一个一次性同步任务
	 * @param target        要执行的玩意
	 * @param delay         什么时候开始执行？
	 */
	void addTask(RunnableTask target, int delay);

	/**
	 * 添加一个循环同步任务
	 * @param target        要执行的玩意
	 * @param delay         什么时候开始运行？
	 * @param interval      每隔几刻执行一次？
	 */
	void addTask(RunnableTask target, int delay, int interval);

	static Scheduler getScheduler(int tps) {
		return SchedulerImpl.getScheduler(tps);
	}
}
