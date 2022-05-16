package geometries;
import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

import static primitives.Util.alignZero;

public class Triangle extends Polygon {
	/**
	 * Constructor
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Point p1, Point p2, Point p3)
	{
		super(p1, p2, p3);
	}
	/**
	 * find all the intsersections between the triangle and the ray.
	 */
	@Override
	public List<Point> findIntsersections(Ray ray)
    {

		Vector v1 = vertices.get(0).subtract(ray.getP0());
		Vector v2 = vertices.get(1).subtract(ray.getP0());
		Vector v3 = vertices.get(2).subtract(ray.getP0());
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		double t1 = alignZero(ray.getDir().dotProduct(n1));
		double t2 = alignZero(ray.getDir().dotProduct(n2));
		double t3 = alignZero(ray.getDir().dotProduct(n3));
		if (t1>0 && t2>0 && t3>0)
		{
			return plane.findIntsersections(ray);
		}
		if (t1<0 && t2<0 && t3<0)
		{
			return plane.findIntsersections(ray);
		}
    	return null; 
    }
	
	public Vector getNormal(Point point)
	{
		return super.getNormal(point);
	}
	@Override
	public  List<GeoPoint> findGeoIntersectionsHelper(Ray ray){//,double maxDistance
		
		 Vector v1 = vertices.get(0).subtract(ray.getP0());
		Vector v2 = vertices.get(1).subtract(ray.getP0());
		Vector v3 = vertices.get(2).subtract(ray.getP0());
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		double t1 = alignZero(ray.getDir().dotProduct(n1));
		double t2 = alignZero(ray.getDir().dotProduct(n2)); 
		double t3 = alignZero(ray.getDir().dotProduct(n3));
		List<GeoPoint> lst= new ArrayList<GeoPoint>();
		if (t1>0 && t2>0 && t3>0) 
		{ 
		    List<GeoPoint> lst1= new ArrayList<GeoPoint>();
			lst1=this.plane.findGeoIntersections(ray);//,maxDistance
			if(lst1==null) {
			return null;
			}
			for(GeoPoint p:lst1) {
				lst.add(new GeoPoint(this,p.point));
			} 
			return lst;
		}
		if (t1<0 && t2<0 && t3<0)
		{
			List<GeoPoint> lst1= new ArrayList<GeoPoint>();
			lst1=this.plane.findGeoIntersections(ray);
			if(lst1==null) {
			return null;
			}
			for(GeoPoint p:lst1) {
				lst.add(new GeoPoint(this,p.point));
			} 
			return lst;
		}
    	return null;	
	}

}
