/**
 * 
 */
package unittests;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import static primitives.Util.*;



/**
 * Unit tests for primitives.Vector class
 * @author Shuva
 *
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1,2,3);
		Vector v2 = new Vector(4,5,6);
		Vector v3 = new Vector(5,7,9);
		assertEquals(v3, v1.add(v2), "ERROR: add() does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v1 = new Vector(1,2,3);
		Vector v2 = new Vector(3,6,9);
		assertEquals(v2, v1.scale(3), "ERROR: scale() does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		 // ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
	    Vector v3 = new Vector(0, 3, -2);
	    Vector v2 = new Vector(-2, -4, -6);

	    //
		assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
		
		assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
	}

	/**
	 * 
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)),"crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class,
        		 () -> v1.crossProduct(v3),"crossProduct() for parallel vectors does not throw an exception");

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		Vector v1 = new Vector(1, 2, 3);
		assertEquals(14,v1.lengthSquared(),"ERROR: lengthSquared() wrong value");
		
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		Vector v1 = new Vector(0, 3, 4);
		assertEquals(5,v1.length(),"ERROR: length() wrong value");

	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalize();
		assertEquals(1,u.length(),"ERROR: the normalized vector is not a unit vector");
		assertThrows(IllegalArgumentException.class,() ->v.crossProduct(u) ,"ERROR: the normalized vector is not parallel to the original one");
        assertTrue(v.dotProduct(u) > 0,"ERROR: the normalized vector is opposite to the original one");	
	}

}
