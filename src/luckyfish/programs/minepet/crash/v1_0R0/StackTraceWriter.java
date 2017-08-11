package luckyfish.programs.minepet.crash.v1_0R0;

import luckyfish.programs.minepet.crash.CrashReportThrowableStackTraceWriter;

import java.io.*;

/**
 * 写入器实现
 */
public class StackTraceWriter extends PrintWriter implements CrashReportThrowableStackTraceWriter {
	private static final WriterCheater cheater = new WriterCheater();
	public StackTraceWriter() {
		super(cheater);
	}

	public StackTraceWriter(Writer out) {
		super(out);
		throw new UnsupportedOperationException();
	}

	public StackTraceWriter(Writer out, boolean autoFlush) {
		super(out, autoFlush);
		throw new UnsupportedOperationException();
	}

	public StackTraceWriter(OutputStream out) {
		super(out);
		throw new UnsupportedOperationException();
	}

	public StackTraceWriter(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		throw new UnsupportedOperationException();
	}

	public StackTraceWriter(String fileName) throws FileNotFoundException {
		super(fileName);
		throw new UnsupportedOperationException();
	}

	public StackTraceWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		throw new UnsupportedOperationException();
	}

	public StackTraceWriter(File file) throws FileNotFoundException {
		super(file);
		throw new UnsupportedOperationException();
	}

	public StackTraceWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		String str = cheater.toString();
		try {
			cheater.close();
		} catch (IOException e) {       // 然而永远不会发生这个问题 :/
			e.printStackTrace();
		}
		return str;
	}
}
