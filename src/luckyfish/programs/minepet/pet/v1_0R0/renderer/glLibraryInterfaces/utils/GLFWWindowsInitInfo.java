package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils;

/**
 * GLFW初始化返回的信息
 */
public class GLFWWindowsInitInfo {
	private final int width;
	private final int height;

	public GLFWWindowsInitInfo(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
