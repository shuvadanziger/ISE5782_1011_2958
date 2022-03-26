package geometries;
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
		
	}
	
}

