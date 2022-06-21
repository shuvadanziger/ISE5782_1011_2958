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
	public int softShadow;
	public double delta;
	public boolean adaptiveSuperSampling;
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
		softShadow=0;
		delta = 0;
		adaptiveSuperSampling=false;

	}
	/**
	 * set the number of rays in soft shadow
	 * @param rayNum number of rays in a row
	 * @return scene
	 */
	public Scene setSoftShadow(int rayNum) {
		softShadow=rayNum;
		return this;
	}
	/**
	 * set if the scene use adaptive Super-sampling
	 * @param a 
	 * @return scene
	 */
	public Scene setAdaptiveSuperSampling(boolean a) {
		this.adaptiveSuperSampling=a;
		return this;
	}
	/**
	 * set the distance between the rays in soft shadow
	 * @param d number of distance between two rays
	 * @return scene
	 */
	public Scene setDelta(double d) {
		delta = d;
		return this;
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
	 * @return scenecdx
	 */
	public Scene setLights(List<LightSource> l) {
		this.lights=l;
		return this;
	}



}
