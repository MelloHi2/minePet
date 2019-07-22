package luckyfish.programs.minepet.utils;

import luckyfish.programs.minepet.utils.math.Vector3D;

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

	public final Vector3D toVector() {
		return new Vector3D(x, y, z);
	}
	public final float distance(Location3D anotherLocation) {
		return (float)Math.sqrt(Math.pow(this.x - anotherLocation.x, 2) + Math.pow(this.y - anotherLocation.y, 2) + Math.pow(this.z - anotherLocation.z, 2));
	}

	public final Location3D divide(float s) {
		x /= s;
		y /= s;
		z /= s;
		return this;
	}
	public final Location3D add(Location3D another) {
		x += another.x;
		y += another.y;
		z += another.z;
		return this;
	}
	public final Location3D add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	@Override
	public Location3D clone() throws CloneNotSupportedException {
		return new Location3D(x, y, z);
	}

	@Override
	public String toString() {
		return "Location3D{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}

	public final Location3D add(Vector3D d) {
		return add(d.x, d.y, d.z);
	}
	public final Location3D subtract(Location3D another) {
		return add(-another.x, -another.y, -another.z);
	}
}
