package renderer;

import java.util.List;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import geometries.Triangle;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import static primitives.Util.*;


public class RayTracerBasic extends RayTracerBase{
	/**
	 * Varies for moving the beginning of the rays for shading rays
	 */
	private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	/**
	 * Checks if the point is shaded
	 * @param gp GeoPoint-the point the function check.
	 * @param l vector from the light source to the point
	 * @param n normal to the geometry object
	 * @param nv Helps to know if the camera is in the normal direction or in the opposite direction to know which direction to move the point
	 * @param light the light source.
	 * @return
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
		Point point = gp.point.add(epsVector);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		//if (gp.geometry instanceof Triangle) {
		//	boolean b=intersections.remove(gp);
		//}
		if (intersections == null) return true;
		
		for (GeoPoint p:intersections) {
			if(p.point.distance(gp.point)<light.getDistance(gp.point))
				return false;
		}
		return true;
	}
	/** 
	 * 
	 * @return color of the scene
	 */
	private Color calcColor(GeoPoint p, Ray ray)
	{
		
		return scene.ambientLight.getIntensity().add(calcLocalEffects(p,ray)); 
	}
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir (); 
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v)); 
		if (nv == 0) return color;
		Material mat = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0 && unshaded(gp,l,n,nv, lightSource)) { // sign(nl) == sing(nv)
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(mat, nl)),iL.scale(calcSpecular(mat, n,l,nl,v)));
			}
		}
		return color;
	}
	/**
	 * Scattering of light from the geometric object coming from a certain direction
	 * @param mat
	 * @param nl
	 * @return
	 */
	private Double3 calcDiffusive(Material mat, double nl) {
		
		return mat.kD.scale(Math.abs(nl));
	}
	/**
	 * Flash of light
	 * @param mat
	 * @param v
	 * @param r
	 * @param nSH
	 * @return
	 */
	private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
		
		Vector temp=n.scale(nl).scale(-2);
		Vector r= l.add(temp);
		double vr=alignZero(r.dotProduct(v.scale(-1)));
		if(vr<=0)
		{
			return Double3.ZERO;
		}
		return mat.kS.scale(Math.pow(vr, mat.nShininess));
		
	}
	/**
	 * return the color of the intsersections between the scene
	 */
	
	public  Color traceRay(Ray ray) {
		List<GeoPoint> lst=scene.geometries.findGeoIntersections(ray);
		if(lst==null) {
			return scene.background; 
		} 
		GeoPoint p=ray.findClosestGeoPoint(lst);
		return calcColor(p,ray); 
		
		
	}

}
