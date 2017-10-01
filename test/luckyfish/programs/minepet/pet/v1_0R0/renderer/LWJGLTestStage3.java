package luckyfish.programs.minepet.pet.v1_0R0.renderer;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.Renderer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.GLFWWindow;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import luckyfish.programs.minepet.utils.ResourceManager;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * luckyfish.programs.minepet.pet.v1_0R0.renderer.LWJGLTestStage3:
 */
public class LWJGLTestStage3 {
	private GLFWWindow window;
	private OpenGLInterface openGLInterface;
	private Mesh mesh;
	private Shader shader;

	@Before
	public void setUp() throws Exception {
		window = new GLFWWindow(g -> {
			g.setGLContextProfile(GLFWContextProfileType.CORE_PROFILE);
			g.setVisible(true);
			g.setResizable(false);
			g.setGLContextVersion(3, 3);
			return new GLFWWindowsInitInfo(300, 300);
		});

		openGLInterface = new OpenGLInterface(window, g -> {
			// we can do nothing
		});

		shader = openGLInterface.getShader();

		shader.createVertexShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/vertex.vsh")));
		shader.createFragmentShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/fragment.fsh")));

		shader.link();

		mesh = new Mesh(new float[] {
				-0.5f, -0.5f, 0.0f,
				-0.5f, 0.5f, 0.0f,
				0.5f, 0.5f, 0.0f,
				0.5f, -0.5f, 0.0f
		}, new int[]{0, 1, 3, 3, 1, 2}, new float[]{
				0.5f, 0.0f, 0.0f,
				0.0f, 0.5f, 0.0f,
				0.0f, 0.0f, 0.5f,
				0.0f, 0.5f, 0.5f,
		}, new Texture(openGLInterface), openGLInterface);
	}

	@Test
	public void run() {
		while (!window.isWindowShouldClose()) {
			openGLInterface.render(new Renderer() {
				@Override
				public void preRender() {
					openGLInterface.clearColor(new Color(0x66ccff));
					openGLInterface.clear();
					shader.bind();
				}
				@Override
				public void render() {
					mesh.draw();
				}
				@Override
				public void postRender() {
					shader.unbind();
				}
			});
		}
	}
}