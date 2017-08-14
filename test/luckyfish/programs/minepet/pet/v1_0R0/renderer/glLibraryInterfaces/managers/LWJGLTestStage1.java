package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.LWJGLTestStage1:
 */
public class LWJGLTestStage1 {
	private GLFWWindow window;
	@Before
	public void setUp() throws Exception {
		window = new GLFWWindow(glfwWindow -> {
			glfwWindow.setGLContextProfile(GLFWContextProfileType.ANY_PROFILE);
			glfwWindow.setVisible(true);
			glfwWindow.setResizable(false);
			glfwWindow.setGLContextVersion(3, 3);
			return new GLFWWindowsInitInfo(300, 300);
		});
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void run() {
		while (!window.isWindowShouldClose()) {
			window.update();
		}
	}
}