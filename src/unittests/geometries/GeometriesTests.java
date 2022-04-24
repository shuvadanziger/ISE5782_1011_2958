package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

class GeometriesTests {

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntsersections() {

		// ============ Equivalence Partitions Tests ==============
 
		// TC01: Some shapes intsersects but not all of them
		Geometries lst1 = new Geometries();
		Plane p1 = new Plane(new Point(1,0,0), new Vector(0,0,1));
		lst1. add(p1);
		Plane p2 = new Plane(new Point(0,0,3), new Vector(0,0,1));
		lst1.add(p2); 
		Triangle t = new Triangle(new Point(3,0,3), new Point(0,3,3), new Point(-3,-3,3));
		lst1.add(t);
		List<Point> result = lst1.findIntsersections(new Ray(new Point(0, 0, 2), new Vector(0,0,1)));
		assertEquals(2, result.size(), "Some shapes intsersects but not all of them");
 
		// =============== Boundary Values Tests ==================

		// TC10: Empty shapes collection
		Geometries lst2 = new Geometries();
		result = lst2.findIntsersections(new Ray(new Point(0,0,1), new Vector(1,0,0)));
		assertEquals(null, result, "Empty shapes collection");

		// TC11: None of the shapes intsersects
		p1 = new Plane(new Point(1,0,0), new Vector(0,0,1));
		lst2.add(p1);
		p2 = new Plane(new Point(0,0,1), new Vector(0,0,1));
		lst2.add(p2);
		t = new Triangle(new Point(3,0,1), new Point(0,3,1), new Point(-3,-3,1));
		lst2.add(t);
		result = lst2.findIntsersections(new Ray(new Point(0, 0, 2), new Vector(0,0,1)));
		assertEquals(null, result, "None of the shapes intsersects");

		// TC12: Only one shape intsersects
		Geometries lst3 = new Geometries();
		p1 = new Plane(new Point(1,0,0), new Vector(0,0,1));
		lst3.add(p1);
		p2 = new Plane(new Point(0,0,1), new Vector(0,0,1));
		lst3.add(p2);
		t = new Triangle(new Point(3,0,3), new Point(0,3,3), new Point(-3,-3,3));
		//p2 = new Plane(new Point(0,0,3), new Vector(0,0,1));
		lst3.add(t);
		result = lst3.findIntsersections(new Ray(new Point(0, 0, 2), new Vector(0,0,1)));
		assertEquals(1, result.size(), "Only one shape intsersects");
		
		// TC13: All of the shapes intsersects
		Geometries lst4 = new Geometries();
		p1 = new Plane(new Point(0,0,4), new Vector(0,0,1));
		lst4.add(p1);
		p2 = new Plane(new Point(0,0,3), new Vector(0,0,1));
		lst4.add(p2);
		t = new Triangle(new Point(3,0,3), new Point(0,3,3), new Point(-3,-3,3));
		lst4.add(t);
		result = lst4.findIntsersections(new Ray(new Point(0, 0, 2), new Vector(0,0,1)));
		assertEquals(3, result.size(), "All of the shapes intsersects");

 
	
	}

}