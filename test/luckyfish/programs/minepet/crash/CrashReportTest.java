package luckyfish.programs.minepet.crash;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * luckyfish.programs.minepet.crash.CrashReportTest:
 */
public class CrashReportTest {
	private CrashReport crashReport0;
	private CrashReport crashReport1;
	@Before
	public void setUp() throws Exception {
		crashReport0 = new CrashReport(new Throwable(), "test!");
		crashReport1 = new CrashReport(new Throwable(new RuntimeException(new Exception())), "test! enclosing exceptions!");
	}

	@After
	public void tearDown() throws Exception {
		new CrashReportWriter().write(crashReport1);
	}

	@Test
	public void toStringTest() {
		System.out.println(crashReport0.toString());
		System.out.println(crashReport1.toString());
	}

}