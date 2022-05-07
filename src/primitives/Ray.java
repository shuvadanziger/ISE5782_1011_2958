package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint; 
import geometries.Intersectable;
import lighting.AmbientLight;
/**
 * 
Ray - The group of points on a line that are on one side of a given point on a line. Defined by point and direction (unit vector)
 *
 */
public class Ray {
	final Point p0;
	final Vector dir;
	/**
	 * Constructor
	 * @param p
	 * @param v
	 */
	public Ray(Point p, Vector v)
	{
		p0 = new Point(p.xyz.d1, p.xyz.d2, p.xyz.d3);
		Vector temp = v.normalize();
		try 
		{
			dir = new Vector(temp.xyz.d1, temp.xyz.d2, temp.xyz.d3);
		}
		catch(IllegalArgumentException ex)
		{
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	/**
	 * return the point
	 * @return
	 */
	public Point getP0()
	{
		return p0;
	}
	/**
	 * return the direction of the ray
	 * @return
	 */
	public Vector getDir()
	{
		return dir;
	}
	@Override 
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Ray)) return false;
		Ray other = (Ray) obj;
		return other.p0.equals(other.p0) && other.dir.equals(other.dir);
	}
	@Override
	public String toString() {
		return "p0 = " + p0.toString() + ", dir = " + dir.toString();
	}
	/**
	 * finds the closest point to the start of the ray 
	 * @param lst list of points
	 * @return closest point to the start of the ray
	 */
	public Point findClosestPoint(List<Point> lst) {
		if(lst.size()==0) {
			return null;
		} 
		Point ans=lst.get(0);
		for(Point p : lst)
		{
			//double x=p.distance(p0);
			if(p.distance(p0)<ans.distance(p0)) {
				ans=p;
			}
		}
		return ans;
	}
	public GeoPoint findClosestGeoPoint(List<GeoPoint> lst) {
		if(lst.size()==0) {
			return null;
		} 
		GeoPoint ans=lst.get(0);
		for(GeoPoint p : lst)
		{
			//double x=p.distance(p0);
			if(p.point.distance(p0)<ans.point.distance(p0)) {
				ans=p;
			}
		}
		return ans;
	}
	

}
