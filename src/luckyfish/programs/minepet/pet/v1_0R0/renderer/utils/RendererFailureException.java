package luckyfish.programs.minepet.pet.v1_0R0.renderer.utils;

/**
 * 当渲染器出现奇奇怪怪的意外时的情况
 */
public class RendererFailureException extends RuntimeException {
	public RendererFailureException(Throwable cause) {
		this.initCause(cause);
	}
}
