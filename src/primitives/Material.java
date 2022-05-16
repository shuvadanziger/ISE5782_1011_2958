package primitives;

public class Material {
	/**
	 * Light scattering
	 */
	public Double3 kD=new Double3(0,0,0);
	/**
	 * Direct reflection
	 */
	public Double3 kS=new Double3(0,0,0);
	/**
	 * transparency
	 */
	public Double3 kT=new Double3(0);
	/**
	 * reflection
	 */
	public Double3 kR=new Double3(0);

	public int nShininess=0;
	/**
	 * set kd
	 * @param kd
	 * @return Material
	 */
	public Material setKd(Double3 kd) {
		this.kD=kd;
		return this;
	}
	/**
	 * set kt
	 * @param kt
	 * @return Material
	 */
	public Material setKt(Double3 kt) {
		this.kT=kt;
		return this;
	}
	/**
	 * set kr
	 * @param kr
	 * @return Material
	 */
	public Material setKR(Double3 kr) {
		this.kR=kr;
		return this;
	}
	/**
	 * set kd
	 * @param kd double
	 * @return Material
	 */
	public Material setKd(double kd) {
		this.kD=new Double3(kd);
		return this;
	}

	/**
	 * set ks
	 * @param ks
	 * @return Material
	 */
	public Material setKs(Double3 ks) {
		this.kS=ks;
		return this;
	}
	/**
	 * set ks
	 * @param ks double
	 * @return Material
	 */
	public Material setKs(double ks) {
		this.kS=new Double3(ks);
		return this;
	}
	/**
	 * set nShininess
	 * @param nshininess
	 * @return Material
	 */
	public Material setShininess(int nshininess) {
		this.nShininess=nshininess;
		return this;
	}
}