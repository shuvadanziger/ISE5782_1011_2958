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
	
	/*
	public Vector normal (double d)
	{
		double v3 = d-this.xyz.d1-this.xyz.d2;
		v3 /=this.xyz.d3;
		Vector ans = new Vector(1,1,v3);
		return ans;
	}
	*/
	
	public Vector normal (Point p)
	{  
		//double v1 = p.xyz.d1-1;
		//double v2 = p.xyz.d2-1;
		double v3 = -(this.xyz.d1+this.xyz.d2)/this.xyz.d3;
		//double v3 = d-this.xyz.d1-this.xyz.d2;
		//v3 /=this.xyz.d3;
		Vector ans = new Vector(1,1,v3);
		//ans.scale(-1);
		return ans;
	}
	/**
     * rotates vector on a given axis by a given angle
     * @param rotationAxis the rotation axis
     * @param angle the rotation angle (degrees)
     * @return the rotated vector
     */
    public Vector rotate(Vector rotationAxis, double angle) {
        // vrot = v*cos(theta) + cross(k,v)sin(theta) + k(k.'v)(1-cos(theta))
        // v - this
        // k - rotation axis
        // theta - rotation angle in degrees

        // convert to radians
        angle = Math.toRadians(angle);

        double x = this.xyz.d1;
        double y = this.xyz.d2;
        double z = this.xyz.d3;

        double u = rotationAxis.xyz.d1;
        double v = rotationAxis.xyz.d2;
        double w = rotationAxis.xyz.d3;

        double dotProduct = this.dotProduct(rotationAxis);

        double xRotated = u * dotProduct * (1d - Math.cos(angle))  // k*(k.'v)(1-cos(theta))
                + x * Math.cos(angle)                              // v*cos(theta)
                + (-w * y + v * z) * Math.sin(angle);              // cross(k,v)*sin(theta)

        double yRotated = v * dotProduct * (1d - Math.cos(angle))  // k*(k.'v)(1-cos(theta))
                + y * Math.cos(angle)                              // v*cos(theta)
                + (w * x - u * z) * Math.sin(angle);               // cross(k,v)*sin(theta)

        double zRotated = w * dotProduct * (1d - Math.cos(angle))  // k*(k.'v)(1-cos(theta))
                + z * Math.cos(angle)                              // v*cos(theta)
                + (-v * x + u * y) * Math.sin(angle);              // cross(k,v)*sin(theta)

        if((xRotated)==0 && (yRotated)==0 && (zRotated)==0)
            throw new IllegalArgumentException("rotation results with zero vector");

        return new Vector(xRotated, yRotated, zRotated);
    }

}
