package geometries;
import java.util.List;

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
	@Override
	public List<Point> findIntsersections(Ray ray)
    {

		Vector v1 = vertices.get(0).subtract(ray.getP0());
		Vector v2 = vertices.get(1).subtract(ray.getP0());
		Vector v3 = vertices.get(2).subtract(ray.getP0());
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		double t1 = lignZero(ray.getDir().dotProduct(n1));
		double t2 = lignZero(ray.getDir().dotProduct(n2));
		double t3 = lignZero(ray.getDir().dotProduct(n3));
		if (t1>0 && t2>0 && t3>0)
		{
			return plane.findIntsersections(rey);
		}
		if (t1<0 && t2<0 && t3<0)
		{
			return plane.findIntsersections(rey);
		}
    	return null;
    }
	public Vector getNormal(Point point)
	{
		return super.getNormal(point);
	}

}
