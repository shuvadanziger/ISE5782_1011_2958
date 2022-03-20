/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * Unit tests for primitives.Point class
 * @author Shuva
 * 
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(2, 3, 4);
		Vector v = new Vector(1, 1, 1);
		assertEquals(v, p1.subtract(p2), "ERROR: subtract(): Point - Point does not work correctly");
		
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		Point p1 = new Point(1, 2, 3);
		Vector v = new Vector(-1, -2, -3);
		Point p2 = new Point(0, 0, 0);
		assertEquals(p2, p1.add(v), "ERROR: add() does not work correctly");
		
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		Point p1 = new Point(1,2,3);
		Point p2 = new Point(4,5,6);
		assertEquals(27, p1.distance(p2), "ERROR: distanceSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		Point p1 = new Point(0,4,5);
		Point p2 = new Point(0,1,1);
		assertEquals(5, p1.distance(p2), "ERROR: distance() wrong value");
	}

}
