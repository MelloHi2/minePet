package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers;

import de.matthiasmann.twl.utils.PNGDecoder;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.ArrayUtils;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.ResourceManager;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * 材质贴图
 *
 * 部分代码来自于https://github.com/lwjglgamedev/lwjglbook/blob/master
 * 嗯哼，我是复制狂魔=w=
 */
public class Texture implements Buffer {
	private final int textureId;
	private boolean isLoadedTexture;
	private final OpenGLInterface openGLInterface;

	private int width;
	private int height;

	public Texture(OpenGLInterface openGLInterface) {
		this.openGLInterface = openGLInterface;
		openGLInterface.setGlCapabilities();
		textureId = glGenTextures();
	}

	@Override
	public void bind() {
		if (!isLoadedTexture) {
			throw new IllegalStateException("Texture isn't loaded yet!");
		}
		glBindTexture(GL_TEXTURE_2D, textureId);
	}

	@Override
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	@Override
	public void loadFromFile(String path) throws IOException {
		isLoadedTexture = true;
		bind();

		PNGDecoder decoder = new PNGDecoder(ResourceManager.getFileResource(path));

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(
				4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(byteBuffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
		byteBuffer.flip();

		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		// 清晰化
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(),
				decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);

		glGenerateMipmap(GL_TEXTURE_2D);

		unbind();

		this.width = decoder.getWidth();
		this.height = decoder.getHeight();
	}

	@Override
	public void finalize() throws Throwable {
		this.unbind();
		openGLInterface.setGlCapabilities();
		glDeleteTextures(textureId);
	}

	public static float[] getTextureCoords(Location2D offset, int width, int height, int depth, int imageWidth, int imageHeight) {
		float offsetX = offset.getX() / imageWidth;
		float offsetY = offset.getY() / imageHeight;

		float width_using = 0.0f + width / imageWidth;
		float height_using = 0.0f + height / imageHeight;
		float depth_using = 0.0f + depth / imageWidth;

		// 前顶右左底后
		float[] coords = new float[24 * 2];
		ArrayUtils.floatArraysFill(coords, Float.NaN);

		// 前
		offsetX += depth_using;
		offsetY += depth_using;
		ArrayUtils.floatArraysFillLast(coords, getCoordsOfFace(offsetX, offsetY, width_using, height_using));
		// 顶
		offsetY -= depth_using;
		ArrayUtils.floatArraysFillLast(coords, getCoordsOfFace(offsetX, offsetY, width_using, depth_using));
		// 右
		offsetY += depth_using;
		offsetX += width_using;
		ArrayUtils.floatArraysFillLast(coords, getCoordsOfFace(offsetX, offsetY, depth_using, height_using));
		// 左
		offsetX -= width_using * 2;
		ArrayUtils.floatArraysFillLast(coords, getCoordsOfFace(offsetX, offsetY, depth_using, height_using));
		// 底
		offsetX += width_using * 2;
		offsetY -= depth_using;
		ArrayUtils.floatArraysFillLast(coords, getCoordsOfFace(offsetX, offsetY, depth_using, height_using));
		// 后
		offsetY += depth_using;
		offsetX += width_using;
		ArrayUtils.floatArraysFillLast(coords, getCoordsOfFace(offsetX, offsetY, width_using, height_using));

		return coords;
	}
	private static float[] getCoordsOfFace(float offsetX, float offsetY, float width, float height) {
		return new float[] {
				offsetX, offsetY,
				offsetX, offsetY + height,
				offsetX + width, offsetY + height,
				offsetX + width, offsetY
		};
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
