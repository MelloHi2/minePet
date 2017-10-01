package luckyfish.programs.minepet.utils.math;

import org.joml.Vector3f;

/**
 * 3D向量
 * 偷懒继承了Vector3f 233
 */
public class Vector3D extends Vector3f implements Cloneable {
	public Vector3D(float x, float y, float z) {
		super(x, y, z);
	}

	public Vector3D(Vector3D a) {
		super(a);
	}

	public Vector3D clone() {
		return new Vector3D(x, y, z);
	}

	@Override
	public String toString() {
		return "Vector3D{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
