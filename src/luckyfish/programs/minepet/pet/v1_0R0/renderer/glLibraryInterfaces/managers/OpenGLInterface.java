package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.GLInitializer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.Renderer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * openGL接口
 */
public final class OpenGLInterface {
	private final GLFWWindow window;
	private final GLCapabilities glCapabilities;
	private final Shader shader;

	public OpenGLInterface(GLFWWindow window, GLInitializer glInitializer) throws Exception {
		this.window = window;
		glInitializer.preInit(this);

		glCapabilities = window.getGlCapabilities();
		shader = new Shader(this);

		glInitializer.init(this);


	}

	public void setGlCapabilities() {
		GL.setCapabilities(glCapabilities);
	}

	public void clearColor(Color color) {
		setGlCapabilities();
		glClearColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getGreen() / 255.0f, color.getAlpha() / 255.0f);
	}
	public void clear() {
		setGlCapabilities();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Renderer renderer) {
		renderer.preRender();
		renderer.render();

		window.update();
		renderer.postRender();
	}

	public void draw(int vertexCount) {
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
	}

	public Shader getShader() {
		return shader;
	}

	public void enable(EnableValues target) {
		setGlCapabilities();
		glEnable(target.getId());
	}
	public void disable(EnableValues target) {
		setGlCapabilities();
		glDisable(target.getId());
	}

	public void useTexture() {
		setGlCapabilities();
		glActiveTexture(GL_TEXTURE0);
	}

	public void setBlendFunction(BlendFunctions srcFunction, BlendFunctions destFunction) {
		setGlCapabilities();
		glBlendFunc(srcFunction.getId(), destFunction.getId());
	}

	public enum EnableValues {
		DEPTH_TEST(GL_DEPTH_TEST),
		TEXTURE(GL_TEXTURE_2D),
		ALPHA(GL_ALPHA_TEST),
		BLEND(GL_BLEND);

		private final int id;
		EnableValues(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
	public enum BlendFunctions {
		ONE_MINUS_SRC_ALPHA(GL_ONE_MINUS_SRC_ALPHA),
		SRC_ALPHA(GL_SRC_ALPHA),
		ONE_MINUS_DST_ALPHA(GL_ONE_MINUS_DST_ALPHA);

		private final int id;

		BlendFunctions(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
	}
}
