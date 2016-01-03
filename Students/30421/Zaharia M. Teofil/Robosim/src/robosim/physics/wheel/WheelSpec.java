package robosim.physics.wheel;

public class WheelSpec {
	private final double
		radius, momentOfInertia,
		slipRatio1, sliprRatio2,
		slipAngle1, slipAngle2,
		mu1Long, mu2Long,
		mu1Lat, mu2Lat;

	public WheelSpec(double radius, double momentOfInertia) {
		this(radius, momentOfInertia, 0, 0, 0, 0, 0 ,0, 0, 0);
	}

	public WheelSpec(
			double radius, double momentOfInertia, 
			double slipRatio1, double sliprRatio2, 
			double slipAngle1, double slipAngle2, 
			double mu1Long, double mu2Long, 
			double mu1Lat, double mu2Lat) {
		this.radius = radius;
		this.momentOfInertia = momentOfInertia;
		this.slipRatio1 = slipRatio1;
		this.sliprRatio2 = sliprRatio2;
		this.slipAngle1 = slipAngle1;
		this.slipAngle2 = slipAngle2;
		this.mu1Long = mu1Long;
		this.mu2Long = mu2Long;
		this.mu1Lat = mu1Lat;
		this.mu2Lat = mu2Lat;
	}

	public double getMomentOfInertia() {
		return momentOfInertia;
	}

	public double getRadius() {
		return radius;
	}

	public double getSlipRatio1() {
		return slipRatio1;
	}

	public double getSliprRatio2() {
		return sliprRatio2;
	}

	public double getSlipAngle1() {
		return slipAngle1;
	}

	public double getSlipAngle2() {
		return slipAngle2;
	}

	public double getMu1Long() {
		return mu1Long;
	}

	public double getMu2Long() {
		return mu2Long;
	}

	public double getMu1Lat() {
		return mu1Lat;
	}

	public double getMu2Lat() {
		return mu2Lat;
	}
	
	
}
