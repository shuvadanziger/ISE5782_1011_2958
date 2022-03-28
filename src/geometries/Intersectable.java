package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;

public interface Intersectable {

    public ArrayList<Point> findIntsersections(Ray ray);

}
