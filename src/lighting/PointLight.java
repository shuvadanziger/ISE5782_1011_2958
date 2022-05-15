package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
	private Point position;
	private double kC;
	private double kL;
	private double kQ;
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

}
