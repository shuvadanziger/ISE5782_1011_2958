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
	public Camera(Point p, Vector v1,Vector v2)
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
	}
	public Camera setVPSize(double width, double height){
		this.width=width;
		this.hight=height;
		return this;
	}
	public Camera setVPDistance(double distance) {
		this.distance=distance;
		return this;
	}
	public Ray constructRay(int nX, int nY, int j, int i) {
		//Image center
		Point pC=location.add(to.scale(distance));
		//Ratio (pixel width & height)
		double rY=hight/nY;
		double rX=width/nX;
		//Pixel[i,j] center
		double yI=-(i-(nY-1)/2)*rY;
		double xJ=(j-(nX-1)/2)*rX;
		Point pIJ=pC.add(right.scale(xJ).add(up.scale(yI)));
		
		Vector vIJ=pIJ.subtract(location);
		return new Ray(location, vIJ);
		
		

		

		
	}
	
	

}
