package geometries;
import primitives.*;

public abstract class Geometry extends Intersectable {
	protected  Color emission=Color.BLACK; 
	public abstract Vector getNormal(Point p);
	/**
	 * get emission 
	 * @return emission 
	 */ 
	public Color getEmission() {
		return emission;
	}
	/**
	 * set emission
	 * @param em emission////////////////
	 * @return Geometry
	 */
	public Geometry setEmission(Color em) {
		this.emission=em;
		return this;
	}

}
