package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * VBO
 */
public class VertexBufferObject implements Buffer {
	private final int VBOId;
	private final OpenGLInterface openGLInterface;
	private int verticesAmount;

	public VertexBufferObject(OpenGLInterface openGLInterface) {
		this.openGLInterface = openGLInterface;

		openGLInterface.setGlCapabilities();
		VBOId = glGenBuffers();
	}

	@Override
	public void editAsFloat(float[] data) {
		openGLInterface.setGlCapabilities();
		FloatBuffer buffer = memAllocFloat(data.length);
		buffer.put(data).flip();

		this.bind();

		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		memFree(buffer);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		this.unbind();

		verticesAmount = data.length;
	}

	@Override
	public void bind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ARRAY_BUFFER, VBOId);
		glEnableVertexAttribArray(0);
	}

	@Override
	public void unbind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void finalize() {
		this.unbind();
		openGLInterface.setGlCapabilities();
		glDeleteBuffers(VBOId);
	}

	public int getVerticesAmount() {
		return verticesAmount;
	}
}
