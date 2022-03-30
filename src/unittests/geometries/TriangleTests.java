/**
 *
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;


/**
 * Unit tests for geometries.Triangle class
 * @author Shuva
 *
 */
class TriangleTests {

	/**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
	@Test
	void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
		Triangle t=new Triangle(new Point(0,0,0),new Point(0,0,1),new Point(0,1,0));
		Point p=new Point(0,0,0);
		Vector normal1=new Vector(1,0,0);
		Vector normal2=new Vector(-1,0,0);
		Vector result=t.getNormal(p);
		// TC01: Test that the result of the normal vector of the point is proper 
		//assertEquals(t.getNormal(p),normal,"ERROR: Triangle.getNormal() does not work correctly");
		assertTrue(result.equals(normal1) || result.equals(normal2),"ERROR: Triangle.getNormal() does not work correctly");

}
	/**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
	@Test
	void testFindIntersections()
	{
		Triangle triangle=new Triangle(new Point(1,-1,1),new Point(-2,0,-1),new Point(0,2,0));
		
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: Ray intersects with a point within the triangle(1 point) 
		Point p=new Point(0.02,0,0.26);
		List<Point> result = triangle.findIntsersections(new Ray(new Point(-2, -4, 0),
                new Vector(2.02,4,0.26)));
		assertEquals(1, result.size(), "Wrong number of points");        
		assertEquals(result,List.of(p),"Ray crosses triangle");
		
		// TC02: Ray intersects with a point outside the triangle, on the same plane(0 points)
		// the intersection point is:(1.08,0.29,0.89);
		result = triangle.findIntsersections(new Ray(new Point(-2, -4, 0),
		                new Vector(3.08,4.29,0.89)));
        assertNull(result,"Ray's line out of triangle");  

		// TC03: Ray intersects with a point outside the triangle, on the same plane between two of edges of the triangle(0 points)
		// the intersection point is:(1.86,-1.69,1.62);
		result = triangle.findIntsersections(new Ray(new Point(-2, -4, 0),
				        new Vector(3.86,2.31,1.62)));
        assertNull(result,"Ray's line out of triangle"); 
        
        // =============== Boundary Values Tests ==================
		// TC04: Ray intersects with a point on the edge of the triangle(0 point) 
        // the intersection point is:(0.32,-0.77,0.54);
     	result = triangle.findIntsersections(new Ray(new Point(-2, -4, 0),
     				        new Vector(2.32,3.22,0.54)));
        assertNull(result,"Ray's line out of triangle");
        
        // TC05: Ray intersects with a point on the vertex of the triangle(0 point) 
        // the intersection point is:(-2,0,-1);
     	result = triangle.findIntsersections(new Ray(new Point(-2, -4, 0),
     				        new Vector(0,4,-1)));
        assertNull(result,"Ray's line out of triangle");
        
        // TC05: Ray intersects with a point on the continuation of the edge of the triangle(0 point) 
        // the intersection point is:(2.43,-1.48,1.95);
     	result = triangle.findIntsersections(new Ray(new Point(-2, -4, 0),
     				        new Vector(4.43,2.52,1.95)));
        assertNull(result,"Ray's line out of triangle");
	}




	}


