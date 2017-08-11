package luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.initializers;

import luckyfish.programs.minepet.pet.v1_0R0.renderer.glLibraryInterfaces.managers.OpenGLInterface;

/**
 * OpenGL初始化
 */
public interface GLInitializer {
	default void preInit(OpenGLInterface glInterface) {}
	void init(OpenGLInterface glInterface);
}
