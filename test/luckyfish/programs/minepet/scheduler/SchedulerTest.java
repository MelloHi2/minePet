package luckyfish.programs.minepet.scheduler;

import org.junit.Test;

public class SchedulerTest {
	@Test
	public void addTask() throws Exception {
		Scheduler scheduler = Scheduler.getScheduler(1);
		scheduler.addTask(() -> System.out.println(5));

		scheduler.addTask(() -> {
			System.out.println(10);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			System.out.println(10);
		}, true);
		scheduler.addTask(() -> {
			System.out.println(15);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			System.out.println(15);
		}, true, 1);
	}

}