package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;
import renderer.Camera;

import java.util.List;

/**
 *  testing class-integration between ray constructing from camera and ray's intersections points with geometric objects
 */
public class IntegrationTest {

    /**
     * camera for tests
     */
    private final Camera camera1 = new Camera(new Point(0,0,0),new Vector(0,-1,0), new Vector(0,0,-1))
            .setVPSize(3,3).setVPDistance(1);

    /**
     * camera for tests
     */
    private final Camera camera2 = new Camera(new Point(0,0,0.5),new Vector(0,-1,0),new Vector(0,0,-1))
            .setVPSize(3,3).setVPDistance(1);

    /**
     * camera for tests
     */
    private final Camera camera3 = new Camera(new Point(0,0,0),new Vector(0,0,1), new Vector(0,1,0))
            .setVPSize(3,3).setVPDistance(1);

    /** 
     * calculate the number of the intersections points between the camera and the geometric object
     * @param object geometric object
     * @param camera camera
     * @return the number of intersections between the camera and the object
     */
    public int numOfIntersections(Geometry object, Camera camera){
        int sum = 0;
        for (int i=0;i<3;i++){
            for (int j=0; j<3;j++){
                List<Point> intsersections = object.findIntsersections(camera.constructRay(3,3,j,i));
                if (intsersections != null)
                    sum += intsersections.size();
            }
        }
        return sum;
    }
    /**
     * test construct ray sphere intersections
     */
    @Test
    public void testConstructRaySphere() {
    	
        // TC01 sphere is smaller than view plane
        Sphere sphere = new Sphere(new Point(0,0,-3),1);
        assertEquals(2, numOfIntersections(sphere, camera1), 
        		"ERROR: wrong number of intersections points");

        // TC02 sphere is bigger than view plane
        Sphere sphere2 = new Sphere(new Point(0,0,-2.5),2.5);
        assertEquals(18, numOfIntersections(sphere2, camera2),
        		"ERROR: wrong number of intersections points");
        
        // TC03 view plane edges doesn't intersect with the sphere
        Sphere sphere3 = new Sphere(new Point(0,0,-2),2);
        assertEquals(10, numOfIntersections(sphere3,camera2),
                "ERROR: wrong number of intersections points");
        
        // TC04 the view plane is inside the sphere
        Sphere sphere4 = new Sphere(new Point(0,0,0),4);
        assertEquals(9, numOfIntersections(sphere4,camera2),
                "ERROR: wrong number of intersections points");       

        // TC05 the sphere is behind the view plane - no intersections points
        Sphere sphere5 = new Sphere(new Point(0,0,1), 0.5);
        assertEquals(0, numOfIntersections(sphere5,camera2),
                "ERROR: wrong number of intersections points");     
    } 

    /**
     * test construct ray plane intersections
     */
    @Test
    public void testConstructRayPlane() {
        // TC01 plane is parallel to the view plane
        Plane plane1 = new Plane(new Point(1,2,-3),
                new Vector(0,-2,0));
        assertEquals(9, numOfIntersections(plane1,camera3), 
        		"ERROR: wrong number of intersections points");

        // TC02 plane isn't parallel nor orthogonal to the view plane
        Plane plane2 = new Plane(new Point(1,2,-3),
                new Vector(1,-2,0));
        assertEquals(9, numOfIntersections(plane2,camera3),
        		"ERROR: wrong number of intersections points");

        // TC03 the third row doesn't intersect with the plane
        Plane plane3 = new Plane(new Point(0,2,0),
                new Vector(0,-0.5,-1));
        assertEquals(6, numOfIntersections(plane3,camera3), 
        		"ERROR: wrong number of intersections points");
    }
    
    /**
     * test construct ray triangle intersections
     */
    @Test
    public void testConstructRayTriangle() {
        //TC01 triangle is smaller or as the size of the center pixel of the view plane
        Triangle triangle1 = new Triangle(new Point(1,-1,-2),
                new Point(0,1,-1),
                new Point(-1,-1,-2));
        assertEquals(1, numOfIntersections(triangle1,camera1),
        		"ERROR: wrong number of intersections points");

        // TC02 triangle is intersecting with two rays from the view plane
        Triangle triangle2 = new Triangle(new Point(0,2,20),
                new Point(-0.5,1,-1), 
                new Point(0.5,1,-1));
        assertEquals(2, numOfIntersections(triangle2,camera3),
        		"ERROR: wrong number of intersections points"); 
    } 
}
