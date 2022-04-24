package renderer;

import primitives.*;
import scene.Scene;

public abstract class RayTracerBase {
	protected Scene scene;
	/**
	 * constructor
	 * @param scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene=scene;
	}
	/**
	 * 
	 * @param ray
	 * @return
	 */
	public abstract Color traceRay(Ray ray);

}
