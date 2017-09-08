package luckyfish.programs.minepet.utils.mojangHelpers.exceptions;

/**
 * 请求次数太多怎么办？
 * 我也不知道怎么办QwQ
 */
public class TooManyRequestsException extends Exception {
	public TooManyRequestsException() {
		super();
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
