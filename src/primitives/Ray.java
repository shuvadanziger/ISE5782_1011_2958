package primitives;

import java.util.Objects;

public class Ray {
	final Point p0;
	final Vector dir;
	public Ray(Point p, Vector v)
	{
		p0 = new Point(p.xyz.d1, p.xyz.d2, p.xyz.d3);
		Vector temp = v.normalize();
		dir = new Vector(temp.xyz.d1, temp.xyz.d2, temp.xyz.d3);
	}
	public Point getP0()
	{
		return p0;
	}
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
