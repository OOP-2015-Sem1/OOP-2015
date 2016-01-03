package robosim.physics.dynamics;

import robosim.math.Vec2;
import robosim.math.Vec2_ROI;

/**
 * Assumes force comes from 2 wheels
 * This module can be substituted for any other
 * which provides a Vec2 force
 * e.g. for 4 wheel drive
 * 
 * Future dev:
 * integrated collision force dynamics with
 * inellastic collisions
 *
 */

public class NetForceComputer {
	// in
	private final Vec2_ROI absForceLeftWheel, absForceRightWheel;
	// out
	private final Vec2 netAbsForce;
	
	public NetForceComputer(Vec2_ROI relForceLeftWheel, Vec2_ROI relForceRightWheel) {
		this.absForceLeftWheel = relForceLeftWheel;
		this.absForceRightWheel = relForceRightWheel;
		
		netAbsForce = new Vec2();
		update();
	}

	public void update() {
		double fx = absForceLeftWheel.getX() + absForceRightWheel.getX();
		double fy = absForceLeftWheel.getY() + absForceRightWheel.getY();
		
		netAbsForce.set(fx, fy);
	}
	
	public Vec2_ROI getForce() {
		return netAbsForce;
	}
}
