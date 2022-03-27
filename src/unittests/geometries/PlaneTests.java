/**
 *
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for geometries.Plane class
 * @a uthor Shuva
 *
 */
class PlaneTests {
	/**
	 * Test method for {@link geometries.Plane#Plane(primitives.Point)}.
	 */
	@Test
	public void testConstructor()
	{
		// =============== Boundary Values Tests ==================

		// TC10: The first 2 points coalesce
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(0,0,0), new Point(0,0,0), new Point(1,0,0)), //
				"Constructed a plane with wrong values - 2 first points are identical");

		// TC11: All 3 points are on the same line
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(1,0,0), new Point(2,0,0), new Point(4,0,0)), //
				"Constructed a plane with wrong values - 3 points are on the same line");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.32
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============

		Plane p = new Plane(new Point(0,0,0), new Point(1,0,0), new Point(0,1,0));
		Vector n1 = new Vector(0,0,1);
		Vector n2 = new Vector(0,0,-1);
		// TC01: Test that the result of getNormal is proper
		assertTrue(p.getNormal(new Point(0,0,0)).equals(n1) || p.getNormal(new Point(0,0,0)).equals(n2), "Bad normal tp plane");
		////////////////assertEquals(n, p.getNormal(new Point(0,0,0)), "Bad normal tp plane");/////////////////////////////////////////

		// TC02: Test that the length of the normal is 1
		assertEquals(1, p.getNormal(new Point(0,0,0)).length(), "The length of the normal is not 1");
	}

}
