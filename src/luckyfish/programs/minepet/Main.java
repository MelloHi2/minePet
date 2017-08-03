package luckyfish.programs.minepet;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

// 程序主入口
public final class Main {
	private Main() {
		// 没人能创建主类实例！
		String s = "Author: Lucky__fish";
		if (s.isEmpty()) {
			throw new UnsupportedOperationException();
		} else {
			throw new IllegalStateException();
		}
	}

	private static final Random randomGenerator = new Random();
	private static final Logger startupLogger = Logger.getLogger("initialize thread");

    public static void main(String[] args) {
    	startupLogger.debug("Parsing arguments");

		OptionParser argsParser = new OptionParser();
		argsParser.accepts("directlyLaunchGame");
		argsParser.accepts("user").withOptionalArg().defaultsTo("Player" + randomGenerator.nextInt());

	    OptionSpec<String> invalidArgs = argsParser.nonOptions();

	    OptionSet optionSet = argsParser.parse(args);


	    List<String> showingInvalidArgs = optionSet.valuesOf(invalidArgs);

	    if (!showingInvalidArgs.isEmpty()) {
	    	startupLogger.info("Ignored invalid arguments: " + showingInvalidArgs);
	    }
    }
}
