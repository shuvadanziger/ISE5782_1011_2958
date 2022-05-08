package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
	private Vector direction;
	/**
	 * constructor
	 * @param c is the intensity of the light
	 * @param p is the position of the light
	 * @param kc is a discount factor
	 * @param kl is a discount factor
	 * @param kq is a discount factor
	 * @param v is the direction of the light
	 */
	public SpotLight(Color c, Point p, Vector v) {
		super(c, p);
		this.direction=v;
	}
	@Override
	public Color getIntensity(Point p) {
		Color ans = super.getIntensity(p);
		double dirL = this.direction.dotProduct(super.getL(p));
		if (0>dirL)
		{
			ans.scale(0);
			return ans;
		}
		ans.scale(dirL);
		return ans;
	}
	@Override
	public Vector getL(Point p) {
		Vector l = super.getL(p);
		return l;
	}
}
