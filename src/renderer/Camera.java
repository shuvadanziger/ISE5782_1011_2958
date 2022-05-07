package renderer;

import java.util.MissingResourceException;

import primitives.*;

public class Camera {
	private Point location;
	private Vector up;
	private Vector to;
	private Vector right;
	private double hight;
	private double width;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;
	/**
	 * set imageWriter
	 * @param im
	 * @return camera
	 */
	public  Camera setImageWriter(ImageWriter im) {
		imageWriter=im;
		return this;
	}
	/**
	 * set rayTracerBase
	 * @param ray
	 * @return camera
	 */
	public Camera setRayTracer(RayTracerBase ray) {
		rayTracerBase=ray;
		return this;
	}
	/**
	 * check if something is empty, then paint the pixels 
	 */
	public void renderImage() {
		if(location==null||up==null||to==null||Double.isNaN(distance)||Double.isNaN(hight)||Double.isNaN(width)||right==null||imageWriter==null||rayTracerBase==null) 
		{ 
			throw new MissingResourceException("one or more of the fields are empty",null,null);
		}
		//throw new UnsupportedOperationException();
		for (int i=0; i<imageWriter.getNy();i++) { 
			for(int j=0;j<imageWriter.getNx();j++) {
				Color c= rayTracerBase.traceRay(constructRay(imageWriter.getNx(),imageWriter.getNy(),j,i));
				imageWriter.writePixel(j, i, c);
			} 
		} 
		 
	}
	/** 
	 * create grid 
	 * @param interval
	 * @param color
	 */
	public void printGrid(int interval, Color color) {
		if(imageWriter==null) {
			throw new MissingResourceException("imageWriter is empty",null,null);
		}
		for (int i=0; i<imageWriter.getNy();i++) {
			for(int j=0;j<imageWriter.getNx();j++) {
				if(i%interval==0||j%interval==0) {
					imageWriter.writePixel(j,i,color);
				}		
			} 
		}
	}
	/**
	 * write to image
	 */
	public void writeToImage() {
		if(imageWriter==null) {
			throw new MissingResourceException("imageWriter is empty",null,null);
		}
		imageWriter.writeToImage();
	}
	
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
		right=v2.crossProduct(v1).normalize();
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
