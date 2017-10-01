package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.Renderer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.GLFWWindow;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.ResourceManager;
import luckyfish.programs.minepet.utils.math.Vector3D;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * luckyfish.programs.minepet.pet.v1_0R0.model.ModelTestStage1:
 */
public class ModelTestStage1 {
	private GLFWWindow window;
	private OpenGLInterface openGLInterface;

	private ModelBox head;
	private ModelBox headwear;
	@Before
	public void setUp() throws Exception {
		window = new GLFWWindow(w -> {
			w.setGLContextProfile(GLFWContextProfileType.CORE_PROFILE);
			w.setVisible(true);
			w.setResizable(false);
			w.setGLContextVersion(3, 3);
			return new GLFWWindowsInitInfo(300, 300);
		});

		openGLInterface = new OpenGLInterface(window, i -> {
			Shader shader = i.getShader();

			try {
				shader.createVertexShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/vertex.vsh")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				shader.createFragmentShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/fragment.fsh")));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				shader.link();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				shader.new Uniform("texture_sampler").setValueAsInteger(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Texture texture = Texture.getAuthorTexture(openGLInterface);

		head = new ModelBox("head", new Vector3D(8, 8, 8), new Location3D(0, 0f, 0), texture, new Vector3D(8, 8, 8), new Location2D(0, 0), openGLInterface);
		headwear = new ModelBox("head.wear", new Vector3D(9, 9, 9), new Location3D(0, 0, 0), texture, new Vector3D(8, 8, 8), new Location2D(32, 0), openGLInterface);
		headwear.setUseAlpha(true);
		head.addChild(headwear);
	}

	@Test
	public void testRender() {
		int i =0;
		while (!window.isWindowShouldClose()) {
			i ++;
			int finalI = i;
			openGLInterface.render(new Renderer() {
				@Override
				public void preRender() {
					openGLInterface.clearColor(new Color(0x66ccff));
					openGLInterface.clear();
					openGLInterface.getShader().bind();
				}

				@Override
				public void render() {
					head.draw();
					headwear.draw();
					head.setRotation(new Vector3D(-45, 30, 0));
				}

				@Override
				public void postRender() {
					openGLInterface.getShader().unbind();
				}
			});
		}
	}
}