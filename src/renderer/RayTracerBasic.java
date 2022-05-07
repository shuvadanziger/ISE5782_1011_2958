package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	/** 
	 * 
	 * @return color of the scene
	 */
	private Color calcColor(GeoPoint p)
	{
		return scene.ambientLight.getIntensity().add(p.geometry.getEmission()); 
	}
	/**
	 * return the color of the intsersections between the scene
	 */
	@Override
	public  Color traceRay(Ray ray) {
		List<GeoPoint> lst=scene.geometries.findGeoIntersectionsHelper(ray);
		if(lst==null) {
			return scene.background; 
		} 
		GeoPoint p=ray.findClosestGeoPoint(lst);
		return calcColor(p);
		
		
	}

}
