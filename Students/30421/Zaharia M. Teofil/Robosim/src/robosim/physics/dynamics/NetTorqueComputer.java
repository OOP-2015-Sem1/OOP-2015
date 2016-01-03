package robosim.physics.dynamics;

import robosim.math.Vec2_ROI;
import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class NetTorqueComputer {
	// in
	private final Vec2_ROI relForceLeftWheel, relForceRightWheel;
	private final double wheelDist;
	// out
	private final DoubleRef netTorque;
	
	public NetTorqueComputer(Vec2_ROI relForceLeftWheel, Vec2_ROI relForceRightWheel, double wheelDist) {
		this.relForceLeftWheel = relForceLeftWheel;
		this.relForceRightWheel = relForceRightWheel;
		this.wheelDist = wheelDist;
		
		this.netTorque = new DoubleRef();
		update();
	}

	public void update() {
		double fLeft = relForceLeftWheel.getY();
		double fRight = relForceRightWheel.getY();
		double torque = ((fRight - fLeft) * wheelDist);
		
		netTorque.setVal(torque);
	}
	
	public Double_ROI getTorque() {
		return netTorque;
	}
}
