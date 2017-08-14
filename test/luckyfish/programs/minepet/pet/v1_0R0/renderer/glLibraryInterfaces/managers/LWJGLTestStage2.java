package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.Renderer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.LWJGLTestStage2:
 */
public class LWJGLTestStage2 {
	private GLFWWindow window;
	private OpenGLInterface openGLInterface;

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
	}

	@Test
	public void run() {
		while (!window.isWindowShouldClose()) {
			openGLInterface.render(new Renderer() {
				@Override
				public void render() {
					openGLInterface.clearColor(new Color(0x66CCFF));
					openGLInterface.clear();
				}
			});
		}
	}
}