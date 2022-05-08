package lighting;

import primitives.Color;

/**
 * class light 
 * @author talia
 *
 */

abstract class Light {
	private Color intensity;
	/**
	 * constructor 
	 * @param i 
	 */
	protected Light(Color i) {
		this.intensity = i;
	}
	/**
	 * @return value of the original light intensity
	 */
	public Color getIntensity() {
		return this.intensity;
	}

}
