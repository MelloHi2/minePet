package luckyfish.programs.minepet.pet.v1_0R0.renderer;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.GLFWWindow;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.utils.RendererFailureException;
import luckyfish.programs.minepet.utils.ResourceManager;

import java.awt.*;

/**
 * 渲染引擎
 */
public class PetRenderer {

	private final Shader shader;

	private final OpenGLInterface openGLInterface;

	public PetRenderer() {
		try {
			openGLInterface = new OpenGLInterface(new GLFWWindow((window) -> {
				window.setGLContextVersion(3, 3);
				window.setResizable(false);
				window.setVisible(false);
				window.setGLContextProfile(GLFWContextProfileType.CORE_PROFILE);
				return new GLFWWindowsInitInfo(300, 300);
			}), (glInterface -> glInterface.clearColor(new Color(233, 233, 233))));

			shader = new Shader(openGLInterface);

			shader.createVertexShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/vertex.vs")));
			shader.createFragmentShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/fragment.fs")));

			shader.link();
		} catch (Throwable t) {
			throw new RendererFailureException(t);
		}
	}
}
