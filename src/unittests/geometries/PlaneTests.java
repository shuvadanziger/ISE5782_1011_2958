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
 * @author Shuva
 *
 */
class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================

        // TC10: The first 2 points coalesce
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(1, 0, 0)), //
                "Constructed a plane with wrong values - 2 first points are identical");

        // TC11: All 3 points are on the same line
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(4, 0, 0)), //
                "Constructed a plane with wrong values - 3 points are on the same line");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.32
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Plane p = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector n1 = new Vector(0, 0, 1);
        Vector n2 = new Vector(0, 0, -1);

        // TC01: Test that the result of getNormal is proper
        assertTrue(p.getNormal(new Point(0, 0, 0)).equals(n1) || p.getNormal(new Point(0, 0, 0)).equals(n2), "Bad normal tp plane");

        // TC02: Test that the length of the normal is 1
        assertEquals(1, p.getNormal(new Point(0, 0, 0)).length(), "The length of the normal is not 1");
    }

    @Test
    void testFindIntsersections() {
        // ============ Equivalence Partitions Tests ==============

        Plane plane = new Plane(new Point(2, 0, 0), new Vector(1, 0, 0));

        // TC01: Ray intersects the plane
        List<Point> result = plane.findIntersections(new Ray(new Point(1,0,0), new Vector(1,0,0)));
        List<Point> check = new ArrayList<Point>(new Point(2,0,0));
        assertEquals(check, result, "Ray crosses plane, and the function didn't find the croos point");

        // TC02: Ray does not intersect the plane
        List<Point> result = plane.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertEquals(null, result, "Ray does not crosses plane, and the function think it does, and i don't know why, my partner did this part :)");

        // =============== Boundary Values Tests ==================



    }

}
