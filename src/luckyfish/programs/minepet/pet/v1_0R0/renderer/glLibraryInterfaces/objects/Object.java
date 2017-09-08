package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects;

import java.io.IOException;

/**
 * OpenGL专用对象
 * 还有我没对象(°Д°)
 */
public interface Object {
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
