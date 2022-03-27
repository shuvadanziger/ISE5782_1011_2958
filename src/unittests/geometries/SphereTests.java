/**
 *
 */
package unittests.geometries;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Sphere class
 * @author Shuva
 *
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Sphere s = new Sphere(new Point(0,0,0), 1);
		Vector n = new Vector(1,0,0);
		// TC01: Test that the result of getNormal is proper
		assertEquals(n, s.getNormal(new Point(1,0,0)), "Bad normal to sphere");
	}
}
