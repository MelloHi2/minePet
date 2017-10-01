package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.math.Vector3D;

/**
 * 楞
 */
public class ModelPlayer extends ModelBase {
	private Vector3D rot = new Vector3D(0, 0, 0);

	public ModelPlayer(Texture texture, OpenGLInterface openGLInterface, boolean isSlim) {
		super(texture, openGLInterface);

		addBox("body", new Vector3D(8, 12, 4), new Location3D(0, 0, 0), new Location2D(16, 16), new Vector3D(8, 12, 4));
		addBox("head", new Vector3D(8, 8, 8), new Location3D(0, 5, 0), new Location2D(0, 0), new Vector3D(8, 8, 8));
		addBox("arms.right", new Vector3D(isSlim ? 3 : 4, 12, 4), new Location3D(-4 - (isSlim ? 3 : 4) / 2.0f, 3, 0), new Location2D(40, 16), new Vector3D(isSlim ? 3 : 4, 12, 4));
		addBox("head.wear", new Vector3D(9, 9, 9), new Location3D(0, 5, 0), new Location2D(32, 0), new Vector3D(8, 8, 8));
		addBox("body.wear", new Vector3D(9, 13, 5), new Location3D(0, 0, 0), new Location2D(16, 32), new Vector3D(8, 12, 4));

		getBox("body.wear").setUseAlpha(true);
		getBox("body").addChild(getBox("body.wear"));

		getBox("head.wear").setUseAlpha(true);
		getBox("head").addChild(getBox("head.wear"));

		getBox("head").setRotationTarget(new Location3D(0, -4, 0));
		getBox("head.wear").setRotationTarget(new Location3D(0, -4, 0));;
		getBox("arms.right").setRotationTarget(new Location3D(0, 4, 0));
	}

	@Override
	public void tick() {
		rot.add(0, 0, 2);
		getBox("arms.right").setRotation(rot);
	}
}
