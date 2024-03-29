package primitives;
/**
 * Vector - An object with length and direction
 *
 */
public class Vector extends Point {
	/**
	 * Constructor
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Vector(double d1, double d2, double d3)
	{
		super(d1, d2, d3);
		if(Double3.ZERO.equals(xyz))
		{
			throw new IllegalArgumentException("Vector zero is prohibited");
		}
	} 
	/**
	 * Constructor
	 * @param d
	 */
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
	
	@Override
	public Vector add(Vector v)
	{
		Vector ans = new Vector(this.xyz.add(v.xyz));
		return ans;
	}
	/**
	 * Vector multiplication by a number
	 * @param scalar
	 * @return
	 */
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
	
	/**
	 * Scalar product
	 * @param v
	 * @return
	 */
	public double dotProduct(Vector v)
	{
		Double3 temp = new Double3(this.xyz.product(v.xyz).d1, this.xyz.product(v.xyz).d2, this.xyz.product(v.xyz).d3);
		return (temp.d1 + temp.d2 + temp.d3);
	}
	/**
	 * Vector multiplication 
	 * @param v
	 * @return
	 */
	public Vector crossProduct(Vector v)
	{
		Vector ans = new Vector(xyz.d2*v.xyz.d3 - xyz.d3*v.xyz.d2,
				xyz.d3*v.xyz.d1 - xyz.d1*v.xyz.d3, xyz.d1*v.xyz.d2 - xyz.d2*v.xyz.d1);
		if(Double3.ZERO.equals(ans.xyz))
		{
			throw new IllegalArgumentException("All the point are in the same line");
		}
		return ans;
	}
	/**
	 * Vector length squared
	 * @return
	 */
	public double lengthSquared()
	{
		return this.dotProduct(this);
	}
	/**
	 * Vector length
	 * @return
	 */
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	/**
	 * A normalization operation that returns a new vector normalized in the same direction as the original vector
	 * @return
	 */
	public Vector normalize()
	{
		double temp = 1/this.length();
		Vector ans = this.scale(temp);
		return ans;
	}
	
	/**
	 * find vector normal to the vector
	 * @return  vector normal to the vector
	 */
	public Vector normal ()
	{  
		double v3 = -(this.xyz.d1+this.xyz.d2)/this.xyz.d3;
		Vector ans = new Vector(1,1,v3);
		return ans;
	}
	

}
