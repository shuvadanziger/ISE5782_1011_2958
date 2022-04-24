package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	/** 
	 * @return background of the scene
	 */
	private Color calcColor(Point p)
	{
		return scene.ambientLight.getIntensity(); 
	}
	@Override
	public  Color traceRay(Ray ray) {
		List<Point> lst=scene.geometries.findIntsersections(ray);
		if(lst==null) {
			return scene.background;
		} 
		Point p=ray.findClosestPoint(lst);
		return calcColor(p);
		
		
	}

}
