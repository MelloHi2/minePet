package luckyfish.programs.minepet.pet.v1_0R0.renderer;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers.Renderer;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.GLFWWindow;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.Shader;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWContextProfileType;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.utils.GLFWWindowsInitInfo;
import luckyfish.programs.minepet.utils.ResourceManager;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * luckyfish.programs.minepet.pet.v1_0R0.renderer.LWJGLTestStage4:
 */
public class LWJGLTestStage4 {
	private GLFWWindow window;
	private OpenGLInterface openGLInterface;
	private Mesh mesh;

	@Before
	public void init() throws Exception {
		window = new GLFWWindow(w -> {
			w.setGLContextVersion(3, 3);
			w.setResizable(false);
			w.setVisible(true);
			w.setGLContextProfile(GLFWContextProfileType.CORE_PROFILE);
			return new GLFWWindowsInitInfo(300, 300);
		});

		openGLInterface = new OpenGLInterface(window, i -> {
			Shader shader = i.getShader();

			try {
				shader.createVertexShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/vertex.vs")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				shader.createFragmentShader(ResourceManager.getFileContents(ResourceManager.getFileResource("assets/shaders/fragment.fs")));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				shader.link();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		float[] vertices = new float[]{
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
				-0.5f, 0.5f, -0.5f,
				// v17
				-0.5f, -0.5f, -0.5f,
				// v18
				-0.5f, -0.5f, 0.5f,
				// v19
				-0.5f, 0.5f, 0.5f,

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

		for (int i = 0;i < 28;i ++) {
			vertices[i * 3] *= 0.5;
			vertices[i * 3 + 1] *= 1;
			vertices[i * 3 + 2] *= 1;
		}

//		for (int i = 0;i < vertices.length;i ++) {
//			System.out.print(vertices[i] + " ");
//			if (i % 3 == 2) {
//				System.out.println();
//			}
//		}

		mesh = new Mesh(vertices, new int[]{
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
		}, new float[] {
				0.5f, 0.0f, 0.0f,
				0.5f, 0.5f, 0.0f,
				0.0f, 0.5f, 0.5f,
				0.0f, 0.0f, 0.5f,
				0.0f, 0.5f, 0.5f,
				0.5f, 0.5f, 0.0f,
				0.0f, 0.0f, 0.0f,
				0.5f, 0.5f, 0.5f,
		}, new Texture(openGLInterface), openGLInterface);
	}

	@Test
	public void testRender() {
		final int[] i = {0};
		while (!window.isWindowShouldClose()) {
			openGLInterface.render(new Renderer() {

				@Override
				public void preRender() {
					openGLInterface.clearColor(new Color(0x66ccff));
					openGLInterface.clear();
					openGLInterface.getShader().bind();
					openGLInterface.enable(OpenGLInterface.EnableValues.DEPTH_TEST);
				}

				@Override
				public void render() {
					mesh.draw();
					mesh.setRotation(i[0]++, i[0]++, 0);
				}

				@Override
				public void postRender() {
					openGLInterface.getShader().unbind();
					openGLInterface.disable(OpenGLInterface.EnableValues.DEPTH_TEST);
				}
			});
		}
	}
}