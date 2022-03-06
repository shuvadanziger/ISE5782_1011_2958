package geometries;
import primitives.*;
/**
 * Sphere (point and radius)
 *
 */
public class Sphere implements Geometry {
	private Point center;
	private double radius;
	public Sphere (Point p1, double r)
	{
		center = p1;
		radius = r;
	}
	public Vector getNormal(Point p)
	{
		return null;
	}
	
}

