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
	private boolean adaptiveSS;
	private List<Color> colores = new ArrayList<>(); 

	private int maxLevelAdaptiveSS = 4;//maximum level of recursion for adaptive supersampling
   /**
    * setter for _maxLevel
    *
    * @param maxLevelAdaptiveSS value
    * @return this
    */
	public Camera setMaxLevelAdaptiveSS(int maxLevelAdaptiveSS) {
       this.maxLevelAdaptiveSS = maxLevelAdaptiveSS;
      
       return this;
	}
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
	public Camera setAdaptiveSS(boolean b) {
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
		if (multiThreading)
			return renderImageMultiThreading();
		if(location==null||up==null||to==null||Double.isNaN(distance)||Double.isNaN(hight)||Double.isNaN(width)||right==null||imageWriter==null||rayTracerBase==null) 
		{ 
			throw new MissingResourceException("one or more of the fields are empty",null,null);
		}
		//throw new UnsupportedOperationException();
		for (int i=0; i<imageWriter.getNy();i++) { 
			for(int j=0;j<imageWriter.getNx();j++) {
				if (adaptiveSS) {
					return renderImageAdaptiveSuperSampling();
				}
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
	 * @return
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
				if (adaptiveSS) {
					imageWriter.writePixel(j, i, castRayAdaptiveSuperSampling(j, i));
				}
				Pixel.pixelDone();
				Pixel.printPixel();
				});
			});
        return this;
	}

	
	/**
	* render the image using the image writer, using adaptive supersampling
	*/
	public Camera renderImageAdaptiveSuperSampling() {
		if(location==null||up==null||to==null||Double.isNaN(distance)||Double.isNaN(hight)||Double.isNaN(width)||right==null||imageWriter==null||rayTracerBase==null) 
		{ 
			throw new MissingResourceException("one or more of the fields are empty",null,null);
		}
	 // for each pixel
		for (int i = 0; i < imageWriter.getNx(); i++) {
			for (int j = 0; j < imageWriter.getNy(); j++) {
				imageWriter.writePixel(j, i, castRayAdaptiveSuperSampling(j, i));
			}
		}
		return this;
	}
	/** 
	* casts beam of rays in pixel according to adaptive supersampling
	*
	* @param j col index
	* @param i row index
	* @return Color for a certain pixel
	*/
	private Color castRayAdaptiveSuperSampling(int j, int i) {
		//List<Color> colores = new ArrayList<>(); 
		int nX=imageWriter.getNx();
		int nY=imageWriter.getNy();

		//Image center  
		Point pC=location.add(to.scale(distance));
		//Ratio (pixel width & height)
		double rY=hight/nY;
		double rX=width/nX;
		// Pixel[i,j] center
		double yI=-(i-((nY-1)/2))*rY;
		double xJ=(j-((nX-1)/2))*rX;
		/*
		 * //distance between the start of the ray in the pixel
		double dX=(double)rX/antiAliasing;
		double dY=(double)rY/antiAliasing;
			
		Point pIJ=pC; 
		double x,y;
		//Ray temp;
		for(int a=0;a<4;a++) {
			pIJ=pC; 
			if(a<3)
				y=yI+maxLevelAdaptiveSS*dY;
			else
				y=yI-maxLevelAdaptiveSS*dY;
			if(a==2||a==3)
				x=xJ-maxLevelAdaptiveSS*dX;
			else
				x=xJ+maxLevelAdaptiveSS*dX;
			if (!Util.isZero(x))
				pIJ=pIJ.add(right.scale(x));
			if(!Util.isZero(y))
				pIJ=pIJ.add(up.scale(y));  
			//temp=new Ray(location, pIJ.subtract(location));
			//ans.add(temp);
			//colores.add(rayTracerBase.traceRay(temp));
		}
		 */
		
		colores.clear();
		for(int a=0;a<81;a++) {
			colores.add(Color.NullColor);
	    }
		return calcAdaptiveSuperSampling(rX, rY, xJ,yI,4,4, maxLevelAdaptiveSS);

	}
	/**
	 * 
	 * @param rX pixel width
	 * @param rY pixel height
	 * @param i width index of the center
	 * @param j height index of the center
	 * @param level level of the recursion
	 * @param xJ center
	 * @param yI center
	 * @return if all the edges of the square are in the same color
	 */
	
	private boolean squareColor(double rX,double rY,int i,int j,int level,double xJ,double yI) {
		Point pC=location.add(to.scale(distance));
		Point pIJ=pC;
		if (xJ!=0)
			pIJ=pIJ.add(right.scale(xJ));
		if(yI!=0) 
			pIJ=pIJ.add(up.scale(yI));
		if(colores.get(9*(j-level)+i-level).equals(Color.NullColor)) {//up left
			colores.add(9*(j-level)+i-level, rayTracerBase.traceRay(new Ray(location, pIJ.add(right.scale(-rX/2)).add(up.scale(rY/2)).subtract(location))));
		}
		if(colores.get(9*(j-level)+i+level).equals(Color.NullColor)) {//up right
			colores.add(9*(j-level)+i+level, rayTracerBase.traceRay(new Ray(location, pIJ.add(right.scale(rX/2)).add(up.scale(rY/2)).subtract(location))));
		}
		
		if(colores.get(9*(j+level)+i-level).equals(Color.NullColor)) {//down left
			colores.add(9*(j+level)+i-level, rayTracerBase.traceRay(new Ray(location, pIJ.add(right.scale(-rX/2)).add(up.scale(-rY/2)).subtract(location))));
		}
		if(colores.get(9*(j+level)+i+level).equals(Color.NullColor)) {//down right
			colores.add(9*(j+level)+i+level, rayTracerBase.traceRay(new Ray(location, pIJ.add(right.scale(rX/2)).add(up.scale(-rY/2)).subtract(location))));
		}
		if(colores.get(9*(j-level)+i-level).equals(colores.get(9*(j-level)+i+level))&&
				colores.get(9*(j-level)+i+level).equals(colores.get(9*(j+level)+i+level))&&
				colores.get(9*(j+level)+i+level).equals(colores.get(9*(j+level)+i-level))) {
			return true;
		}
		return false;
	}
	
   
	/**
	* calculates actual color using adaptive supersampling
	*
	* @param nX num of rows
	* @param nY num of cols
	* @param j col index of pixel
	* @param i row index of pixel
	* @param level level of recursion
	* @return color of pixel
	*/
	private Color calcAdaptiveSuperSampling(double rX, double rY, double xJ, double yI, int i,int j,int level) {
		if(level==0) {
			return colores.get(9*j+i);
		}
		if(squareColor(rX,rY,i,j,level,xJ,yI)) {
			return colores.get(j-level+i-level); 
		}
		Point pC=location.add(to.scale(distance));
		Point pIJ=pC;
		if (xJ!=0)
			pIJ=pIJ.add(right.scale(xJ));
		if(yI!=0)
			pIJ=pIJ.add(up.scale(yI));
		colores.add(9*j+i,rayTracerBase.traceRay(new Ray(location, pIJ.subtract(location))));
		if(colores.get(9*(j-level)+i).equals(Color.NullColor)) {//up
			colores.add(9*(j-level)+i, rayTracerBase.traceRay(new Ray(location, pIJ.add(up.scale(rY/2)).subtract(location))));
		}
		if(colores.get(9*j+i+level).equals(Color.NullColor)) {//right
			colores.add(9*j+i+level, rayTracerBase.traceRay(new Ray(location, pIJ.add(right.scale(rX/2)).subtract(location))));
		}
		if(colores.get(9*(j+level)+i).equals(Color.NullColor)) {//down
			colores.add(9*(j+level)+i, rayTracerBase.traceRay(new Ray(location, pIJ.add(up.scale(-rY/2)).subtract(location))));
		}
		if(colores.get(9*j+i-level).equals(Color.NullColor)) {// left
			colores.add(9*j+i-level, rayTracerBase.traceRay(new Ray(location, pIJ.add(right.scale(-rX/2)).subtract(location))));
		}
		Color c=Color.BLACK;
		c=c.add(calcAdaptiveSuperSampling(rX/2,rY/2,xJ-rX/4,yI+rY/4,(int)Math.ceil(i-(level/2)),(int)Math.floor(j-(level/2)),level/2));//up left
		c=c.add(calcAdaptiveSuperSampling(rX/2,rY/2,xJ+rX/4,yI+rY/4,(int)Math.ceil(i+(level/2)),(int)Math.floor(j-(level/2)),level/2));//up right
		c=c.add(calcAdaptiveSuperSampling(rX/2,rY/2,xJ+rX/4,yI-rY/4,(int)Math.ceil(i+(level/2)),(int)Math.floor(j+(level/2)),level/2));//down right
		c=c.add(calcAdaptiveSuperSampling(rX/2,rY/2,xJ-rX/4,yI-rY/4,(int)Math.ceil(i-(level/2)),(int)Math.floor(j+(level/2)),level/2));//down left
		return c.reduce(4);
		/*
		 *	Color ans=Color.BLACK;
		if (level == 0) {//if the level of the recursion is 0
			return ans.add(colores.get(0),colores.get(1),colores.get(2),colores.get(3)).reduce(4);	
		}
		if (xJ!=0)
			pIJ=pIJ.add(right.scale(xJ));
		if(yI!=0)
			pIJ=pIJ.add(up.scale(yI));
		colores.add(rayTracerBase.traceRay(new Ray(location, pIJ.subtract(location))));//add the center of the pixel(5)
		pIJ=pC;
		if(yI+level*dY!=0)
			pIJ=pIJ.add(up.scale(yI+level*dY));
		colores.add(rayTracerBase.traceRay(new Ray(location, pIJ.subtract(location))));//up-6
		pIJ=pC;
		if(xJ+level*dX!=0)
			pIJ=pIJ.add(up.scale(xJ+level*dX));
		colores.add(rayTracerBase.traceRay(new Ray(location, pIJ.subtract(location))));//right-7
		pIJ=pC;
		if(yI-level*dY!=0)
			pIJ=pIJ.add(up.scale(yI-level*dY));
		colores.add(rayTracerBase.traceRay(new Ray(location, pIJ.subtract(location))));//down-8
		pIJ=pC;
		if(xJ-level*dX!=0)
			pIJ=pIJ.add(up.scale(xJ-level*dX));
		colores.add(rayTracerBase.traceRay(new Ray(location, pIJ.subtract(location))));//left-9
		int l=level/2;
		List<Color> c = new ArrayList<>(); 
		c.add(colores.get(0));
		c.add(colores.get(5));
		c.add(colores.get(4));
		c.add(colores.get(8));
		ans=ans.add(calcAdaptiveSuperSampling(dX,dY,xJ-l*dX,yI+l*dY,l,c));//up left square
		c.clear();
		c.add(colores.get(5));
		c.add(colores.get(1));
		c.add(colores.get(4));
		c.add(colores.get(6));
		ans=ans.add(calcAdaptiveSuperSampling(dX,dY,xJ+l*dX,yI+l*dY,l,c));//up right square
		c.clear();
		c.add(colores.get(4));
		c.add(colores.get(6));
		c.add(colores.get(2));
		c.add(colores.get(7));
		ans=ans.add(calcAdaptiveSuperSampling(dX,dY,xJ+l*dX,yI-l*dY,l,c));//down right square
		c.clear();
		c.add(colores.get(8));
		c.add(colores.get(4));
		c.add(colores.get(7));
		c.add(colores.get(3));
		ans=ans.add(calcAdaptiveSuperSampling(dX,dY,xJ-l*dX,yI-l*dY,l,c));//down left square
		return ans.reduce(4);
 
		 */
		
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
		antiAliasing=0;
		multiThreading=false;
		adaptiveSS=false;
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
	*
	* @param nX number of row pixels
	* @param nY number of col pixels
	* @param j col index
	* @param i row index
	* @return list of rays in beam
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
	 * @return
	 */
	private Color calcColorAntiAliasing(List<Ray> ray)
	{
		Color ans=Color.BLACK;
		for (Ray r: ray) {
			ans = ans.add(rayTracerBase.traceRay(r));
		}
		ans = ans.reduce(81);
		return ans;
	}
	
	

}
