package luckyfish.programs.minepet.utils;

import org.joml.Vector3f;

/**
 * 3D向量
 * 偷懒继承了Vector3f 233
 */
public class Vector3D extends Vector3f implements Cloneable {
	public Vector3D(float x, float y, float z) {
		super(x, y, z);
	}
	public Vector3D clone() {
		return new Vector3D(x, y, z);
	}
}
