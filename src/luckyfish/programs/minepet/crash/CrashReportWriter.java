package luckyfish.programs.minepet.crash;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * 一次性崩溃日志写入器
 */
public class CrashReportWriter {
	private BufferedWriter writer;
	private boolean isWriterClosed = false;

	public CrashReportWriter() throws IOException {
		Calendar calendar = Calendar.getInstance();
		writer = new BufferedWriter(new FileWriter(String.format("crash-%d-%d-%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))));
	}

	public void write(CrashReport report) throws IOException {
		if (isWriterClosed) {
			return;
		}
		try {
			writer.write(report.toString());
			writer.flush();
		} finally {
			writer.close();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if (!isWriterClosed) {
			writer.close();
		}
	}
}
