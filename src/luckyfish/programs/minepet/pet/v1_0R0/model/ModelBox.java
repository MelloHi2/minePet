package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.Mesh;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.Vector3D;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 方块模型
 * 拿来捏人的
 */
public class ModelBox {
	private static final float[] vertices = new float[]{
			// VO
			-0.5f,  0.5f,  -0.5f,
			// V1
			-0.5f, -0.5f,  -0.5f,
			// V2
			0.5f, -0.5f,  -0.5f,
			// V3
			0.5f,  0.5f,  -0.5f,
			// V4
			-0.5f,  0.5f, 0.5f,
			// V5
			0.5f,  0.5f, 0.5f,
			// V6
			-0.5f, -0.5f, 0.5f,
			// V7
			0.5f, -0.5f, 0.5f,

			// top face
			// v8
			-0.5f, 0.5f, 0.5f,
			// v9
			-0.5f, 0.5f, -0.5f,
			// v10
			0.5f, 0.5f, -0.5f,
			// v11
			0.5f, 0.5f, 0.5f,

			// right face
			// v12
			0.5f, 0.5f, -0.5f,
			// v13
			0.5f, -0.5f, -0.5f,
			// v14
			0.5f, -0.5f, 0.5f,
			// v15
			0.5f, 0.5f, 0.5f,

			// left face
			// v16
			-0.5f, 0.5f, 0.5f,
			// v17
			-0.5f, -0.5f, 0.5f,
			// v18
			-0.5f, -0.5f, -0.5f,
			// v19
			-0.5f, 0.5f, -0.5f,

			// bottom face
			// v20
			-0.5f, -0.5f, 0.5f,
			// v21
			-0.5f, -0.5f, -0.5f,
			// v22
			0.5f, -0.5f, -0.5f,
			// v23
			0.5f, -0.5f, 0.5f,
	};
	private static final int[] indices = new int[]{
			// Front face
			0, 1, 3, 3, 1, 2,
			// Top Face
			8, 9, 11, 11, 9, 10,
			// Right face
			12, 13, 15, 15, 13, 14,
			// Left face
			16, 17, 19, 19, 17, 18,
			// Bottom face
			20, 21, 23, 23, 21, 22,
			// Back face
			4, 5, 7, 7, 5, 6,
	};

	private Location3D location;
	private final Mesh mesh;
	private OpenGLInterface openGLInterface;

	private List<ModelBox> children = new LinkedList<>();

	private boolean useAlpha = false;
	private Vector3D rotationOffset = new Vector3D(0, 0, 0);
	private Vector3D rotation = new Vector3D(0, 0, 0);

	public ModelBox(Vector3D size, Location3D location, Texture texture, Vector3D sizeForTexture, Location2D textureOffset, OpenGLInterface openGLInterface) throws IOException {
		float[] currentVertex = new float[vertices.length];
		System.arraycopy(vertices, 0, currentVertex, 0, vertices.length);
		size.div(32);
		for (int i = 0;i < 24;i ++) {
			currentVertex[i * 3] *= size.x;
			currentVertex[i * 3 + 1] *= size.y;
			currentVertex[i * 3 + 2] *= size.z;
		}

		this.location = location;
//		System.out.println((int) sizeForTexture.y);
		mesh = new Mesh(currentVertex, indices, Texture.getTextureCoords(textureOffset, (int) sizeForTexture.x, (int) sizeForTexture.y, (int) sizeForTexture.z, texture.getWidth(), texture.getHeight()), texture, openGLInterface);

		mesh.setLocation(location);

		this.openGLInterface = openGLInterface;
	}

	public void setUseAlpha(boolean useAlpha) {
		this.useAlpha = useAlpha;
	}

	public void draw() {
		openGLInterface.enable(OpenGLInterface.EnableValues.DEPTH_TEST);
		openGLInterface.enable(OpenGLInterface.EnableValues.TEXTURE);
		if (useAlpha) {
			openGLInterface.enable(OpenGLInterface.EnableValues.ALPHA);
			openGLInterface.enable(OpenGLInterface.EnableValues.BLEND);
			openGLInterface.setBlendFunction(OpenGLInterface.BlendFunctions.SRC_ALPHA, OpenGLInterface.BlendFunctions.ONE_MINUS_SRC_ALPHA);
		}
		openGLInterface.useTexture();
		Vector3D rotation = this.rotation.clone();
		rotation.add(rotationOffset);
		mesh.setRotation(rotation);
		mesh.draw();
		if (useAlpha) {
			openGLInterface.disable(OpenGLInterface.EnableValues.ALPHA);
			openGLInterface.disable(OpenGLInterface.EnableValues.BLEND);
		}
		openGLInterface.disable(OpenGLInterface.EnableValues.TEXTURE);
		openGLInterface.disable(OpenGLInterface.EnableValues.DEPTH_TEST);
	}

	public void setLocation(Location3D location) {
		this.location = location;
		mesh.setLocation(location);
	}
	public void setRotation(Vector3D rotation) {
		this.rotation = rotation;
		for (ModelBox box : children) {
			box.setRotationOffset(rotation);
		}
	}

	public void setRotationOffset(Vector3D rotationOffset) {
		this.rotationOffset = rotationOffset;
	}

	public void addChild(ModelBox child) {
		children.add(child);
	}
}
