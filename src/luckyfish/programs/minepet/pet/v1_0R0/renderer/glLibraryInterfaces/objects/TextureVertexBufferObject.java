package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.TextureVertexBufferObject:
 */
public class TextureVertexBufferObject implements Object {
	private final int textureVBOId;
	private final OpenGLInterface openGLInterface;

	public TextureVertexBufferObject(OpenGLInterface openGLInterface) {
		this.openGLInterface = openGLInterface;
		openGLInterface.setGlCapabilities();

		textureVBOId = glGenBuffers();
	}

	@Override
	public void bind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ARRAY_BUFFER, textureVBOId);
	}
	@Override
	public void unbind() {
		openGLInterface.setGlCapabilities();
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void finalize() throws Throwable {
		unbind();
		openGLInterface.setGlCapabilities();
		glDeleteBuffers(textureVBOId);
	}

	@Override
	public void editAsFloat(float[] data) {
		openGLInterface.setGlCapabilities();
		FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(data.length);
		textCoordsBuffer.put(data).flip();
		glBindBuffer(GL_ARRAY_BUFFER, textureVBOId);
		glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
	}
}
