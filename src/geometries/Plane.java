package geometries;
import primitives.*;
/**
 * Plane (point and vector)
 *
 */
public class Plane implements Geometry {
	private Point q0;
	private Vector normal;
	/**
	 * Constructor
	 * @param p1
	 * @param p2
	 * @param p3
	 * Calculate the normal to the plane formed by the 3 points obtained
	 */
	public Plane (Point p1, Point p2, Point p3)
	{
		q0 = p1;
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		normal = v1.crossProduct(v2).normalize();
	}
	/**
	 * Constructor
	 * @param p
	 * @param v
	 */
	public Plane (Point p, Vector v)
	{
		q0 = p;
		normal = new Vector(v.getXyz());
	}
	/**
	 * Calculates the normalized normal to the plane - the value of the field normal
	 * @return
	 */
	public Vector getNormal(Point p)
	{
		return normal;

	}
	/**
	 * Returns the value of the field normal
	 * @return
	 */
	public Vector getNormal()
	{
		return normal;
	}

}
