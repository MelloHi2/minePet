package luckyfish.programs.minepet.crash;

import luckyfish.programs.minepet.crash.v1_0R0.StackTraceWriter;

import java.io.*;

/**
 * 报错信息内存写入器
 */
public abstract class CrashReportThrowableStackTraceWriter extends PrintWriter {
	public CrashReportThrowableStackTraceWriter(Writer out) {
		super(out);
	}

	public CrashReportThrowableStackTraceWriter(Writer out, boolean autoFlush) {
		super(out, autoFlush);
	}

	public CrashReportThrowableStackTraceWriter(OutputStream out) {
		super(out);
	}

	public CrashReportThrowableStackTraceWriter(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
	}

	public CrashReportThrowableStackTraceWriter(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	public CrashReportThrowableStackTraceWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
	}

	public CrashReportThrowableStackTraceWriter(File file) throws FileNotFoundException {
		super(file);
	}

	public CrashReportThrowableStackTraceWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
	}

	public abstract String toString();

	static CrashReportThrowableStackTraceWriter getStackTraceWriter() {
		return new StackTraceWriter();
	}
}
