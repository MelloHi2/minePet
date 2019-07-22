package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.system.MemoryUtil.memAllocInt;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * 顶点序列
 */
public class IndexBufferObject implements Object {
	private final int indexBufferObjectId;
	private final OpenGLInterface openGLInterface;

	public IndexBufferObject(OpenGLInterface openGLInterface) {
		this.openGLInterface = openGLInterface;
		openGLInterface.setGlCapabilities();

		indexBufferObjectId = glGenBuffers();
	}

	@Override
	public void bind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObjectId);
	}

	@Override
	public void unbind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	@Override
	public void finalize() throws Throwable {
		unbind();

		openGLInterface.setGlCapabilities();
		glDeleteBuffers(indexBufferObjectId);
	}

	@Override
	public void editAsInt(int[] data) {
		openGLInterface.setGlCapabilities();

		IntBuffer buffer = memAllocInt(data.length);
		buffer.put(data).flip();
		this.bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		this.unbind();
		memFree(buffer);
	}
}
