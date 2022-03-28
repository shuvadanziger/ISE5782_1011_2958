package geometries;
import java.util.ArrayList;
import java.util.List;

import primitives.*;
/**
 * Sphere (point and radius)
 *
 */
public class Sphere implements Geometry {
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

	public List<Point> findIntsersections(Ray ray)
    {
		Vector u=center.subtract(ray.getP0());
		double tm=ray.getDir().dotProduct(u);
		double d= Math.sqrt(u.lengthSquared()-tm*tm);
		if(d>=radius)
		{
			return null;
		}
		double th= Math.sqrt(radius*radius-d*d);
		double t1=tm+th;
		double t2=tm-th;
		ArrayList<Point> lst= new ArrayList<Point>();
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

