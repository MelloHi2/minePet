package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers;

/**
 * 渲染器
 */
public interface Renderer {
	default void preRender() {}
	void render();
	default void postRender() {}
}
