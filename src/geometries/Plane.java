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
		normal = getNormal(p2);
	}
	public Plane (Point p, Vector v)
	{
		q0 = p;
		normal = new Vector(v.getXyz());
	}
	public Vector getNormal(Point p)
	{
		return null;
		//Vector v1 = new Vector(1,0,0);
		//Vector v2 = new Vector(0,1,0);
		//Vector n = v1.crossProduct(v2).normalize();
	}
	public Vector getNormal()
	{
		return normal;
	}

}
