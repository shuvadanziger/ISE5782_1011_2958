package geometries;
import primitives.*;

public abstract class Geometry extends Intersectable {
	protected  Color emission=Color.BLACK; 
	private Material material=new Material();
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
	/**
	 * get material
	 * @return material
	 */
	public Material getMaterial() {
		return material;
	}
	/**
	 * set Material
	 * @param m Material
	 * @return Geometry
	 */
	public Geometry setMaterial(Material m) {
		material=m;
		return this;
	}

}
