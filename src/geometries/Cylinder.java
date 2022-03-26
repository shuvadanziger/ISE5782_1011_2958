package geometries;
import primitives.*;

public class Cylinder implements Geometry {
	private double height;
	/**
	 * Constructor
	 * @param h
	 */
	public Cylinder (double h)
	{
		height = h;
	}
	public Vector getNormal(Point p)
	{
		return null;
	}

}
