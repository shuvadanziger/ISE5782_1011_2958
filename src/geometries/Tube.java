package geometries;
import primitives.*;

public class Tube implements Geometry {
	private Ray axisRay;
	private double radius;
	public Tube(Ray a, double r)
	{
		axisRay = new Ray(a.getP0(), a.getDir());
		radius = r;
	}
	public Vector getNormal(Point p)
	{
		return null;
	}
	

}
