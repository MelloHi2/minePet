package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.system.MemoryUtil.memAllocFloat;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * 颜色VBO
 */
public class ColorVertexBufferObject implements Buffer {
	private final int colorVBOId;
	private final OpenGLInterface openGLInterface;

	public ColorVertexBufferObject(OpenGLInterface openGLInterface) {
		openGLInterface.setGlCapabilities();
		colorVBOId = glGenBuffers();
		this.openGLInterface = openGLInterface;
	}

	@Override
	public void bind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ARRAY_BUFFER, colorVBOId);
	}

	@Override
	public void unbind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void finalize() throws Throwable {
		this.unbind();
		openGLInterface.setGlCapabilities();
		glDeleteBuffers(colorVBOId);
	}

	@Override
	public void editAsFloat(float[] colours) {
		openGLInterface.setGlCapabilities();
		this.bind();
		FloatBuffer colourBuffer = memAllocFloat(colours.length);
		colourBuffer.put(colours).flip();

		glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);
		memFree(colourBuffer);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		this.unbind();
	}
}
