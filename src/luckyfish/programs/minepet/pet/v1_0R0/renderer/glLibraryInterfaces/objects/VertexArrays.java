package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

/**
 * 顶点数组
 */
public class VertexArrays implements Object {
	private final int vertexArraysId;
	private final OpenGLInterface openGLInterface;

	public VertexArrays(OpenGLInterface openGLInterface) {
		this.openGLInterface = openGLInterface;
		openGLInterface.setGlCapabilities();
		vertexArraysId = glGenVertexArrays();
	}

	@Override
	public void bind() {
		openGLInterface.setGlCapabilities();
		glBindVertexArray(vertexArraysId);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
	}

	@Override
	public void unbind() {
		openGLInterface.setGlCapabilities();
		glBindVertexArray(0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}

	@Override
	public void finalize() throws Throwable {
		this.unbind();
		openGLInterface.setGlCapabilities();
		glDeleteVertexArrays(vertexArraysId);
	}
}
