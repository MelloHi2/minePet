package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.Mesh;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.Vector3D;

import java.io.IOException;

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

			// back face
			// V24
			-0.5f,  0.5f,  0.5f,
			// V25
			-0.5f, -0.5f,  0.5f,
			// V26
			0.5f, -0.5f,  0.5f,
			// V27
			0.5f,  0.5f,  0.5f,
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
			24, 25, 27, 27, 25, 26,
	};

	private Location3D location;
	private final Mesh mesh;
	private OpenGLInterface openGLInterface;

	public ModelBox(Vector3D size, Location3D location, String textureLocation, OpenGLInterface openGLInterface) throws IOException {
		float[] currentVertex = new float[vertices.length];
		System.arraycopy(vertices, 0, currentVertex, 0, vertices.length);
		for (int i = 0;i < 28;i ++) {
			currentVertex[i * 3] *= size.x;
			currentVertex[i * 3 + 1] *= size.y;
			currentVertex[i * 3 + 2] *= size.z;
		}
//		for (int i = 0;i < 8;i ++) {
//			for (int j = 0;j < 3;j ++) {
//				System.out.print(currentVertex[i * 3 + j] + " ");
//			}
//			System.out.println();
//		}

		this.location = location;

		Texture texture = new Texture(openGLInterface);
		texture.loadFromFile(textureLocation);

		mesh = new Mesh(currentVertex, indices, new float[] {
				// V0
				8.0f / 64.0f, 8.0f / 64.0f,
				// V1
				8.0f / 64.0f, 16.0f / 64.0f,
				// V2
				16.0f / 64.0f, 16.0f / 64.0f,
				// V3
				16.0f / 64.0f, 8.0f / 64.0f,
				// V4
				24.0f / 64.0f, 8.0f / 64.0f,
				// V5
				24.0f / 64.0f, 16.0f / 64.0f,
				// V6
				32.0f / 64.0f, 16.0f / 64.0f,
				// V7
				32.0f / 64.0f, 8.0f / 64.0f,
				// V8
				8.0f / 64.0f, 0.0f / 64.0f,
				// V9
				8.0f / 64.0f, 8.0f / 64.0f,
				// V10
				16.0f / 64.0f, 8.0f / 64.0f,
				// V11
				16.0f / 64.0f, 0.0f / 64.0f,
				// V12
				16.0f / 64.0f, 8.0f / 64.0f,
				// V13
				16.0f / 64.0f, 16.0f / 64.0f,
				// V14
				24.0f / 64f, 16f / 64f,
				// V15
				24f / 64f, 8f / 64f,
				// V16
				0f / 64f, 8f / 64f,
				// V17
				0f / 64f, 16f / 64f,
				// V18
				8f / 64f, 16f / 64f,
				// V19
				8f / 64f, 8f / 64f,
				// V20
				16f / 64f, 0f / 64f,
				16f / 64f, 8f / 64f,
				24f / 64f, 8f / 64f,
				24f / 64f, 0f / 64f,
				// V4
				24.0f / 64.0f, 8.0f / 64.0f,
				// V5
				24.0f / 64.0f, 16.0f / 64.0f,
				// V6
				32.0f / 64.0f, 16.0f / 64.0f,
				// V7
				32.0f / 64.0f, 8.0f / 64.0f,
		}, texture, openGLInterface);

		mesh.setLocation(location);

		this.openGLInterface = openGLInterface;
	}

	public void draw() {
		openGLInterface.enable(OpenGLInterface.EnableValues.DEPTH_TEST);
		openGLInterface.enable(OpenGLInterface.EnableValues.TEXTURE);
		openGLInterface.useTexture();
		mesh.draw();
		openGLInterface.disable(OpenGLInterface.EnableValues.TEXTURE);
		openGLInterface.disable(OpenGLInterface.EnableValues.DEPTH_TEST);
	}

	public void setLocation(Location3D location) {
		this.location = location;
		mesh.setLocation(location);
	}
	public void setRotation(Vector3D rotation) {
		rotation.add(0, 0, 0);
		mesh.setRotation(rotation);
	}
}
