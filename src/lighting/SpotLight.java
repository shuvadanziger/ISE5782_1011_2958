package lighting;

import java.util.ArrayList;
import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util.*;
/**
 * point light with a direction
 * @author Shuva
 *
 */
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
		this.direction=v.normalize();
	}
	@Override
	public Color getIntensity(Point p) {
		//return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
		 Color ans = super.getIntensity(p);
		double dirL = (this.direction.dotProduct(getL(p)));
		if (0>dirL)
		{
			return ans.scale(0);
		}
		return ans.scale(dirL);
		 
		
	}
	@Override
	public Vector getL(Point p) {
		Vector l = super.getL(p);
		return l;
	}
	@Override
	public List<Ray> getV(Point p)
	{
		List<Ray> ans = new ArrayList();
		ans = super.getV(p);
		return ans;
	}

}
