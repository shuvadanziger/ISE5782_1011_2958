package renderer;

import primitives.*;

public class Camera {
	private Point location;
	private Vector up;
	private Vector to;
	private Vector right;
	private double hight;
	private double width;
	private double distance;
	public double getHight() {
		return hight;
	}	
	public double getWidth() {
		return width;
	}
	public double getDistance() {
		return distance;
	}
	public Camera(Vector v1,Vector v2,Point p, double h, double w, double d )
	{
		if(v1.dotProduct(v2)!=0)
		{
			throw new IllegalArgumentException("the vectors are not orthogonal");
		}
		if(v1.length()!=1||v2.length()!=1)
		{
			throw new IllegalArgumentException("vectors are not normalized  ");
		}
		up=v1;
		to=v2;
		right=v1.crossProduct(v2).normalize();
		location=p;
		distance=d;
		width=w;
		hight=h;	
	}
	public Camera setVPSize(double width, double height){
		return this;
	}
	///
	public Camera setVPDistance(double distance) {
		
		return this;
	}
	
	
	

}
