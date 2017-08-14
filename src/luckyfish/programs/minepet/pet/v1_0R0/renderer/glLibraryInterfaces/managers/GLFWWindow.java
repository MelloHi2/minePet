package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.GLFWInitializer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * 宠物渲染窗口
 */
public final class GLFWWindow {
	private final GLCapabilities glCapabilities;
	private long windowHandle;

	public GLFWWindow(GLFWInitializer initializer) {
		glfwDefaultWindowHints();
		initializer.preInit(this);
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("We cannot initialize the GLFW library");
		}
		glfwDefaultWindowHints();
		GLFWWindowsInitInfo initInfo = initializer.init(this);

		windowHandle = glfwCreateWindow(initInfo.getWidth(), initInfo.getHeight(), this.hashCode() + "", NULL, NULL);
		if (windowHandle == NULL) {
			throw new IllegalStateException("We cannot create the window");
		}
		glfwMakeContextCurrent(windowHandle);
		glCapabilities = createCapabilities();

		initializer.postInit(this);
		glfwSwapInterval(1);
	}

	private void checkState(boolean mustBeforeInit) {
		if (mustBeforeInit && windowHandle != NULL) {
			throw new AssertionError();
		} else if (!mustBeforeInit && windowHandle == NULL) {
			throw new AssertionError();
		}
	}

	public void setResizable(boolean resizable) {
		checkState(true);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	}

	public void setVisible(boolean visible) {
		try {
			checkState(false);
			if (visible) {
				glfwShowWindow(windowHandle);
			} else {
				glfwHideWindow(windowHandle);
			}
		} catch (AssertionError error) {    // 好吧，我们并没有创建窗口
			glfwWindowHint(GLFW_VISIBLE, visible ? GLFW_TRUE : GLFW_FALSE);
		}
	}

	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}

	public void setGLContextVersion(int majorVersion, int minorVersion) {
		checkState(true);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, majorVersion);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minorVersion);
	}

	public void setGLContextProfile(GLFWContextProfileType type) {
		checkState(true);
		glfwWindowHint(GLFW_OPENGL_PROFILE, type.getGlfwOpenGLProfile());
	}

	public boolean isWindowShouldClose() {
		checkState(false);
		return glfwWindowShouldClose(windowHandle);
	}

	@Override
	protected void finalize() {
		if (windowHandle != NULL) {
			glfwDestroyWindow(windowHandle);
		}
		glfwTerminate();
	}

	GLCapabilities getGlCapabilities() {
		return glCapabilities;
	}
}
