package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.GLFWWindow;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;

/**
 * 宠物渲染窗口初始化
 */
public interface GLFWInitializer {
	default void preInit(GLFWWindow glfwWindow) {} // We cannot directly access window before it's initialized

	GLFWWindowsInitInfo init(GLFWWindow glfwWindow);

	default void postInit(GLFWWindow glfwWindow) {}
}