package luckyfish.programs.minepet.utils;

import static java.lang.Double.isNaN;

/**
 * luckyfish.programs.minepet.utils.ArrayUtils:
 */
public final class ArrayUtils {
	private ArrayUtils() {
		throw new UnsupportedOperationException();
	}

	public static float[] floatArraysFillLast(float[] target, float[] source) {
		int i = 0;
		for (float f : target) {
			if (isNaN(f)) {
				for (float fl : source) {
					target[i ++] = fl;

				}
				return target;
			}
			i ++;
		}
		return target;
	}

	public static float[] floatArraysFill(float[] target, float src) {
		for (int i = 0;i < target.length;i ++) {
			target[i] = src;
		}
		return target;
	}
}
