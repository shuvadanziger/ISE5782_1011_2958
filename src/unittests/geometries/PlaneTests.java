/**
 *
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntsersections() {
        Plane plane = new Plane(new Point(2, 0, 0), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        List<Point> result = plane.findIntsersections(new Ray(new Point(1,0,0), new Vector(1,-2,0)));
        List<Point> check = new ArrayList<Point>();
        check.add(new Point(2,-2,0));
        assertEquals(check, result, "Ray crosses plane, and the function didn't find the croos point");

        // TC02: Ray does not intersect the plane
        result = plane.findIntsersections(new Ray(new Point(1,0,0), new Vector(0,0,1)));
        assertEquals(null, result, "Ray does not crosses plane, and the function think it does, and i don't know why, my partner did this part :)");

        // =============== Boundary Values Tests ==================

        // TC10: Ray is parallel to the plane and included in it
        result = plane.findIntsersections(new Ray(new Point(2,0,0), new Vector(0,0,1)));
        assertEquals(null, result, "Ray is parallel to the plane and included in it");

        // TC11: Ray is parallel to the plane and not included in it
        result = plane.findIntsersections(new Ray(new Point(1,0,0), new Vector(0,0,1)));
        assertEquals(null, result, "Ray parallel to the plane and not included in it, but the function think it does");

        // TC12: Ray is orthogonal to the plane and P0 is before the plane
        result = plane.findIntsersections(new Ray(new Point(1,0,0), new Vector(1,0,0)));
        check = new ArrayList<Point>();
        check.add(new Point(2,0,0));
        assertEquals(check, result, "Ray is orthogonal to the plane and P0 is before the plane - one intsersection point");

        // TC13: Ray is orthogonal to the plane and P0 is in the plane
        result = plane.findIntsersections(new Ray(new Point(2,0,1), new Vector(1,0,0)));
        check = new ArrayList<Point>();
        check.add(new Point(2,0,1));
        assertEquals(check, result, "Ray is orthogonal to the plane and P0 is in the plane - p0 is intsersection point");

        // TC14: Ray is orthogonal to the plane and P0 is after the plane
        result = plane.findIntsersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertEquals(null, result, "Ray is orthogonal to the plane and P0 is after the plane - no intsersection point");

        // TC15: Ray is neither orthogonal nor parallel to the plane and begins at the plane
        List<Point> result1 = plane.findIntsersections(new Ray(new Point(2,1,1), new Vector(1,-2,-2)));
        List<Point> check1 = new ArrayList<Point>();
        check1.add(new Point(2,1,1));
        assertEquals(check1, result1, "Ray is neither orthogonal nor parallel to the plane and begins at the plane");

        // TC16: Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane
        List<Point> result2 = plane.findIntsersections(new Ray(new Point(2,0,0), new Vector(1,-2,-2)));
        List<Point> check2 = new ArrayList<Point>();
        check2.add(new Point(2,0,0));
        assertEquals(check2, result2, "Ray is neither orthogonal nor parallel to the plane and begins in " +
                "the same point which appears as reference point in the plane");

    }

}
