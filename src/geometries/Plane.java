package geometries;
import java.util.ArrayList;
import java.util.List;

import primitives.*;

import static primitives.Util.isZero;
import static primitives.Util.*;


/**
 * Plane (point and vector)
 *
 */
public class Plane extends Geometry {
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

	/**
	 * find the intsersections between the ray and the plane
	 */
	@Override
	public List<Point> findIntsersections(Ray ray)
    {
		if(isZero(normal.dotProduct(ray.getDir())))//if there are no intsersections
		{
			return null;
		}
		if(ray.getP0().equals(q0))//if the ray starts at the plane
		{
			return List.of(q0);
		}
		double t=normal.dotProduct(q0.subtract(ray.getP0()));
		if (t==0)
		{
			return List.of(ray.getP0());
		}
		t/=normal.dotProduct(ray.getDir());
		if (t<0)
		{
			return null;
		} 
		List<Point> ans=new ArrayList<Point>();
		ans.add(ray.getP0().add(ray.getDir().scale(t)));
    	return ans;//
		//
    }

}

