package primitives;

import java.util.Objects;
/**
 *  point- 3 coordinates
 *
 */
public class Point {
	final Double3 xyz;
	/**
	 * Constructor
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Point(double d1, double d2, double d3)
	{
		xyz = new Double3(d1, d2, d3);
	}
	/**
	 * Constructor
	 * @param d
	 */
	public Point(Double3 d)
	{
		xyz = new Double3(d.d1, d.d2, d.d3);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Point)) return false;
		Point other = (Point) obj;
		return this.xyz.equals(other.xyz);
	}
	@Override
	public String toString() {
		return "xyz = " + xyz.toString();
	}
	/**
	 * Subtraction 
	 * @param p
	 * @return
	 */
	public Vector subtract(Point p)
	{
		Vector ans = new Vector(xyz.subtract(p.xyz));
		return ans;
	}
	/**
	 * add vector to the point
	 * @param v
	 * @return
	 */
	public Point add(Vector v)
	{
		Point ans = new Point(this.xyz.add(v.xyz));
		return ans;
	}
	/**
	 * Distance between two points squared
	 * @param p
	 * @return
	 */
	public double distanceSquared(Point p)
	{
		return (xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1)+(xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2)+(xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3);
	}
	/**
	 * Distance between two points
	 * @param p
	 * @return
	 */
	public double distance(Point p)
	{
		double ans = Math.sqrt(distanceSquared(p));
		return ans;
	}
	public double getX() {
		return xyz.d1;
	}


}
