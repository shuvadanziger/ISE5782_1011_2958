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
	 * @param ray -ray from the camera
	 * @return the color of the intersections of the ray
	 */
	public abstract Color traceRay(Ray ray);

}
