package renderer;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

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
    * number of rays in beam for supersampling 
    */
	private int _nSS = 64; //////////////////////////////////////
	/**
    * maximum level of recursion for adaptive supersampling
    */
	private int _maxLevelAdaptiveSS = 3; /////////////////////////////////

   ///////////////////////////////////////////////////////////////////////////
   /**
    * setter for _nSS
    *
    * @param nSS value
    * @return this
    */
   public Camera setNSS(int nSS) {
       _nSS = nSS;
       return this;
   }

   /**
    * setter for _maxLevel
    *
    * @param maxLevelAdaptiveSS value
    * @return this
    */
   public Camera setMaxLevelAdaptiveSS(int maxLevelAdaptiveSS) {
       _maxLevelAdaptiveSS = maxLevelAdaptiveSS;
       return this;
   }
   ///////////////////////////////////////////
   
   
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
	
	//////////////////////////////////////////////////////////////////////////////
	/**
	* render the image using the image writer, using super sampling in the random method
	*/
	public void renderImageSuperSampling() {
		if(location==null||up==null||to==null||Double.isNaN(distance)||Double.isNaN(hight)||Double.isNaN(width)||right==null||imageWriter==null||rayTracerBase==null) 
		{ 
			throw new MissingResourceException("one or more of the fields are empty",null,null);
		}
		//throw new UnsupportedOperationException();
		for (int i=0; i<imageWriter.getNy();i++) { 
			for(int j=0;j<imageWriter.getNx();j++) {
				imageWriter.writePixel(j, i, castBeamSuperSampling(j, i));
			} 
		} 
	}
	/**
	* casts beam of rays around the center ray of pixel
	*
	* @param j col index
	* @param i row index
	* @return Color for a certain pixel
	*/
	private Color castBeamSuperSampling(int j, int i) {
		List<Ray> beam = constructBeamSuperSampling(imageWriter.getNx(), imageWriter.getNy(), j, i);
		Color color = Color.BLACK;
		// calculate average color of rays traced
		for (Ray ray : beam) {
			color = color.add(rayTracerBase.traceRay(ray));
			}
		return color.reduce(_nSS); 
	}
	/**
	* creates beam of rays around center of pixel randomly
	*
	* @param nX num of row pixels
	* @param nY num of col pixels
	* @param j col index
	* @param i row index
	* @return list of rays in beam
	*/
	private List<Ray> constructBeamSuperSampling(int nX, int nY, int j, int i) {
		List<Ray> beam = new LinkedList<>(); ////////////////////////////////////////////////////
		beam.add(constructRay(nX, nY, j, i));
		double ry = hight / nY;
		double rx = width / nX;
		double yScale = Util.alignZero((j - nX / 2d) * rx + rx / 2d); 
		double xScale = Util.alignZero((i - nY / 2d) * ry + ry / 2d); 
		Point pixelCenter = location.add(to.scale(distance)); // center
		if (!Util.isZero(yScale)) 
			pixelCenter = pixelCenter.add(right.scale(yScale));
		if (!Util.isZero(xScale))  
			pixelCenter = pixelCenter.add(up.scale(-1 * xScale));
		Random rand = new Random();
		// create rays randomly around the center ray
		for (int c = 0; c < _nSS; c++) { 
			// move randomly in the pixel
			double dxfactor = rand.nextBoolean() ? rand.nextDouble() : -1 * rand.nextDouble();
			double dyfactor = rand.nextBoolean() ? rand.nextDouble() : -1 * rand.nextDouble();
			double dx = rx * dxfactor;
			double dy = ry * dyfactor;
			Point randomPoint = pixelCenter;
			if (!Util.isZero(dx)) 
				randomPoint = randomPoint.add(right.scale(dx));
			if (!Util.isZero(dy)) 
				randomPoint = randomPoint.add(up.scale(-1 * dy));
			beam.add(new Ray(location, randomPoint.subtract(location)));
			}
		return beam;
	}
	////////////////////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////////////
	/**
     * render the image using the image writer, using adaptive supersampling
     * @return this, builder pattern
     */
    public void renderImageAdaptiveSuperSampling() {
    	if(location==null||up==null||to==null||Double.isNaN(distance)||Double.isNaN(hight)||Double.isNaN(width)||right==null||imageWriter==null||rayTracerBase==null) 
		{ 
			throw new MissingResourceException("one or more of the fields are empty",null,null);
		}
		//throw new UnsupportedOperationException();
		for (int i=0; i<imageWriter.getNy();i++) { 
			for(int j=0;j<imageWriter.getNx();j++) {
				imageWriter.writePixel(j, i, castBeamAdaptiveSuperSampling(j, i));
            }
        }
    }

    /**
     * casts beam of rays in pixel according to adaptive supersampling
     *
     * @param j col index
     * @param i row index
     * @return Color for a certain pixel
     */
    private Color castBeamAdaptiveSuperSampling(int j, int i) {
        Ray center = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
        Color centerColor = rayTracerBase.traceRay(center);
        return calcAdaptiveSuperSampling(imageWriter.getNx(), imageWriter.getNy(), j, i, _maxLevelAdaptiveSS, centerColor);
    }

    /**
     * calculates actual color using adaptive supersampling
     *
     * @param nX    num of rows
     * @param nY    num of cols
     * @param j     col index of pixel
     * @param i     row index of pixel
     * @param level level of recursion
     * @return color of pixel
     */
    private Color calcAdaptiveSuperSampling(int nX, int nY, int j, int i, int level, Color centerColor) {
        // recursion reached maximum level
        if (level == 0) {
            return centerColor;
        }
        Color color = centerColor;
        // divide pixel into 4 mini-pixels
        Ray[] beam = new Ray[]{constructRay(2 * nX, 2 * nY, 2 * j, 2 * i),
                constructRay(2 * nX, 2 * nY, 2 * j, 2 * i + 1),
                constructRay(2 * nX, 2 * nY, 2 * j + 1, 2 * i),
                constructRay(2 * nX, 2 * nY, 2 * j + 1, 2 * i + 1)};
        // for each mini-pixel
        for (int ray = 0; ray < 4; ray++) {
            Color currentColor = rayTracerBase.traceRay(beam[ray]);
            if (!currentColor.equals(centerColor))
                currentColor = calcAdaptiveSuperSampling(2 * nX, 2 * nY,
                        2 * j + ray / 2, 2 * i + ray % 2, level - 1, currentColor);
            color = color.add(currentColor);
        }
        return color.reduce(5);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    
    
	
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
