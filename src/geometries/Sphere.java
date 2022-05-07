package geometries;
import java.util.ArrayList;
import java.util.List;

import primitives.*;
/**
 * Sphere (point and radius)
 *
 */
public class Sphere extends Geometry {
	private Point center;
	private double radius;
	/**
	 * Constructor
	 * @param p1
	 * @param r
	 */
	public Sphere (Point p1, double r)
	{
		center = p1;
		radius = r;
	}
	/**
	 * Calculates the normalized normal to the sphere
	 * @return
	 */
	public Vector getNormal(Point p)
	{
		Vector n = p.subtract(center).normalize();
		return n;
	}
	@Override
	public List<Point> findIntsersections(Ray ray)
    {
		if(ray.getP0().equals(center))//if the ray starts at the center of the sphere
		{
			return List.of(center.add(ray.getDir().scale(radius)));
		}
		Vector u=center.subtract(ray.getP0());
		
		double tm=ray.getDir().dotProduct(u);
		double temp=tm*tm;
		double d= Math.sqrt(u.lengthSquared()-temp);
		if(d>=radius)//if d is bigger then the radius there are no intsersections.
		{
			return null;
		}
		double th= Math.sqrt(radius*radius-d*d);
		double t1=tm+th;
		double t2=tm-th;
		if(t1<=0 && t2<=0)
		{
			return null; 
		}
		List<Point> lst= new ArrayList<Point>();
		if(t1>0)
		{
			lst.add(ray.getP0().add(ray.getDir().scale(t1)));
		}
		if(t2>0)
		{
			lst.add(ray.getP0().add(ray.getDir().scale(t2)));
		}
		return lst;
	}


}

