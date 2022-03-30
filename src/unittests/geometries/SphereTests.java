/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

class SphereTests 
{

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		 // ============ Equivalence Partitions Tests ==============
		Sphere s=new Sphere(new Point(0,0,0),4);
		Point p=new Point(0,0,4);
		Vector normal=new Vector(0,0,1);
		// TC01: Test that the result of the normal vector of the point is proper 
		assertEquals(normal,s.getNormal(p),"ERROR: Sphere.getNormal() does not work correctly");
	}
	/**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point (1, 0, 0),1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                   "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntsersections(new Ray(new Point(-1, 0, 0),
                                                                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");        
        if (result.get(0).getX() > result.get(1).getX())
        {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(result,List.of(p1, p2),"Ray crosses sphere");
        
        // TC03: Ray starts inside the sphere (1 point)
        Point p3=new Point(2,0,0);
        List<Point> result3 = sphere.findIntsersections(new Ray(new Point(1.1, 0.1, 0.1),
                new Vector(0.9,-0.1,-0.1)));
        assertEquals(1, result3.size(), "Wrong number of points");        
        assertEquals(result3,List.of(p3),"Ray crosses sphere");  
        
        // TC04: Ray starts after the sphere (0 points)
        p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        result = sphere.findIntsersections(new Ray(new Point(-1, 0, 0),
                                                                new Vector(-3, -1, 0)));
        assertNull(result,"Ray's line out of sphere");   
 
        
      

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        p1=new Point(1,0,1);
        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),
                new Vector(-1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");        
        assertEquals(result,List.of(p1),"Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        p1=new Point(1,0,1);
        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),
                new Vector(1, 0, -1)));
        assertNull(result,"Ray's line out of sphere");  
        
        //check opposite direction to check if the ray intersects with the sphere
        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),
                new Vector(-1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points in the opposite direction");        
        assertEquals(result,List.of(p1),"Opposite ray crosses sphere");
        
        
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1=new Point(0,0,0);
        p2=new Point(2,0,0);
        result = sphere.findIntsersections(new Ray(new Point(3, 0, 0),
                        new Vector(-1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");        
        if (result.get(0).getX() > result.get(1).getX())
        {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(result,List.of(p1, p2),"Ray crosses sphere");
        
        // TC14: Ray starts at sphere and goes inside (1 points)
        p1=new Point(0,0,0);
        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),
                        new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");        
        assertEquals(result,List.of(p1),"Ray crosses sphere");
        //check that the ray in the opposite direction has no intersections with the sphere
        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),
                new Vector(1, 0, 0)));
        assertNull(result,"Ray's line out of sphere");  

        // TC15: Ray starts inside (1 points)
        p1=new Point(0,0,0);        
        result = sphere.findIntsersections(new Ray(new Point(0.5, 0, 0),
                        new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");        
        assertEquals(result,List.of(p1),"Ray crosses sphere");
      
        
        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntsersections(new Ray(new Point(1, 0, 0), new Vector(2, 0, 0)));
        Point po=new Point(2,0,0);  
        assertEquals(1, result.size(), "Wrong number of points"); 
        assertEquals(result,List.of(po),"Ray crosses sphere");
        
        
        
        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),
                new Vector(1, 0, 0)));
        assertNull(result,"Ray's line out of sphere");
        
     
        
        
        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntsersections(new Ray(new Point(3, 0, 0),
                new Vector(1, 0, 0)));
        assertNull(result,"Ray's line out of sphere");
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntsersections(new Ray(new Point(2, 1, 0),
                new Vector(0, -1, 0)));
        assertNull(result,"Ray's line out of sphere");
        
        // TC20: Ray starts at the tangent point
        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),
                new Vector(0, -1, 0)));
        assertNull(result,"Ray's line out of sphere");
        
        // TC21: Ray starts after the tangent point
        result = sphere.findIntsersections(new Ray(new Point(2, 1, 0),
                new Vector(0, 1, 0)));
        assertNull(result,"Ray's line out of sphere");
        
     
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntsersections(new Ray(new Point(3, 0, 0),
                new Vector(0, 1, 0)));
        assertNull(result,"Ray's line out of sphere");
}
}
