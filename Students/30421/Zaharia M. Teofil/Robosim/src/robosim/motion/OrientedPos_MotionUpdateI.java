package robosim.motion;

import robosim.math.Vec2;

public interface OrientedPos_MotionUpdateI {
	public void incrementByScaled(double timeStep, Vec2 dVel, double dAngularVel);
}
