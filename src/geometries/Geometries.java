package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable {

	private ArrayList<Intersectable> lst;


	public Geometries() {
		lst = new ArrayList<Intersectable>();
	}
	public Geometries(Intersectable... geometries)
	{
		lst = new ArrayList<Intersectable>(geometries);
		//return null;
	}
	public void add(Intersectable... geometries)
	{
		lst.add(geometries);
	}

	@Override
	public ArrayList<Point> findIntsersections(Ray ray)
	{
		return null;
	}
}
