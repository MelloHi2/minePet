package luckyfish.programs.minepet.utils.math;

import org.junit.Test;

public class MathHelperTest {
	@Test
	public void sin() throws Exception {
		if (MathHelper.sin(MathHelper.toRadians(270)) > 0) {
			throw new IllegalStateException(String.valueOf(MathHelper.sin(MathHelper.toRadians(270))));
		}
	}

	@Test
	public void cos() throws Exception {
	}

}