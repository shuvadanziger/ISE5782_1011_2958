package lighting;

import primitives.*;

public class AmbientLight {
	/**
	 * constructor
	 * @param iA Original fill light (light intensity according to RGB components)
	 * @param kA Coefficient of attenuation of filler light
	 */
	public AmbientLight(Color iA, Double3 kA){
		intensity=iA.scale(kA);
	}
	/** 
	 * Default constructor
	 */
	public AmbientLight() {
		intensity=Color.BLACK;
	}
	/** 
	 * @return Value of ambient lighting intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
	private Color intensity;

}
