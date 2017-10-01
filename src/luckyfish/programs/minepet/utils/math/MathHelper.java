package luckyfish.programs.minepet.utils.math;

/**
 * 帮助算数学什么的
 */
public class MathHelper {
	private static final float[] SIN_TABLE = new float[65536];
	private static final float PI = 3.14159265358979f;

	static {
		for (int i = 0;i < 65535;i ++) {
			SIN_TABLE[i] = (float) Math.sin(Math.PI / 65535.0f * (i * 2));
		}
	}

	public static float sin(float radian) {
		return SIN_TABLE[(int)(radian * 10430.378F) & 65535];
	}
	public static float cos(float radian) {
		return SIN_TABLE[(int)(radian * 10430.378f + 16384) & 65535];
	}
	public static float toRadians(float degree) {
		return degree / 180 * PI;
	}
	public static float abs_float(float a) {
		return a < 0 ? -a : a;
	}


	private MathHelper() {
		throw new UnsupportedOperationException();
	}
}
