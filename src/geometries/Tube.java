package geometries;
import primitives.*;
import primitives.Double3;

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
	public Vector getNormal(Point p)
	{
		double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
		Point o = axisRay.getP0().add(axisRay.getDir().scale(t));
		Vector n = p.subtract(o).normalize();
		return n;
		/////לעשות חריגה כשהנקודה המתקבלת נמצאת מול ראש הקרן - שזה הנקודה P0 של הקרן \
		
		
		//if(Double3.ZERO.equals(ans.xyz))
		//{
		//	throw new IllegalArgumentException("All the point are in the same line");
		//}
	}
	

}
