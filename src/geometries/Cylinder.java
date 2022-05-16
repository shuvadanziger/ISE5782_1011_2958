package geometries;
import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

public class Cylinder extends Geometry {
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
	@Override
	public ArrayList<Point> findIntsersections(Ray ray)
    {
    	return null;
    }
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){//,double maxDistance
		return null;
	}


}
