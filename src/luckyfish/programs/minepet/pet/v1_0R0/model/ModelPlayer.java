package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.math.Vector3D;

/**
 * 人
 */
public class ModelPlayer extends ModelBase {
	private Vector3D rot = new Vector3D(0, 0, 0);

	public ModelPlayer(Texture texture, OpenGLInterface openGLInterface, boolean isSlim) {
		super(texture, openGLInterface);

		addBox("body", new Vector3D(8, 12, 4), new Location3D(0, 0, 0), new Location2D(16, 16), new Vector3D(8, 12, 4));
		addBox("head", new Vector3D(8, 8, 8), new Location3D(0, 6, 0), new Location2D(0, 0), new Vector3D(8, 8, 8));
		addBox("arms.right", new Vector3D(isSlim ? 3 : 4, 12, 4), new Location3D(-4 - (isSlim ? 3 : 4) / 2.0f, 4, 0), new Location2D(40, 16), new Vector3D(isSlim ? 3 : 4, 12, 4));
		addBox("arms.left", new Vector3D(isSlim ? 3 : 4, 12, 4), new Location3D(4 + (isSlim ? 3 : 4) / 2.0f, 4, 0), new Location2D(40, 48), new Vector3D(isSlim ? 3 : 4, 12, 4));
		addBox("legs.right", new Vector3D(4, 12, 4), new Location3D(-2, -8, 0), new Location2D(0, 16), new Vector3D(4, 12, 4));
		addBox("legs.left", new Vector3D(4, 12, 4), new Location3D(2, -8, 0), new Location2D(16, 48), new Vector3D(4, 12, 4));

		addBox("arms.right.wear", new Vector3D(isSlim ? 4 : 5, 13, 5), new Location3D(-4 - (isSlim ? 3 : 4) / 2.0f, 4, 0), new Location2D(40, 32), new Vector3D(isSlim ? 3 : 4, 12, 4));
		addBox("arms.left.wear", new Vector3D(isSlim ? 4 : 5, 13, 5), new Location3D(4 + (isSlim ? 3 : 4) / 2.0f, 4, 0), new Location2D(48, 48), new Vector3D(isSlim ? 3 : 4, 12, 4));
		addBox("legs.right.wear", new Vector3D(5, 13, 5), new Location3D(-2, -8, 0), new Location2D(0, 32), new Vector3D(4, 12, 4));
		addBox("legs.left.wear", new Vector3D(5, 13, 5), new Location3D(2, -8, 0), new Location2D(0, 48), new Vector3D(4, 12, 4));
		addBox("head.wear", new Vector3D(9, 9, 9), new Location3D(0, 6, 0), new Location2D(32, 0), new Vector3D(8, 8, 8));
		addBox("body.wear", new Vector3D(9, 13, 5), new Location3D(0, 0, 0), new Location2D(16, 32), new Vector3D(8, 12, 4));


		getBox("body.wear").setUseAlpha(true);
		getBox("body").addChild(getBox("body.wear"));

		getBox("head.wear").setUseAlpha(true);
		getBox("head").addChild(getBox("head.wear"));

		getBox("arms.right.wear").setUseAlpha(true);
		getBox("arms.right").addChild(getBox("arms.right.wear"));

		getBox("arms.left.wear").setUseAlpha(true);
		getBox("arms.left").addChild(getBox("arms.left.wear"));

		getBox("legs.right.wear").setUseAlpha(true);
		getBox("legs.right").addChild(getBox("legs.right.wear"));

		getBox("legs.left.wear").setUseAlpha(true);
		getBox("legs.left").addChild(getBox("legs.left.wear"));

		getBox("head").setRotationTarget(new Location3D(0, -4, 0));
		getBox("head.wear").setRotationTarget(new Location3D(0, -4, 0));;
		getBox("arms.right").setRotationTarget(new Location3D(0, 4, 0));
		getBox("arms.right.wear").setRotationTarget(new Location3D(0, 4, 0));
		getBox("arms.left").setRotationTarget(new Location3D(0, 4, 0));
		getBox("arms.left.wear").setRotationTarget(new Location3D(0, 4, 0));
		getBox("legs.right").setRotationTarget(new Location3D(0, 4, 0));
		getBox("legs.right.wear").setRotationTarget(new Location3D(0, 4, 0));
		getBox("legs.left").setRotationTarget(new Location3D(0, 4, 0));
		getBox("legs.left.wear").setRotationTarget(new Location3D(0, 4, 0));
	}

	@Override
	public void tick() {

	}
}
