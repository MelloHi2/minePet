package luckyfish.programs.minepet.pet.v1_0R0.model;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.objects.Texture;
import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;
import luckyfish.programs.minepet.utils.Location2D;
import luckyfish.programs.minepet.utils.Location3D;
import luckyfish.programs.minepet.utils.math.Vector3D;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 每只模型的基础
 */
public abstract class ModelBase {
	public Map<String, ModelBox> getBoxes() {
		return boxes;
	}

	private Map<String, ModelBox> boxes = new LinkedHashMap<>();

	private final OpenGLInterface openGLInterface;
	private final Texture texture;

	ModelBase(Texture texture, OpenGLInterface openGLInterface) {
		this.texture = texture;
		this.openGLInterface = openGLInterface;
	}

	final void addBox(String name, Vector3D size, Location3D location, Location2D textureOffset, Vector3D sizeForTexture) {
		if (boxes.containsKey(name)) {
			throw new IllegalArgumentException("There is already a box here!");
		}
		boxes.put(name, new ModelBox(name, size, location, texture, sizeForTexture, textureOffset, openGLInterface));
	}
	final void addBox(String name, Vector3D size, Location3D location, Location2D textureOffset, Vector3D sizeForTexture, Vector3D preRotation) {
		if (boxes.containsKey(name)) {
			throw new IllegalArgumentException("There is already a box here!");
		}
		boxes.put(name, new ModelBox(name, size, location, texture, sizeForTexture, textureOffset, openGLInterface, preRotation));
	}
	final ModelBox getBox(String name) {
		if (!boxes.containsKey(name)) {
			throw new NullPointerException("Target box not found!");
		}
		return boxes.get(name);
	}

	public final void render() {
		openGLInterface.enable(OpenGLInterface.EnableValues.DEPTH_TEST);
		boxes.forEach((s, b) -> b.draw());
		openGLInterface.disable(OpenGLInterface.EnableValues.DEPTH_TEST);
	};
	public abstract void tick();
	public void sit() {
		// 什么都不干=-=
	}

	public void walk(long time) {
		// 什么都不干=-=
	}
}
