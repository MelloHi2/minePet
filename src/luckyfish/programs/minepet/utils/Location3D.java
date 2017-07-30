package luckyfish.programs.minepet.utils;

import org.joml.Vector3f;

/**
 * 三维位置
 */
public final class Location3D implements Cloneable {
	private float x;
	private float y;
	private float z;

	public Location3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public final float getX() {
		return x;
	}

	public final void setX(float x) {
		this.x = x;
	}

	public final float getY() {
		return y;
	}

	public final void setY(float y) {
		this.y = y;
	}

	public final float getZ() {
		return z;
	}

	public final void setZ(float z) {
		this.z = z;
	}

	public final Vector3f toVector() {
		return new Vector3f(x, y, z);
	}
	public final float distance(Location3D anotherLocation) {
		return (float)Math.sqrt(Math.pow(this.x - anotherLocation.x, 2) + Math.pow(this.y - anotherLocation.y, 2) + Math.pow(this.z - anotherLocation.z, 2));
	}
}
