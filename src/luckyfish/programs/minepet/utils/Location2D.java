package luckyfish.programs.minepet.utils;

import org.joml.Vector2f;

/**
 * 二维位置
 */
public final class Location2D implements Cloneable {
	private final float x;
	private final float y;

	public Location2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Vector2f toVector() {
		return new Vector2f(x, y);
	}
	public float distance(Location2D location2D) {
		return (float)Math.sqrt(Math.pow(x - location2D.x, 2) + Math.pow(y - location2D.y, 2));
	}
}
