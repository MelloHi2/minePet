package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils;

import static org.lwjgl.glfw.GLFW.*;

/**
 * luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType:
 */
public enum GLFWContextProfileType {
	CORE_PROFILE(GLFW_OPENGL_CORE_PROFILE),
	ANY_PROFILE(GLFW_OPENGL_ANY_PROFILE),
	COMPAT_PROFILE(GLFW_OPENGL_COMPAT_PROFILE);

	private final int glfwOpenGLProfile;

	GLFWContextProfileType(int glfwOpenGLProfile) {
		this.glfwOpenGLProfile = glfwOpenGLProfile;
	}

	public int getGlfwOpenGLProfile() {
		return glfwOpenGLProfile;
	}
}
