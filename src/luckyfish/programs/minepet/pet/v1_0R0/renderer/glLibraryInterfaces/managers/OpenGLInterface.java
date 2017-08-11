package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.GLInitializer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.Renderer;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * openGL接口
 */
public final class OpenGLInterface {
	private final GLFWWindow window;

	public OpenGLInterface(GLFWWindow window, GLInitializer glInitializer) {
		this.window = window;
		glInitializer.preInit(this);

		glInitializer.init(this);
	}

	public void clearColor(Color color) {
		glClearColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getGreen() / 255.0f, color.getAlpha() / 255.0f);
	}

	public void render(Renderer renderer) {
		renderer.preRender();
		renderer.render();

		window.swapBufferAndPollEvents();
		renderer.postRender();
	}
}
