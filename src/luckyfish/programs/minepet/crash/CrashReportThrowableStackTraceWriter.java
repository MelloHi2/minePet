package luckyfish.programs.minepet.crash;

import luckyfish.programs.minepet.crash.v1_0R0.StackTraceWriter;

/**
 * 报错信息内存写入器
 */
public interface CrashReportThrowableStackTraceWriter {
	String toString();

	static StackTraceWriter getStackTraceWriter() {
		return new StackTraceWriter();
	}
}
