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
	 * maximum levels of recursion
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/**
	 * minimum amount of color that effects
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;
	/**
	 * Initial value of color that effects
	 */
	private static final double INITIAL_K = 1.0;

	/**
	 * Constructor
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	/**
	 * find the closest intersection
	 * @param ray
	 * @return
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> lst = scene.geometries.findGeoIntersections(ray);  //find the list of intersection points
		if (lst==null)
			return null;
		GeoPoint p = ray.findClosestGeoPoint(lst); //find the closest intersection point
		return p;
	}
	/**
	 * Calculate the reflected ray
	 * @param gp-geo point 
	 * @param v
	 * @param n
	 * @param nv
	 * @return HISHTAKFUT-reflected ray
	 */
	private Ray constructReflectedRay(Point p, Vector v, Vector n) {
		if (n.dotProduct(v)==0)
			return new Ray(p, v, n);
		Vector r = v.subtract(n.scale(v.dotProduct(n)).scale(2)); //caculates: r = v-2*(v*n)*n
		Ray ans = new Ray(p, r, n);
		return ans;
	}
	/**
	 * calculate the retracted ray
	 * @param gp
	 * @param v
	 * @param n
	 * @param nv
	 * @return SHKIFUT-Refracted ray 
	 */
	private Ray constructRefractedRay(Point p, Vector v, Vector n) { 
		Ray ans = new Ray(p, v, n);
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
	/*
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);//ray from point to the light source
		GeoPoint p = this.findClosestIntersection(lightRay);//find the closest intersection to the point 
		if (p==null) return true;//if there are no points between the point and the light- the point in unshaded
		if (p.point.distance(gp.point)<light.getDistance(gp.point)&&p.geometry.getMaterial().kT.equals(Double3.ZERO))//if the kT of the closest point is  0
			return false;//there is a shadow
		return true;
	}
	*/
	
	/**
	 * Calculate the 
	 * @param geoPoint
	 * @param ls
	 * @param l
	 * @param n
	 * @return
	 */
	private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);//ray from point to the light source		
		Double3 ktr = new Double3(1.0);
		List<GeoPoint> lst = scene.geometries.findGeoIntersections(lightRay,ls.getDistance(geoPoint.point)); //find the list of intersection points
		if (lst==null) return new Double3(1.0);//if there are no points between the point and the light- the geometry is transparent and does'nt effect the color of the point
		for (GeoPoint gp: lst) //go over every intersection point in the list
		{  
			//if (gp.point.distance(geoPoint.point)<ls.getDistance(geoPoint.point))//checks if the intersection point is closer to the point than the light source
				ktr=gp.geometry.getMaterial().kT.product(ktr); //multiply ktr by kT of the intersection
		}
		if (ktr.equals(Double3.ZERO)) // already close enough to 0
            return Double3.ZERO;
		return ktr;
		
	}
	
	/** calculate color 
	 * 
	 * @return color of the geo point
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k)
	{
		Color color = calcLocalEffects(gp,ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray.getDir(), level, k)); //if we arrived to the minimum level - 1 - we finish the recursion' and return the color we calculated,
		//if we haven't finish the recursion and didn't reach the minimum level - we call calcGlobalEffects that calculates the color (with recursion).
	}

	/**
	 * calculate color - Shell function to calcColor
	 * @param gp
	 * @param ray
	 * @return color of the geo point
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}
	/**
	 * calculate global effects
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
		Double3 kkr = material.kR.product(k);
		if (!(kkr.lowerThan(MIN_CALC_COLOR_K))) { //////////////////////
			color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);//calculate global effects for reflected ray
		}
		Double3 kkt = material.kT.product(k);
		if (!(kkt.lowerThan(MIN_CALC_COLOR_K))) { ////////////////////////
			color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));//calculate global effect for refracted ray
		}
		return color;
	}
	/**
	 * calculate the global effects
	 * @param ray
	 * @param level
	 * @param kx
	 * @param kkx
	 * @return
	 */
	private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
		GeoPoint gp = findClosestIntersection (ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level-1, kkx)).scale(kx); //if there are no intersection points - returns the background color,
		//and if there are - calculates the right color with calcColor
	}
	/**
	 * calculate the local effects-shadow, Diffusive, Specular, intensity
	 * @param gp
	 * @param ray
	 * @return
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
		Color color = gp.geometry.getEmission(); 
		Vector v = ray.getDir(); 
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v)); 
		if (nv == 0) return color; //////////////////////
		Material mat = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) { //go over all the light sources in the scene
			Vector l = lightSource.getL(gp.point); //find the vector from the light source to the point
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				Double3 ktr = transparency(gp, lightSource, l, n);
				if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) { /////////////////////////////
					Color iL = lightSource.getIntensity(gp.point).scale(ktr); //scales the intensity of the light source with the color (Double3) returns from transperency
					color = color.add(iL.scale(calcDiffusive(mat, nl)),iL.scale(calcSpecular(mat, n,l,nl,v))); //add the diffusive and the specular to the color
				}
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
	 * @param mat material of the geometry object
	 * @param n
	 * @param l
	 * @param nl
	 * @param v
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
	 * return the color of the intersections between the scene
	 */
	public  Color traceRay(Ray ray) {
		GeoPoint closestPoint = this.findClosestIntersection(ray);//find the closets intersection
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);//if there is no intersections-the color us the background' if not- call to calcColor
	}

}