package lighting;

import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

import primitives.Color;
import primitives.Double3;
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
	public ArrayList<Ray> softShadow(Point p)
	{
		//Creates a plane that its normal is the vector between the position and the point on the geometric object.
		//The length of the vectors that define the plane is delta 
		Vector n = getL(p);//n- vector from the light to the point
		Vector v1 = n.normal().scale(-1).normalize();//v1- vector normal to n
		Vector v2 = v1.crossProduct(n).scale(-1).normalize();//v2- vector normal to v1 and to n		
		Vector up=new Vector(v1.scale(LightSource.DELTA).getXyz());
		Vector side=new Vector(v2.scale(LightSource.DELTA).getXyz());

		ArrayList<Ray> ans = new ArrayList<Ray>();
		Point first=this.position.add(up.scale(4)).add(side.scale(4));//The first point on the grid that is on the plain 
																//(The center of the grid is the position of the light)
		Point temp;  
		for (int i=0;i<81;i++) {
			//Goes through all the points on the grid and takes out from each point a ray to the point on the geometric object
			temp=first;
			if((int)(i/9)==0) {
				if(i%9==0) {
					temp=first;
				}
				else {
					temp=first.add((side).scale(-i%9));
				}
			}  
			if(i%9==0 && (int)(i/9)!=0) {
				 temp=first.add(up.scale(-(int)(i/9)));
			}
			if((int)(i/9)!=0 && i%9!=0) {
				temp=first.add(up.scale(-(int)(i/9)));
				temp=temp.add((side).scale(-i%9));
			}	
			ans.add(i, new Ray(temp,p.subtract(temp).normalize()));
		}
		return ans;
	
	}

}
