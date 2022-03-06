package primitives;

import java.util.Objects;
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
	
	

}
