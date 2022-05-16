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
	private static final double INITIAL_K = 1.0;


	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	/**
	 *   
	 * @param ray
	 * @return
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> lst = scene.geometries.findGeoIntersections(ray);
		if (lst==null)
			return null;
		GeoPoint p = ray.findClosestGeoPoint(lst);
		return p;
	}
	/**
	 * 
	 * @param gp
	 * @param v
	 * @param n
	 * @param nv
	 * @return
	 */
	private Ray constructReflectedRay(Point p, Vector v, Vector n) {
		double nv = alignZero(n.dotProduct(v)); 
		Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
		Point point = p.add(epsVector);	
		if (v.dotProduct(n)==0)
			return new Ray(point, v);
		Vector r = v.subtract(n.scale(v.dotProduct(n)).scale(2));
		Ray ans = new Ray(point, r);
		return ans;
	}
	/**
	 * 
	 * @param gp
	 * @param v
	 * @param n
	 * @param nv
	 * @return
	 */
	private Ray constructRefractedRay(Point p, Vector v, Vector n) { 
		double nv = alignZero(n.dotProduct(v)); 
		Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
		Point point = p.add(epsVector);
		Ray ans = new Ray(point, v);
		return ans;
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
		//if (gp.geometry.getMaterial().kT.equals(Double3.ZERO))
			//return true;
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
		Point point = gp.point.add(epsVector);
		Ray lightRay = new Ray(point, lightDirection);
		
		/*List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);//, light.getDistance(gp.point)
		if (intersections == null) return true;
		for (GeoPoint p:intersections) {
			if(p.point.distance(gp.point)<light.getDistance(gp.point))
				return false;
		}
		return true;*/
		
		GeoPoint p = this.findClosestIntersection(lightRay);
		if (p==null) return true;
		if (p.point.distance(gp.point)<light.getDistance(gp.point))
			return false;
		return true;
	}
	
	/** 
	 * 
	 * @return color of the scene
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k)
	{
		Color color = calcLocalEffects(gp,ray);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray.getDir(), level, k));
		//return scene.ambientLight.getIntensity().add(calcLocalEffects(p,ray)); 
	}
	
	/**
	 * 
	 * @param gp
	 * @param ray
	 * @return
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}
	/**
	 * 
	 * @param gp
	 * @param v
	 * @param level
	 * @param k
	 * @return
	 */
	private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
		Color color = Color.BLACK; 
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		//double kkr = material.kR.scale(k);
		//double kkr = k * material.kr;
		Double3 kkr = material.kR.product(k);
		//if (kkr > MIN_CALC_COLOR_K)
		if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
			color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
		}
		//double kkt = k * material.kt;
		Double3 kkt = material.kT.product(k);
		//if (kkt > MIN_CALC_COLOR_K)
		if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
			color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
		}
		return color;
	}
	/**
	 * 
	 * @param ray
	 * @param level
	 * @param kx
	 * @param kkx
	 * @return
	 */
	private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
		GeoPoint gp = findClosestIntersection (ray);
		int levelMinus1 = level-1;
		return (gp == null ? scene.background : calcColor(gp, ray, levelMinus1, kkx)).scale(kx);
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
		GeoPoint closestPoint = this.findClosestIntersection(ray);
		return closestPoint != null ? calcColor(closestPoint, ray) : scene.background;
		
		/*if(point==null) {
			return scene.background;
		}
		return calcColor(point,ray);*/
		
				
		/*List<GeoPoint> lst=scene.geometries.findGeoIntersections(ray);
		if(lst==null) {
			return scene.background; 
		} 
		//GeoPoint p=scene.geometries.findClosestIntersection(ray);
		GeoPoint p=ray.findClosestGeoPoint(lst);
		return calcColor(p,ray); */
		
		
	}

}
