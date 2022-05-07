package geometries;

import java.util.List;
import primitives.*;
/**
 * 
 * @author Shuva
 *
 */
public abstract class Intersectable {
	/**
	 * Calculate the points of intersection of a geometry object with a ray
	 * @param ray 
	 * @return list of the intersections points
	 */
	public abstract List<Point> findIntsersections(Ray ray);
	/**
	 * Point on geometric object
	 * @author Shuva
	 *
	 */
	public static class GeoPoint {
		/**
		 * the geometry
		 */
	    public Geometry geometry;
	    /**
	     * the point
	     */
	    public Point point;
	    /**
	     * Constructor
	     * @param geo
	     * @param p
	     */
	    public GeoPoint(Geometry geo, Point p) {
	    	this.geometry=geo;
	    	this.point=p;
	    }
	    @Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof GeoPoint)) return false;
			GeoPoint other = (GeoPoint) obj;
			return this.point.xyz.equals(other.point.xyz);
		}
		@Override
		public String toString() {
			return "xyz = " + xyz.toString();
		}
	}

	
}
