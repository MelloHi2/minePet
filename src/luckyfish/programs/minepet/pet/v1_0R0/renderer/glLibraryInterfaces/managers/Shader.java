package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

/**
 * 着色器
 *
 * 代码源自
 * https://github.com/lwjglgamedev/lwjglbook/blob/master
 * 并经过了一些修改
 */

public class Shader {

	private final int programId;

	private int vertexShaderId;

	private int fragmentShaderId;

	private final List<Uniform> uniformList = new LinkedList<>();

	private final OpenGLInterface openGLInterface;

	public Shader(OpenGLInterface openGLInterface) throws Exception {
		this.openGLInterface = openGLInterface;
		openGLInterface.setGlCapabilities();
		programId = glCreateProgram();
		if (programId == 0) {
			throw new Exception("Could not create Shader");
		}
	}

	public void createVertexShader(String shaderCode) throws Exception {
		openGLInterface.setGlCapabilities();
		vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
	}

	public void createFragmentShader(String shaderCode) throws Exception {
		openGLInterface.setGlCapabilities();
		fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}

	protected int createShader(String shaderCode, int shaderType) throws Exception {
		openGLInterface.setGlCapabilities();
		int shaderId = glCreateShader(shaderType);
		if (shaderId == 0) {
			throw new Exception("Error creating shader. Type: " + shaderType);
		}

		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
		}

		glAttachShader(programId, shaderId);

		return shaderId;
	}

	public void link() throws Exception {
		openGLInterface.setGlCapabilities();
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
		}

		if (vertexShaderId != 0) {
			glDetachShader(programId, vertexShaderId);
		}
		if (fragmentShaderId != 0) {
			glDetachShader(programId, fragmentShaderId);
		}

		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
		}

	}

	public void bind() {
		openGLInterface.setGlCapabilities();
		glUseProgram(programId);
	}

	public void unbind() {
		openGLInterface.setGlCapabilities();
		glUseProgram(0);
	}

	public Uniform getUniform(String name) throws Exception {
		for (Uniform u : uniformList) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		throw new Exception("Didn't found uniform: " + name);
	}

	@Override
	protected void finalize() {
		unbind();
		openGLInterface.setGlCapabilities();
		if (programId != 0) {
			glDeleteProgram(programId);
		}
	}

	public final class Uniform {
		private final String name;

		public int getUniformId() {
			return uniformId;
		}

		private final int uniformId;

		public Uniform(String name) throws Exception {
			uniformId = glGetUniformLocation(Shader.this.programId, name);

			if (uniformId < 0) {
				throw new Exception("Failed to create uniform" + name);
			}
			this.name = name;
			Shader.this.uniformList.add(this);
		}

		public String getName() {
			return name;
		}

		public void setValueAsMatrix(Matrix4f value) {
			try (MemoryStack stack = MemoryStack.stackPush()) {
				FloatBuffer fb = stack.mallocFloat(16);
				value.get(fb);
				glUniformMatrix4fv(this.uniformId, false, fb);
			}
		}

		public void setValueAsInteger(int value) {
			glUniform1i(this.uniformId, value);
		}
	}
}