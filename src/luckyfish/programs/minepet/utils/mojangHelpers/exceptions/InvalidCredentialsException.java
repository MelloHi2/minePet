package luckyfish.programs.minepet.utils.mojangHelpers.exceptions;

/**
 * 无效的用户信息？
 */
public class InvalidCredentialsException extends Exception {
	public InvalidCredentialsException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
