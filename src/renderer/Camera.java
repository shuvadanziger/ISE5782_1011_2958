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
	/**
	 * return the height 
	 * @return
	 */
	public double getHight() {
		return hight;
	}	
	/**
	 * return the width
	 * @return
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * return the distance
	 * @return
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * Constructor
	 * @param p location
	 * @param v1 vector up
	 * @param v2 vector to
	 */
	public Camera(Point p, Vector v1,Vector v2)
	{
		if(v1.dotProduct(v2)!=0) //check if the vectors are orthogonal
		{
			throw new IllegalArgumentException("the vectors are not orthogonal");
		}
		if(v1.length()!=1||v2.length()!=1)//check if the vectors are normalized
		{
			throw new IllegalArgumentException("vectors are not normalized  ");
		}
		up=v1;
		to=v2;
		right=v1.crossProduct(v2).normalize();
		location=p;	 
	}
	/**
	 * set the width and the height, and return the camera
	 * @param width
	 * @param height
	 * @return
	 */
	public Camera setVPSize(double width, double height){
		this.width=width;
		this.hight=height;
		return this;
	}
	/**
	 * set the distance, and return the camera
	 * @param distance
	 * @return
	 */
	public Camera setVPDistance(double distance) {
		this.distance=distance;
		return this;
	}
	/**
	 * construct ray
	 * @param nX-Represents the amount of columns
	 * @param nY-Represents the amount of rows
	 * @param j
	 * @param i
	 * @return 
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		//Image center
		Point pC=location.add(to.scale(distance));
		//Ratio (pixel width & height)
		double rY=hight/nY;
		double rX=width/nX;
		//Pixel[i,j] center
		double yI=-(i-((nY-1)/2))*rY;
		double xJ=(j-((nX-1)/2))*rX;
		Point pIJ=pC; 
		if (xJ!=0)
			pIJ=pIJ.add(right.scale(xJ));
		if(yI!=0)
			pIJ=pIJ.add(up.scale(yI));
		
		Vector vIJ=pIJ.subtract(location);
		return new Ray(location, vIJ);
		
		

		

		
	}
	
	

}
