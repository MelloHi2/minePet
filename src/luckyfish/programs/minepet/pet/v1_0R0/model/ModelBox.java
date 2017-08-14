package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.Mesh;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.Vector3D;

import java.io.IOException;

/**
 * 方块模型
 * 会拿来捏人的
 */
public class ModelBox {
	private static final float[] vertices = {
			// V0
			-0.5f, 0.5f, 0.5f,
			// V1
			-0.5f, -0.5f, 0.5f,
			// V2
			0.5f, -0.5f, 0.5f,
			// V3
			0.5f, 0.5f, 0.5f,
			// V4
			-0.5f, 0.5f, -0.5f,
			// V5
			-0.5f, -0.5f, -0.5f,
			// V6
			0.5f, -0.5f, -0.5f,
			// V7
			0.5f, 0.5f, -0.5f,

			// For text coords in top face
			// V8: V4 repeated
			-0.5f, 0.5f, -0.5f,
			// V9: V5 repeated
			-0.5f, 0.5f, 0.5f,
			// V10: V0 repeated
			0.5f, 0.5f, 0.5f,
			// V11: V3 repeated
			0.5f, 0.5f, -0.5f,

			// For text coords in right face
			// V12: V3 repeated
			0.5f, 0.5f, 0.5f,
			// V13: V2 repeated
			0.5f, -0.5f, 0.5f,
			// V14
			0.5f, -0.5f, -0.5f,
			// V15
			0.5f, 0.5f, -0.5f,

			// For text coords in left face
			// V16: V0 repeated
			-0.5f, 0.5f, 0.5f,
			// V17: V1 repeated
			-0.5f, -0.5f, 0.5f,
			// V18
			-0.5f, -0.5f, -0.5f,
			// V19
			-0.5f, 0.5f, -0.5f

			// For text coords in bottom face
			// V20: V6 repeated
			-0.5f, -0.5f, -0.5f,
			// V21: V7 repeated
			-0.5f, -0.5f, 0.5f,
			// V22: V1 repeated
			0.5f, -0.5f, 0.5f,
			// V23: V2 repeated
			0.5f, -0.5f, -0.5f,
	};
	private static final int[] indices = {
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
			4, 5, 7, 7, 5, 6
	};

	private Location3D location;
	private final Mesh mesh;
	private OpenGLInterface openGLInterface;

	public ModelBox(Vector3D size, Location3D location, String textureLocation, OpenGLInterface openGLInterface) throws IOException {
		float[] currentVertex = new float[24];
		System.arraycopy(vertices, 0, currentVertex, 0, 24);
		for (int i = 0;i < 8;i ++) {
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
				24f / 64f, 0f / 64f
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
		rotation.add(0, 180, 0);
		mesh.setRotation(rotation);
	}
}
