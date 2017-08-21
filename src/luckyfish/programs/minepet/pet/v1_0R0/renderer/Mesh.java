package luckyfish.programs.minepet.pet.v1_0R0.renderer;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers.*;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.utils.RendererFailureException;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.Vector3D;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * 网状结构
 */
public final class Mesh {
	private final VertexArrays arrays;
	private final VertexBufferObject VBO;
	private final IndexBufferObject IBO;
	private final TextureVertexBufferObject textureVertexBufferObject;
	private final Texture texture;

	private final int vertexAmount;

	private OpenGLInterface openGLInterface;

	private Vector3f rotation = new Vector3f();
	private Vector3f location = new Vector3f();
	private float scale = 1.0f;

	private Transformation transformation;

	public Mesh(float[] vertices, int[] indices, float[] textureCoords, Texture texture, OpenGLInterface openGLInterface) {
		arrays = new VertexArrays(openGLInterface);
		VBO = new VertexBufferObject(openGLInterface);
		textureVertexBufferObject = new TextureVertexBufferObject(openGLInterface);
		IBO = new IndexBufferObject(openGLInterface);
		this.texture = texture;

		vertexAmount = vertices.length;

		arrays.bind();

		VBO.editAsFloat(vertices);
		textureVertexBufferObject.editAsFloat(textureCoords);
		IBO.editAsInt(indices);

		arrays.unbind();

		this.openGLInterface = openGLInterface;
		this.transformation = new Transformation();
	}

	public void draw() {
		texture.bind();
		arrays.bind();

		textureVertexBufferObject.bind();
		VBO.bind();
		IBO.bind();

		Shader.Uniform projectionMatrix;
		Shader.Uniform worldMatrix;
		try {
//			projectionMatrix = openGLInterface.getShader().getUniform("projectionMatrix");
			worldMatrix = openGLInterface.getShader().getUniform("worldMatrix");
		} catch (Exception e) {
			try {
//				projectionMatrix = openGLInterface.getShader().new Uniform("projectionMatrix");
				worldMatrix = openGLInterface.getShader().new Uniform("worldMatrix");
			} catch (Exception e1) {
				throw new RendererFailureException(e1);
			}
		}

//		projectionMatrix.setValueAsMatrix(transformation.getProjectionMatrix());
		worldMatrix.setValueAsMatrix(transformation.getWorldMatrix(location, rotation, scale));

		openGLInterface.draw(vertexAmount);

		IBO.unbind();
		VBO.unbind();
		textureVertexBufferObject.unbind();

		arrays.unbind();
		texture.unbind();

	}

	public void setLocation(Location3D location) {
		this.location = location.toVector();
		this.location.div(128.0f);
	}

	public void setRotation(float x, float y, float z) {
		setRotation(new Vector3D(x, y, z));
	}
	public void setRotation(Vector3D vector3D) {
		this.rotation = vector3D;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * 变形器
	 * 主要调整方向和位置
	 *
	 * 膜改自https://github.com/lwjglgamedev/lwjglbook/blob/master
	 * 顺便+1s
	 */
	public final class Transformation {
//		private final Matrix4f projectionMatrix = new Matrix4f(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		private final Matrix4f worldMatrix = new Matrix4f();
//	    /*
//		Q:为什么我被注释掉？ QAQ
//		A:因为你让我的东西没法显示了啊qwq
//		*/
//		public final Matrix4f getProjectionMatrix() {
//			projectionMatrix.identity();
//			projectionMatrix.perspective(90, 1, 0.01f, 1000.0f);
//			return projectionMatrix;
//		}

		Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
			worldMatrix.identity().translate(offset).
					rotateX((float)Math.toRadians(rotation.x)).
					rotateY((float)Math.toRadians(rotation.y)).
					rotateZ((float)Math.toRadians(rotation.z)).
					scale(scale);
			return worldMatrix;
		}
	}
}
