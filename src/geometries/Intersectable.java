package geometries;

import java.util.List;
import primitives.*;
/**
 * 
 * @author Shuva
 *
 */
public interface Intersectable {
	/**
	 * Calculate the points of intersection of a geometry object with a ray
	 * @param ray 
	 * @return list of the intersections points
	 */
	public List<Point> findIntsersections(Ray ray);
}
