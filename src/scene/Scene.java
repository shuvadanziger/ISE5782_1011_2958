package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.*;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.*;

public class Scene {
	public String name;
	public Color background; 
	public AmbientLight ambientLight;
	public Geometries geometries;
	public List<LightSource> lights;
	/**
	 * constructor
	 * @param sceneName
	 */
	public Scene(String sceneName) {
		this.name=sceneName;
		geometries=new Geometries();
		background=Color.BLACK;
		ambientLight=new AmbientLight();
		lights = new LinkedList<>();
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
	 * set geometries objects
	 * @param geometries
	 * @return scene
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries=geometries;
		return this;
	}
	/**
	 * set lights list
	 * @param l light
	 * @return scene
	 */
	public Scene setLights(List<LightSource> l) {
		this.lights=l;
		return this;
	}



}
