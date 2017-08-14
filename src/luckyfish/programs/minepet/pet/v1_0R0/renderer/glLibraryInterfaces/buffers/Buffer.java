package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.buffers;

import java.io.IOException;

/**
 * 缓冲对象
 */
public interface Buffer {
	void bind();
	void unbind();

	void finalize() throws Throwable;

	default void editAsFloat(float[] data) {
		throw new UnsupportedOperationException();
	}

	default void editAsInt(int[] data) {
		throw new UnsupportedOperationException();
	}

	default void loadFromFile(String path) throws IOException {
		throw new UnsupportedOperationException();
	}
}
