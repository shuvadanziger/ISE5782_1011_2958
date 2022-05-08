package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
	private Vector direction;
	/**
	 * constructor
	 * @param c is the intensity of the light
	 * @param v is the direction of the light
	 */
	public DirectionalLight(Color c, Vector v)
	{
		super(c);
		this.direction=v;
	}
	@Override
	public Color getIntensity(Point p) {
		return this.getIntensity();
	}
	@Override
	public Vector getL(Point p) {
		return this.direction;
	}
}
