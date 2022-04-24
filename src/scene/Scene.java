package scene;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;

public class Scene {
	public String name;
	public Color background; 
	public AmbientLight ambientLight;
	public Geometries geometries;
	/**
	 * constructor
	 * @param sceneName
	 */
	public Scene(String sceneName) {
		this.name=sceneName;
		geometries=new Geometries();
		background=Color.BLACK;
		ambientLight=new AmbientLight();
	}

	/**
	 * set background
	 * @param back
	 * @return scene
	 */
	public Scene setBackground(Color back) {
		background=back;
		return this;	
	}
	/**
	 * set ambient light
	 * @param ambient
	 * @return scene
	 */
	public Scene setAmbientLight(AmbientLight ambient) {
		ambientLight=ambient;
		return this;
	}
	/**
	 * set geometries
	 * @param geometries
	 * @return scene
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries=geometries;
		return this;
	}



}
