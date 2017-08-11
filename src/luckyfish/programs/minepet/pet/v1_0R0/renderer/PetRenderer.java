package luckyfish.programs.minepet.pet.v1_0R0.renderer;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.utils.RendererFailureException;

/**
 * 渲染引擎
 */
public class PetRenderer {

	private Shader shader;

	public PetRenderer() {
		try {
			shader = new Shader();


		} catch (Throwable t) {
			throw new RendererFailureException(t);
		}
	}
}
