/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
<<<<<<< HEAD
=======
import geometries.Polygon;
>>>>>>> branch 'master' of https://github.com/shuvadanziger/ISE5782_1011_2958.git
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
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(0,0,0), new Point(0,0,0), new Point(1,0,0)), //
				"Constructed a plane with wrong values - 2 first points are identical");
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(1,0,0), new Point(2,0,0), new Point(4,0,0)), //
				"Constructed a plane with wrong values - 3 points are on the same line");
	}

	/**
<<<<<<< HEAD
	 * Test method for {@link geometries.Plane#Plane(primitives.Point)}.
	 */
	@Test
	public void testConstructor() {
		Plane p = new Plane(new Point(0,0,0), new Point(0,0,0), new Point(1,0,0));
		
	} 
	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
=======
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.32
>>>>>>> branch 'master' of https://github.com/shuvadanziger/ISE5782_1011_2958.git
	 */
	@Test
<<<<<<< HEAD
	void testGetNormal() {    
		Plane p = new Plane(new Point(0,0,0), new Point(1,0,0), new Point(0,1,0));
		Vector n = new Vector(0,0,1);
		
		assertEquals(n, p.getNormal(new Point(0,0,0)), "Bad normal tp plane");
		
=======
	void testGetNormal() {
		Plane p = new Plane(new Point(0,0,0), new Point(1,0,0), new Point(0,1,0));
		Vector n1 = new Vector(0,0,1);
		Vector n2 = new Vector(0,0,-1);
		assertTrue(p.getNormal(new Point(0,0,0)).equals(n1) || p.getNormal(new Point(0,0,0)).equals(n2), "Bad normal tp plane");
		//assertEquals(n, p.getNormal(new Point(0,0,0)), "Bad normal tp plane");/////////////////////////////////////////
>>>>>>> branch 'master' of https://github.com/shuvadanziger/ISE5782_1011_2958.git
		assertEquals(1, p.getNormal(new Point(0,0,0)).length(), "The length of the normal is not 1");
	}

}
