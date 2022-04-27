package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;

class RayTests {

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(List)}.
	 */
	@Test
	void testFindClosestPoint () {
		Ray ray=new Ray(new Point(1,0,0),new Vector(1,0,0));
		
	    // ============ Equivalence Partitions Tests ==============
	    // TC01: A point in the middle of the list is closest to the beginning of the ray
		 List<Point> lst1=new ArrayList<Point>();
		 lst1.add(new Point(3,0,0));
		 lst1.add(new Point(2,0,0));
		 lst1.add(new Point(4,0,0));
		 assertEquals(new Point(2,0,0), ray.findClosestPoint(lst1), "ERROR: findClosestPoint() does not work correctly");
	     
		 // =============== Boundary Values Tests ==================
		 //TC10:Empty list
		 List<Point> lst2=new ArrayList<Point>();
		 assertEquals(null,ray.findClosestPoint(lst2),"ERROR: Ray.findClosestPoint() for orthogonal vectors is not zero");
		 
		 //TC11:The first point is closest to the beginning of the ray
		 List<Point> lst3=new ArrayList<Point>();
		 lst3.add(new Point(2,0,0));
	     lst3.add(new Point(3,0,0));
		 lst3.add(new Point(4,0,0));
		 assertEquals(new Point(2,0,0),ray.findClosestPoint(lst3),"ERROR: Ray.findClosestPoint() for orthogonal vectors is not zero");
		 
		 //TC12:The last point is closest to the beginning of the ray
		 List<Point> lst4=new ArrayList<Point>();
		 lst4.add(new Point(4,0,0));
		 lst4.add(new Point(3,0,0));
		 lst4.add(new Point(2,0,0));
		 assertEquals(new Point(2,0,0), ray.findClosestPoint(lst4), "ERROR");



		 



	}

}
