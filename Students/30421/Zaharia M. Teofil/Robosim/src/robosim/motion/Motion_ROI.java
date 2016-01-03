package robosim.motion;

import robosim.math.Vec2_ROI;

public interface Motion_ROI {
	public Motion_ROI get();
	public Vec2_ROI getVel();
	public double getAngularVel();
	public OrientedPos getIncrement(double timeStep);
}
