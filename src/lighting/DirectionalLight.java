package lighting;

import java.util.ArrayList;
import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * Light with direction but without location - like the sun
 * @author Shuva
 *
 */
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
		this.direction=v.normalize();
	}
	@Override 
	public Color getIntensity(Point p) {
		return this.getIntensity();
	}
	@Override
	public Vector getL(Point p) {
		return this.direction;
	}
	public double getDistance(Point point) {
		return Double.POSITIVE_INFINITY;
	}
	public List<Ray> getV(Point p)
	{
		//Ray r = new Ray()
		List<Ray> ans = new ArrayList();
		return ans;
	}

}
