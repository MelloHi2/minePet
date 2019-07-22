package luckyfish.programs.minepet.utils.v1_0R0.debugger;

import luckyfish.programs.minepet.pet.v1_0R0.model.ModelPlayer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.Renderer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.GLFWWindow;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import luckyfish.programs.minepet.utils.ResourceManager;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class PetModellingDebuggerTest {
	private PetModellingDebugger debugger;
	private GLFWWindow window;
	private OpenGLInterface openGLInterface;
	private ModelPlayer model;

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

		openGLInterface.getCamera().setRotation(0, 45, 45);

		model = new ModelPlayer(texture, openGLInterface, true);
		debugger = new PetModellingDebugger(model);
		debugger.pack();
		new Thread(() -> debugger.setVisible(true)).start();
	}

	@Test
	public void render() throws Exception {
		int i = 0;
		while (!window.isWindowShouldClose()) {
			i ++;
			int finalI = i;
			openGLInterface.render(new Renderer() {
				@Override
				public void preRender() {
					openGLInterface.getShader().bind();
					openGLInterface.clearColor(new Color(0x66ccff));
					openGLInterface.clear();
				}
				@Override
				public void render() {
					model.render();
					model.tick();
					debugger.update();
//					model.setRotation(new Vector3D(finalI, finalI, 0));
				}

				@Override
				public void postRender() {
					openGLInterface.getShader().unbind();
				}
			});
		}
	}
}