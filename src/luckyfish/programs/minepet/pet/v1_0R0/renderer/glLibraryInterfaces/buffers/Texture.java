package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers;

import de.matthiasmann.twl.utils.PNGDecoder;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.ArrayUtils;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.ResourceManager;
import luckyfish.programs.minepet.utils.mojangHelpers.MojangAPI;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

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

	public static Texture getAuthorTexture(OpenGLInterface openGLInterface) throws IOException {
		Texture texture = new Texture(openGLInterface);
		try {
			texture.isLoadedTexture = true;
			texture.bind();

			PNGDecoder decoder = new PNGDecoder(MojangAPI.getPlayerSkinInputStream(new UUID(210805187849766789L, -7191104397834579219L)));

			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(decoder.getWidth() * 4 * decoder.getHeight());
			decoder.decode(byteBuffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			byteBuffer.flip();

			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			// 清晰化
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(),
					decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);

			glGenerateMipmap(GL_TEXTURE_2D);

			texture.width = decoder.getWidth();
			texture.height = decoder.getHeight();

			texture.unbind();
		} catch (NullPointerException e) {
			System.out.println(new String(Base64.getDecoder().decode("dxrykkUr/iOm8iVgkxa08HxUZLqwt3j5SF3zS9ZONbsD1W8LYl+7281YGhL5ZC7IoSLoquPxQv0+WctVGS6Vy0+1aQ+axb9UA9pVg/Z9jaigms63S5ayfkF+Q8kvy6AXf7pp3SpGr2aGGRrEBHejFCKmNbr51FYH/5HodevLUurjr9oZzz4u6eGgHVzDnnL/8GKm+bVgVRGmoL0F3YaQ4A==")));
		}

		return texture;
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

	public static float[] getTextureCoords(Location2D offsets, int width, int height, int depth, int textureWidth, int textureHeight) {
		float[] retn = new float[24 * 2];
		for (int i = 0; i < retn.length; i++) {
			retn[i] = (float) Double.NaN;
		}

		// 前面
		ArrayUtils.floatArraysFillLast(retn, getTextureCoordWithSurface(offsets.getX() + depth, offsets.getY() + depth, width, height));
		// 后面
		ArrayUtils.floatArraysFillLast(retn, getTextureCoordWithSurface(offsets.getX() + depth * 2 + width, offsets.getY() + depth, width, height));
		// 顶部
		ArrayUtils.floatArraysFillLast(retn, getTextureCoordWithSurface(offsets.getX() + depth, offsets.getY(), width, depth));
		// 右边
		ArrayUtils.floatArraysFillLast(retn, getTextureCoordWithSurface(offsets.getX() + depth + width, offsets.getY() + depth, depth, height));
		// 左边
		ArrayUtils.floatArraysFillLast(retn, getTextureCoordWithSurface(offsets.getX(), offsets.getY() + depth, depth, height));
		// 底部
		ArrayUtils.floatArraysFillLast(retn, getTextureCoordWithSurface(offsets.getX() + depth + width, offsets.getY(), width, depth));

		for (int i = 0;i < 24 * 2;i ++) {
			retn[i] /= (i % 2 == 0 ? textureHeight : textureWidth);
		}

		return retn;
	}
	private static float[] getTextureCoordWithSurface(float starterX, float starterY, int width, int height) {
		return new float[] {
				starterX, starterY,
				starterX, starterY + height,
				starterX + width, starterY + height,
				starterX + width, starterY
		};
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
