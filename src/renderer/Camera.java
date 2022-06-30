

package renderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.stream.IntStream;

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
	private int antiAliasing;
	private boolean multiThreading;
	private int adaptiveSS;

	
	/**
	 * set the number of rays in anti aliasing
	 * @param a number of rays in a row
	 * @return
	 */
	public Camera setAntialiasing(int a) {
		antiAliasing = a;
		return this;
	}
	/**
	 * set to use multi threading
	 * @param b
	 * @return
	 */
	public Camera setMultiThreading(boolean b) {
		multiThreading = b;
		return this;
	}
	/**
	 * set to use adaptive super sampling
	 * @param b
	 * @return
	 */
	public Camera setAdaptiveSS(int b) {
		adaptiveSS = b;
		return this;
	}
	/**
	 * set imageWriter
	 * @param im
	 * @return camera
	 */
	public Camera setImageWriter(ImageWriter im) {
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
	public Camera renderImage() { 
		
		if(location==null||up==null||to==null||Double.isNaN(distance)||Double.isNaN(hight)||Double.isNaN(width)||right==null||imageWriter==null||rayTracerBase==null) 
		{ 
			throw new MissingResourceException("one or more of the fields are empty",null,null);
		}
		if (multiThreading)
			return renderImageMultiThreading();
		if (adaptiveSS!=0) {
			return renderImageAdaptiveSuperSampling();
		}
		//throw new UnsupportedOperationException();
		for (int i=0; i<imageWriter.getNy();i++) { 
			for(int j=0;j<imageWriter.getNx();j++) {
				
				if (antiAliasing == 0) {
					Color c= rayTracerBase.traceRay(constructRay(imageWriter.getNx(),imageWriter.getNy(), j, i));
					imageWriter.writePixel(j, i, c);
				}
				else {
					Color c= calcColorAntiAliasing(constructReyAntiAliasing(imageWriter.getNx(),imageWriter.getNy(),j,i));
					imageWriter.writePixel(j, i, c);
				}
				

			} 
		} 
		return this;
		 
	}
	
	
	/**
	* render with threads, with or without anti alising
	 * @return camera
	 */
	public Camera renderImageMultiThreading() {
        Pixel.initialize(imageWriter.getNy(), imageWriter.getNx(), 60);
		IntStream.range(0, imageWriter.getNy()).parallel().forEach(i -> {
			IntStream.range(0, imageWriter.getNx()).parallel().forEach(j -> {
				//castRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
				if (antiAliasing == 0) {
					imageWriter.writePixel(j, i, rayTracerBase.traceRay(constructRay(imageWriter.getNx(),imageWriter.getNy(), j, i)));
				}
				else {
					imageWriter.writePixel(j, i, calcColorAntiAliasing(constructReyAntiAliasing(imageWriter.getNx(),imageWriter.getNy(),j,i)));
				}
				if (adaptiveSS!=0) {
					imageWriter.writePixel(j, i, adaptiveSamplingHelper(imageWriter.getNx(),imageWriter.getNy(), j, i));
				}
				Pixel.pixelDone();
				Pixel.printPixel();
				});
			});
        return this;
	}

	
	/**
	 * render the image using adaptive super sampling
	 * @return camera
	 */
	public Camera renderImageAdaptiveSuperSampling() {
		if(location==null||up==null||to==null||Double.isNaN(distance)||Double.isNaN(hight)||Double.isNaN(width)||right==null||imageWriter==null||rayTracerBase==null) 
		{ 
			throw new MissingResourceException("one or more of the fields are empty",null,null);
		}
	 // for each pixel
		for (int i = 0; i < imageWriter.getNx(); i++) {
			for (int j = 0; j < imageWriter.getNy(); j++) {
				imageWriter.writePixel(j, i, adaptiveSamplingHelper(imageWriter.getNx(),imageWriter.getNy(), j, i));
			}
		}
		return this;
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
	 * @return hight
	 */
	public double getHight() {
		return hight;
	}	
	/**
	 * return the width
	 * @return width
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * return the distance
	 * @return distance
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
		antiAliasing=0;
		multiThreading=false;
		adaptiveSS=0;
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
	
	/**
	* creates beam of rays around center of pixel 
	* @param nX number of row pixels
	* @param nY number of col pixels
	* @param j col index
	* @param i row index
	* @return list of rays (grid)
	*/
	private List<Ray> constructReyAntiAliasing(int nX, int nY, int j, int i) {	
	 	List<Ray> ans = new ArrayList<>(); 
		//Image center  
		Point pC=location.add(to.scale(distance));
		//Ratio (pixel width & height)
		double rY=hight/nY;
		double rX=width/nX;
		//Pixel[i,j] center
		double yI=-(i-((nY-1)/2))*rY;
		double xJ=(j-((nX-1)/2))*rX;
		//distance between the start of the ray in the pixel
		double dX=(double)rX/antiAliasing;
		double dY=(double)rY/antiAliasing;
		//the first point
		double firstX=xJ+((int)(antiAliasing/2))*dX;
		double firstY=yI+((int)(antiAliasing/2))*dY;
		Point pIJ=pC; 
		if (!Util.isZero(firstX))
			pIJ=pIJ.add(right.scale(firstX));
		if(!Util.isZero(firstY))
			pIJ=pIJ.add(up.scale(firstY));  
		Point p=pIJ; 
	    for (int c = 0; c < antiAliasing; c++)
	    {
	    	for (int b=0;b<antiAliasing;b++) {
	    		p=pIJ;
		    	if(!Util.isZero(c)) {
		    		p=p.add(right.scale(dX*c));
		    	}
		    	if(!Util.isZero(b)) {
		    		p=p.add(up.scale(dY*b));
		    	}
		    	
		        ans.add(new Ray(location, p.subtract(location)));
	    	}
	    }
	    return ans;	
	}
	/**
	 * Calculate the color for anti analyzing
	 * @param ray
	 * @return average color of the rays
	 */
	private Color calcColorAntiAliasing(List<Ray> ray)
	{
		Color ans=Color.BLACK;
		for (Ray r: ray) {
			ans = ans.add(rayTracerBase.traceRay(r));
		}
		ans = ans.reduce(antiAliasing*antiAliasing);
		return ans;
	}
	/**
	 * calculate the color of the pixel using adaptive super samling
	 * @param nX number of row pixels
	 * @param nY number of col pixels
	 * @param j col index
	 * @param i row index
	 * @return color of the pixel
	 */
	 private Color adaptiveSamplingHelper(int nX, int nY, int j, int i) {
	        Point imgCenter = location.add(to.scale(distance));
			//Ratio (pixel width & height)
	        double rY = hight / nY;
	        double rX = width / nX;
	        //Pixel[i,j] center
			double yI=-(i-((nY-1)/2))*rY;
			double xJ=(j-((nX-1)/2))*rX;
	       
	        Point pIJ = imgCenter;
	        if (xJ != 0) pIJ = pIJ.add(right.scale(xJ));
	        if (yI != 0) pIJ = pIJ.add(up.scale(yI));

	        Point leftUp = pIJ.add(right.scale(-rX/2)).add(up.scale(rY/2));//left up edge of the pixel 
	        Point rightUp = pIJ.add(right.scale(rX/2)).add(up.scale(rY/2));// right edge of the pixel
	        Point leftDown = pIJ.add(right.scale(-rX/2)).add(up.scale(-rY/2));//left down edge of the pixel
	        Point rightDown = pIJ.add(right.scale(rX/2)).add(up.scale(-rY/2));//right down edge of the pixel

	        Color leftUpColor = rayTracerBase.traceRay(new Ray(location, leftUp.subtract(location)));
	        Color rightUpColor = rayTracerBase.traceRay(new Ray(location, rightUp.subtract(location)));
	        Color leftDownColor = rayTracerBase.traceRay(new Ray(location, leftDown.subtract(location)));
	        Color rightDownColor = rayTracerBase.traceRay(new Ray(location, rightDown.subtract(location)));

	        return adaptiveSampling(pIJ, rX, rY,leftUpColor, rightUpColor,leftDownColor, rightDownColor, adaptiveSS);
	    }

	    /**
	     * calculate the color of the mini pixel using adaptive super sampling-recorsion
	     * 
	     * @param center center point of the mini pixel
	     * @param rX width of the mini pixel
	     * @param rY height of the mini pixel
	     * @param leftUpColor color of the upper left edge of the mini pixel
	     * @param rightUpColor color of the upper right edge of the mini pixel
	     * @param leftDownColor color of the lower left edge of the mini pixel
	     * @param rightDownColor color of the lower right edge of the  mini pixel
	     * @param depth recursion max level
	     * @return color of the mini pixel
	     */
	    private Color adaptiveSampling(Point center, double rX, double rY,Color leftUpColor, Color rightUpColor, Color leftDownColor, Color rightDownColor,int depth) {
	        if (depth == 0) {//if the level is 0 -Calculate the average color of the four edges 
	            return Color.BLACK.add(leftUpColor, rightUpColor, leftDownColor, rightDownColor).reduce(4);
	        }
	        if (leftUpColor.equals(rightUpColor) && leftUpColor.equals(leftDownColor) && leftUpColor.equals(rightDownColor)) {//If the color of all the edges is equal — return the color
	            return leftUpColor;
	        }
	        else {
	            Point pUp = center.add(up.scale(rY/2));//up middle point
	            Point pDown = center.add(up.scale(-rY/2));//down middle point
	            Point pLeft = center.add(right.scale(-rX/2));//left middle point
	            Point pRight = center.add(right.scale(rX/2));//right middle point
	            
	            Color centerColor = rayTracerBase.traceRay(new Ray(location, center.subtract(location)));
	            Color upColor = rayTracerBase.traceRay(new Ray(location, pUp.subtract(location)));	            
	            Color downColor = rayTracerBase.traceRay(new Ray(location, pDown.subtract(location)));
	            Color leftColor = rayTracerBase.traceRay(new Ray(location, pLeft.subtract(location)));
	            Color rightColor = rayTracerBase.traceRay(new Ray(location, pRight.subtract(location)));
	            boolean check = true;
	            if (leftUpColor.equals(leftDownColor) && leftUpColor.equals(rightUpColor) && !rightDownColor.equals(leftUpColor)) {//if all the colores are the same except the right down
	            	rightDownColor = adaptiveSampling(center.add(right.scale(rX/4)).add(up.scale(-rY/4)), rX/2, rY/2,  centerColor, rightColor, downColor, rightDownColor, depth-1);
	            	check = false;
	            }
	            if (leftUpColor.equals(leftDownColor) && leftUpColor.equals(rightDownColor) && !rightUpColor.equals(leftUpColor)) {//if all the colores are the same except the right up
	            	 rightUpColor = adaptiveSampling(center.add(right.scale(rX/4)).add(up.scale(rY/4)), rX/2, rY/2, upColor, rightUpColor, centerColor, rightColor, depth-1);
	            	 check = false;
	            }
	            if (leftUpColor.equals(rightDownColor) && leftUpColor.equals(rightUpColor) && !leftDownColor.equals(leftUpColor)) {//if all the colores are the same except the left down
	            	leftDownColor = adaptiveSampling(center.add(right.scale(-rX/4)).add(up.scale(-rY/4)), rX/2, rY/2, leftColor, centerColor, leftDownColor, downColor, depth-1);
	            	 check = false;
	            }
	            if (rightDownColor.equals(leftDownColor) && rightDownColor.equals(rightUpColor) && !leftUpColor.equals(rightDownColor)) {//if all the colores are the same except the left up
	            	leftUpColor = adaptiveSampling(center.add(right.scale(-rX/4)).add(up.scale(rY/4)), rX/2, rY/2, 
	                           leftUpColor, upColor, leftColor, centerColor, depth-1);
	            	 check = false;
	            }
	            if (check) {//If there is more than one color different from all the others
	            	rightDownColor = adaptiveSampling(center.add(right.scale(rX/4)).add(up.scale(-rY/4)), rX/2, rY/2, centerColor, rightColor, downColor, rightDownColor, depth-1);
	            	 rightUpColor = adaptiveSampling(center.add(right.scale(rX/4)).add(up.scale(rY/4)), rX/2, rY/2,upColor, rightUpColor, centerColor, rightColor, depth-1);
	            	 leftDownColor = adaptiveSampling(center.add(right.scale(-rX/4)).add(up.scale(-rY/4)), rX/2, rY/2,leftColor, centerColor, leftDownColor, downColor, depth-1);
	            	 leftUpColor = adaptiveSampling(center.add(right.scale(-rX/4)).add(up.scale(rY/4)), rX/2, rY/2, leftUpColor, upColor, leftColor, centerColor, depth-1);
	            }
		        Color color = Color.BLACK;
	            color = color.add(leftUpColor, rightUpColor, leftDownColor, rightDownColor);
	            return color.reduce(4);
	        }
	    }

	
	

}

