package geometries;
import java.util.List;

import primitives.*;
//import primitives.Double3;

public class Tube implements Geometry {
	private Ray axisRay;
	private double radius;
	/**
	 * Constructor
	 * @param a
	 * @param r
	 */
	public Tube(Ray a, double r)
	{
		axisRay = new Ray(a.getP0(), a.getDir());
		radius = r;
	}
	/**
	 * Calculates the normalized normal to the tube
	 * @return
	 */
	public Vector getNormal(Point p)
	{
		Vector v = p.subtract(axisRay.getP0());
		double ans = axisRay.getDir().dotProduct(v);
		if (ans==0)
		{
			throw new IllegalArgumentException("The point is in front of the head of the ray");
		}
		double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
		Point o = axisRay.getP0().add(axisRay.getDir().scale(t));
		Vector n = p.subtract(o).normalize();
		return n;
	}

	public List<Point> findIntsersections(Ray ray)
    {
    	return null;
    }


}
