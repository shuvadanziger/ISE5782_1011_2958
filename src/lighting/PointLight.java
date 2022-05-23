package lighting;

import java.util.ArrayList;
import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * light that come from one point- to every direction
 * @author Shuva
 *
 */
public class PointLight extends Light implements LightSource{
	private Point position;//location of the light
	private double kC;//permanent(const) Discount factor 
	private double kL;//liniar Discount factor
	private double kQ;//Squares Discount factor
	public PointLight setKc(double k) {
		this.kC=k;
		return this;
	}
	public PointLight setKl(double k) {
		this.kL=k;
		return this;
	}
	public PointLight setKq(double k) {
		this.kQ=k;
		return this;
	}
	/**
	 * constructor 
	 * @param c is the intensity of the light
	 * @param p is the position of the light
	 * @param kc is a discount factor
	 * @param kl is a discount factor
	 * @param kq is a discount factor
	 */
	public PointLight(Color c, Point p) {
		super(c);
		this.position=p;
		kC = 1;
		kL = 0;
		kQ = 0;
	}
	@Override
	public Color getIntensity(Point p) {
		double d = this.position.distance(p);
		double temp = this.kC+this.kL*d+this.kQ*d*d;
		Color ans=this.getIntensity().scale(1/temp);
		return ans;
	}
	@Override
	public Vector getL(Point p) {
		Vector ans=p.subtract(position).normalize();
		return ans;
	}
	public double getDistance(Point point) {
		return position.distance(point);
	}
	@Override
	public List<Ray> getV(Point p)
	{
		Vector n = getL(p);
		Vector temp = new Vector(p.subtract(Vector.ZERO).getXyz());
		double dot = n.dotProduct(temp);
		Vector v1 = n.normal(dot);
		Vector v2 = v1.crossProduct(n);
		v1.add(this.position.subtract(Vector.ZERO));
		v2.add(this.position.subtract(Vector.ZERO));
		
		List<Ray> ans = new ArrayList();
		for (int i=0; i<81; i++)
		{
			double mod = i%9;
			double partial = i/9;
			Vector up = v1.scale(mod);
			Vector side = v2.scale(partial);
			Point location = new Point(this.position.add(up).add(side).subtract(Vector.ZERO).getXyz());
			Ray answer = new Ray(location, new Vector(p.subtract(location).getXyz()).normalize());
			ans.add(answer);
		}
		return ans;
	}
	
	//public Ray r(Vector v1, Vector v2)
	//{
	//	
	//}

}
