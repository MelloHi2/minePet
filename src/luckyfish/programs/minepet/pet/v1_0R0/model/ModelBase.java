package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.Vector3D;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 每只模型的基础
 */
public abstract class ModelBase {
	private Map<String, ModelBox> boxes = new LinkedHashMap<>();

	private final OpenGLInterface openGLInterface;
	private final Texture texture;

	ModelBase(Texture texture, OpenGLInterface openGLInterface) {
		this.texture = texture;
		this.openGLInterface = openGLInterface;
	}

	final void addBox(String name, Vector3D size, Location3D location, Location2D textureOffset, Vector3D sizeForTexture) {
		boxes.put(name, new ModelBox(size, location, texture, sizeForTexture, textureOffset, openGLInterface));
	}
	final ModelBox getBox(String name) {
		if (!boxes.containsKey(name)) {
			throw new NullPointerException("Target box not found!");
		}
		return boxes.get(name);
	}

	public final void render() {
		boxes.forEach((s, b) -> b.draw());
	};
	public abstract void tick();
	public void sit() {
		// 什么都不干=-=
	}

	public void walk() {
		// 什么都不干=-=
	}

	public void setRotation(Vector3D rotation) {
		boxes.forEach((s, b) -> b.setRotation((Vector3D) rotation.set(0, rotation.y, rotation.z)));
	}
}
