package luckyfish.programs.minepet.utils.math;

import org.joml.Vector2f;

public class Vector2D extends Vector2f implements Cloneable {
	public Vector2D(float x, float y) {
		super(x, y);
	}

	public Vector2D(Vector2D a) {
		super(a);
	}

	public Vector2D clone() {
		return new Vector2D(x, y);
	}

	@Override
	public String toString() {
		return "Vector2D{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
