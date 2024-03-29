package geometries;

import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
/**
 * class geometries-collection of geometries objects
 * @author Shuva
 *
 */
public class Geometries extends Intersectable {

	private ArrayList<Intersectable> lst;
	
	/**
	 * default constractor
	 */
	public Geometries() {
		lst = new ArrayList<Intersectable>();
	}
	/**
	 * Constractor 
	 * @param geometries
	 */ 
	public Geometries(Intersectable... geometries)
	{
		lst = new ArrayList<Intersectable>();

		for(Intersectable inter: geometries)
		{
			lst.add(inter); 
		}
	}
	/**
	 * add new geometries object to the collection
	 * @param geometries
	 */
	public void add(Intersectable... geometries)
	{
		for(Intersectable inter: geometries)
		{
			lst.add(inter);
		}
	}
	/**
	 * finds all the intsersections of the ray and the geometries object
	 */
	@Override 
	public List<Point> findIntsersections(Ray ray)
	{ 
		List<Point> result= new ArrayList<Point>();
		List<Point> temp;
		for (Intersectable i: lst) {
			temp = i.findIntsersections(ray);
			if (temp!=null)//if there are intsersections between the ray and the geometry object
			{
				for(Point p: temp) {
					if(p!=null) 
					{
					result.add(p);
					}
				}
			}
		}
		if (result.size()==0)
		{
			return null;
		} 
		return result;
	}
	//public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance){
		//return findGeoIntersectionsHelper(ray,maxDistance);
	//}
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance){//,double maxDistance
		List<GeoPoint> result= new ArrayList<GeoPoint>();
		List<GeoPoint> temp;
		for (Intersectable i: lst) { 
			temp = i.findGeoIntersections(ray, maxDistance);//maxDistance
			if (temp!=null)//if there are intsersections between the ray and the geometry object
			{
				for(GeoPoint p: temp) {
					if(p!=null) 
					{
					result.add(p);
					}
				}
			}
		}
		if (result.size()==0)
		{
			return null;
		}
		return result;
	
	}

}