package luckyfish.programs.minepet.crash;


import java.io.Serializable;
import java.lang.management.ManagementFactory;

/**
 * 崩溃报告
 */
public class CrashReport implements Serializable {
	private static final long serialVersionUID = 8416479846549L;
	private boolean isFatal;

	public Throwable getCause() {
		return cause;
	}

	private Throwable cause;
	private String description;


	public CrashReport(Throwable cause, String description) {
		this.cause = cause;
		this.description = description;
	}

	public CrashReport(Throwable cause, String description, boolean isFatal /*什么？大新闻吗？*/) {
		this(cause, description);
		this.isFatal = isFatal;
	}

	/**
	 * 用来生成可以写入的崩溃日志
	 * @return  崩溃日志字符串版
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("----------崩溃报告----------").append(System.getProperty("line.separator"));
		builder.append(System.getProperty("line.separator"));


		builder.append("// ").append(description).append(System.getProperty("line.separator"));
		builder.append(System.getProperty("line.separator"));

		CrashReportThrowableStackTraceWriter writer = CrashReportThrowableStackTraceWriter.getStackTraceWriter();
		cause.printStackTrace(writer);

		builder.append(writer.toString()).append(System.getProperty("line.separator"));

		builder.append(System.getProperty("line.separator"));
		builder.append(System.getProperty("line.separator"));
		builder.append("----------系统信息----------").append(System.getProperty("line.separator"));
		builder.append(System.getProperty("line.separator"));

		builder.append(String.format("操作系统：%s (%s) 版本：%s", System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version"))).append(System.getProperty("line.separator"));
		builder.append(String.format("Java信息：%s，%s", System.getProperty("java.version"), System.getProperty("java.vendor"))).append(System.getProperty("line.separator"));
		builder.append(String.format("Java虚拟机信息：%s（%s），%s", System.getProperty("java.vm.name"), System.getProperty("java.vm.info"), System.getProperty("java.vm.vendor"))).append(System.getProperty("line.separator"));

		// 内存信息
		Runtime runtime = Runtime.getRuntime();
		double maxMemory = runtime.maxMemory();
		double totalMemory = runtime.totalMemory();
		double freeMemory = runtime.freeMemory();

		double maxMemory_MB = maxMemory / 1024 / 1024;
		double totalMemory_MB = totalMemory / 1024 / 1024;
		double freeMemory_MB = freeMemory / 1024 / 1024;

		builder.append(String.format("已向系统申请%.2fMB内存，还剩%.2fMB已申请内存可用，最多可申请%.2fMB内存", totalMemory_MB, freeMemory_MB, maxMemory_MB)).append(System.getProperty("line.separator"));
		if (freeMemory <= 1024 * 512 && totalMemory - maxMemory <= 1024 * 512) {
			builder.append("嗯哼，看起来你的内存不够用诶，试试给予我更多内存看看？反正java程序不都是吃内存的嘛=w=").append(System.getProperty("line.separator"));
		}
		// 虚拟机参数
		String args = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", " ");
		builder.append(String.format("虚拟机参数：%s，总共有%d个参数", args, args.split(" ").length)).append(System.getProperty("line.separator"));

		return builder.toString();
	}

	public boolean isFatal() {
		return isFatal;
	}
}
