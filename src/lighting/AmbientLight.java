package lighting;

import primitives.*;

public class AmbientLight extends Light {
	/**
	 * constructor
	 * @param iA Original fill light (light intensity according to RGB components)
	 * @param kA Coefficient of attenuation of filler light
	 */
	public AmbientLight(Color iA, Double3 kA){
		super(iA.scale(kA));
	}
	public AmbientLight(Color iA, double kA){
		super(iA.scale(kA));
	}
	/** 
	 * Default constructor
	 */
	public AmbientLight() {
		super(Color.BLACK);
	}
}
