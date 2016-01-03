package robosim.motion;

import robosim.math.Vec2;
import robosim.math.Vec2_ROI;

public class Motion implements Motion_ROI {
	private Vec2 vel;
	private double angularVel;
	private OrientedPos_MotionUpdateI targetPos;
	
	public Motion(Vec2 vel, 
			double angularVel,
			OrientedPos_MotionUpdateI targetPos) {
		this.vel = vel;
		this.angularVel = angularVel;
		this.targetPos = targetPos;
	}
	
	public Motion(OrientedPos_MotionUpdateI targetPos) {
		this(new Vec2(), 0, targetPos);
	}
	
	public void set(Vec2_ROI newVel, double newAngularVel) {
		this.vel.copy(newVel);
		this.angularVel = newAngularVel;
	}
	
	public void set(Motion_ROI other) {
		this.vel.copy(other.getVel());
		this.angularVel = other.getAngularVel();
	}
	
	public void setTargetPos(OrientedPos_MotionUpdateI targetPos) {
		this.targetPos = targetPos;
	}
	
	public void incremenTargetPos(double timeStep) {
		targetPos.incrementByScaled(timeStep, vel, angularVel);
	}
	
	
	public Motion_ROI get() {
		return (Motion_ROI) this;
	}

	public Vec2_ROI getVel() {
		return (Vec2_ROI) vel;
	}

	public double getAngularVel() {
		return angularVel;
	}
	
	public OrientedPos getIncrement(double timeStep) {
		return new OrientedPos(vel.getScaled(timeStep), angularVel * timeStep, null);
	}
	
}
