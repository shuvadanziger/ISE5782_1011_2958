package primitives;

import java.util.Objects;

public class Point {
	final Double3 xyz;
	public Point(double d1, double d2, double d3)
	{
		xyz = new Double3(d1, d2, d3);
	}
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
	//public Double3 getXyz() {
	//	return xyz;
	//}
	public Vector subtract(Point p)
	{
		Vector ans = new Vector(this.xyz.subtract(p.xyz));
		return ans;
	}
	public Point add(Vector v)
	{
		Point ans = new Point(this.xyz.add(v.xyz));
		//Point ans = new Point(xyz.d1+v.xyz.d1, xyz.d2+v.xyz.d2, xyz.d3+v.xyz.d3);
		return ans;
	}
	public double distanceSquared(Point p)
	{
		return (xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1)+(xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2)+(xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3);
	}
	public double distance(Point p)
	{
		double ans = Math.sqrt(distanceSquared(p));
		return ans;
	}


}
