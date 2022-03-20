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
		
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Plane p = new Plane(new Point(0,0,0), new Point(1,0,0), new Point(0,1,0));
		Vector n = new Vector(0,0,1);
		assertEquals(n, p.getNormal(new Point(0,0,0)), "Bad normal tp plane");/////////////////////////////////////////
		assertEquals(1, p.getNormal(new Point(0,0,0)).length(), "The length of the normal is not 1");
	}

}
