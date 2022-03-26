package geometries;
import primitives.*;
/**
 * Plane (point and vector)
 *
 */
public class Plane implements Geometry {
	private Point q0;
	private Vector normal;
	public Plane (Point p1, Point p2, Point p3)
	{
		q0 = p1;
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		try {
			normal = v1.crossProduct(v2).normalize();
		}
		catch(IllegalArgumentException ex)
		{
			throw new IllegalArgumentException("all the points are in the same line");
		}
		
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		normal = v1.crossProduct(v2).normalize();
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		normal = v1.crossProduct(v2).normalize();
	}
	public Plane (Point p, Vector v)
	{
		q0 = p;
		normal = new Vector(v.getXyz());
	}
	public Vector getNormal(Point p)
	{
		return normal;
		
	}
	public Vector getNormal()
	{
		return normal;
	}
	

}
