package luckyfish.programs.minepet.scheduler;

/**
 * 任务, 每个任务都需要实现这个方法
 */
public interface RunnableTask {
	void run() throws Throwable;
}
