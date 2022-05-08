package renderer;

import java.util.List;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
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
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	/** 
	 * 
	 * @return color of the scene
	 */
	private Color calcColor(GeoPoint p, Ray ray)
	{
		/**
		 * Geometry geometry=p.geometry;
		Point point=p.point;
		Material material=geometry.getMaterial();
		Color ans=Color.BLACK;
		for(LightSource light:scene.lights) {
			Vector lI=light.getL(point);
			Vector n=geometry.getNormal(point);
			Double3 kD=material.kD;
			Double3 kS=material.kS;
			int nSH=material.nShininess;
			Color iLI=light.getIntensity(point);
			Vector v=ray.getDir();
			Vector r=lI.add(-2*);
			double max=0;
			if(0<v.scale(-1).dotProduct(r)) {
				max=v.scale(-1).dotProduct(r);
			}

			Color c=iLI.scale(kD.scale(lI.crossProduct(n).length()).add(kS.scale(Math.pow(max,nSH))));
			ans.add(c);
		}
		 */
		 
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
			if (nl * nv > 0) { // sign(nl) == sing(nv)
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
