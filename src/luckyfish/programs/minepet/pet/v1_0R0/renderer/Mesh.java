package luckyfish.programs.minepet.pet.v1_0R0.renderer;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.*;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.utils.Camera;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.utils.RendererFailureException;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.math.Vector3D;
import org.jetbrains.annotations.NotNull;
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

		Shader.Uniform worldMatrix = getUniform("worldMatrix");
		Shader.Uniform viewMatrix = getUniform("viewMatrix");

//		projectionMatrix.setValueAsMatrix(transformation.getProjectionMatrix());
		worldMatrix.setValueAsMatrix(transformation.getWorldMatrix(location, rotation, scale));
		viewMatrix.setValueAsMatrix(transformation.getViewMatrix(openGLInterface.getCamera()));

		openGLInterface.draw(vertexAmount);

		IBO.unbind();
		VBO.unbind();
		textureVertexBufferObject.unbind();

		arrays.unbind();
		texture.unbind();

	}

	public void setLocation(Location3D location) {
		this.location = location.toVector();
		this.location.div(32);
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

	@NotNull
	private Shader.Uniform getUniform(String name) {
		Shader.Uniform uniform;
		try {
			uniform = openGLInterface.getShader().getUniform(name);
		} catch (Exception e) {
			try {
				uniform = openGLInterface.getShader().new Uniform(name);
			} catch (Exception e1) {
				throw new RendererFailureException(e1);
			}
		}
		return uniform;
	}

	/**
	 * 变形器
	 * 主要调整方向和位置
	 *
	 * 膜改自https://github.com/lwjglgamedev/lwjglbook/blob/master
	 * 顺便+1s
	 */
	public final class Transformation {
		private final Matrix4f worldMatrix = new Matrix4f();
		private final Matrix4f viewMatrix = new Matrix4f();

		Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
			worldMatrix.identity().translate(offset).
					rotateX((float)Math.toRadians(rotation.x)).
					rotateY((float)Math.toRadians(rotation.y)).
					rotateZ((float)Math.toRadians(rotation.z)).
					scale(scale);
			return worldMatrix;
		}

		Matrix4f getViewMatrix(Camera camera) {
			Vector3f cameraPos = camera.getPosition();
			Vector3f rotation = camera.getRotation();

			viewMatrix.identity();
			// First do the rotation so camera rotates over its position
			viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
					.rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
			// Then do the translation
			viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
			return viewMatrix;
		}
	}
}
