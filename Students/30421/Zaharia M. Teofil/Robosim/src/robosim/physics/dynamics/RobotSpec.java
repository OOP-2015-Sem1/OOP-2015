package robosim.physics.dynamics;

public class RobotSpec {
	private final double
		width,
		height,
		mass,
		angularInertia,
		distToWheels;

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public RobotSpec(double width, double height, double mass, double angularInertia, double distToWheels) {
		this.width = width;
		this.height = height;
		this.mass = mass;
		this.angularInertia = angularInertia;
		this.distToWheels = distToWheels;
	}

	public double getMass() {
		return mass;
	}

	public double getAngularInertia() {
		return angularInertia;
	}

	public double getDistToWheels() {
		return distToWheels;
	};
	
}
