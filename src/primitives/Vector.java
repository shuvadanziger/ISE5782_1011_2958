package primitives;

public class Vector extends Point {
	public Vector(double d1, double d2, double d3)
	{
		super(d1, d2, d3);
		if(Double3.ZERO.equals(xyz))
		{
			throw new IllegalArgumentException("Vector zero is prohibited");
		}
	}
	public Vector(Double3 d)
	{
		super(d);
		if(Double3.ZERO.equals(xyz))
		{
			throw new IllegalArgumentException("Vector zero is prohibited");
		}
	}
	public Double3 getXyz() {
		return xyz;
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	@Override
	public String toString() {
		return super.toString();
	}
	/*public Vector subtract(Vector v)
	{
		Point temp = (Point) v;
		return super.subtract(temp);
	}*/
	@Override
	public Vector add(Vector v)
	{
		/*Point temp = super.add(v);
		Vector ans = new Vector(temp.xyz);*/
		Vector ans = new Vector(this.xyz.add(v.xyz));
		return ans;
	}
	public Vector scale(double scalar)
	{
		try
		{
			Vector ans = new Vector(this.xyz.scale(scalar));
			return ans;
		}
		catch(IllegalArgumentException ex)
		{
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	public double dotProduct(Vector v)
	{
		//Double3 temp = this.xyz.product(v.xyz);
		Double3 temp = new Double3(this.xyz.product(v.xyz).d1, this.xyz.product(v.xyz).d2, this.xyz.product(v.xyz).d3);
		return (temp.d1 + temp.d2 + temp.d3);
	}
	public Vector crossProduct(Vector v)
	{
		Vector ans = new Vector(xyz.d2*v.xyz.d3 - xyz.d3*v.xyz.d2,
				xyz.d3*v.xyz.d1 - xyz.d1*v.xyz.d3, xyz.d1*v.xyz.d2 - xyz.d2*v.xyz.d1);
		return ans;
	}
	public double lengthSquared()
	{
		return this.dotProduct(this);
	}
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	public Vector normalize()
	{
		double temp = 1/this.length();
		Vector ans = this.scale(temp);
		return ans;
	}

}
