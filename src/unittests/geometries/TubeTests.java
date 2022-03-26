/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Tube;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Tube class
 * @author Shuva
 *
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		 // ============ Equivalence Partitions Tests ==============
		Tube t = new Tube(new Ray(new Point(0,0,0), new Vector(0,0,1)), 1);
		Point p1 = new Point(1,0,1);
		Vector n = new Vector(1,0,0);
		// TC01: Test that the result of getNormal is proper
		assertEquals(n, t.getNormal(p1), "Bad normal to tube");
		Point p2 = new Point(1,0,0);
		
		// =============== Boundary Values Tests ==================
        // TC10: The point is in front of the ray head
		assertThrows(IllegalArgumentException.class,
				() -> t.getNormal(p2), "calculates normal when the point is in front of the ray head");
	}

}
