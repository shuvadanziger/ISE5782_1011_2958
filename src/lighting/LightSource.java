package lighting;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * interface LightSource - for light sources
 * @author talia
 *
 */

public interface LightSource {
	final double DELTA = 1;
	/**
	 * find the light intensity at a point p
	 * @param p 
	 * @return the light intensity at a point p 
	 */
	public Color getIntensity(Point p);
	/**
	 * find the vector from the light source to the point
	 * @param p
	 * @return vector fron the light to the point
	 */
	public Vector getL(Point p);
	/**
	 * distance from the light to the point
	 * @param point
	 * @return the distance from the light to the point
	 */
	public double getDistance(Point point);
	public List<Ray> getV(Point p);
}
