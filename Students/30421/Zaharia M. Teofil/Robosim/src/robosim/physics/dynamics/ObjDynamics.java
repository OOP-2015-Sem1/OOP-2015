package robosim.physics.dynamics;

import robosim.math.Vec2;
import robosim.math.Vec2_ROI;
import robosim.motion.OrientedPos;
import robosim.motion.OrientedPos_ROI;
import robosim.util.Bool_ROI;
import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class ObjDynamics {
	// in
	private final RobotSpec dynamicProps;
	private final Bool_ROI isColliding;
	private final Vec2_ROI netAbsForce;
	private final Double_ROI netTorque;
	private final Double_ROI TIME_STEP;
	// interior
	private final Vec2 vel;
	private final DoubleRef angularVel; 
	// out
	private final OrientedPos opos;

	public ObjDynamics(
			RobotSpec dynamicProps, 
			Bool_ROI isColliding, Vec2_ROI netAbsForce, Double_ROI netTorque,
			Double_ROI tIME_STEP, OrientedPos_ROI initialOpos) {
		this.dynamicProps = dynamicProps;
		this.isColliding = isColliding;
		this.netAbsForce = netAbsForce;
		this.netTorque = netTorque;
		TIME_STEP = tIME_STEP;
		
		this.opos = new OrientedPos(initialOpos);
		this.vel = new Vec2();
		this.angularVel = new DoubleRef();
	}

	public void update() {
		updateMotion();
		collisionOverride();
		updatePos();
	}
	
	public void updateMotion() {
		double m = dynamicProps.getMass();
		double I = dynamicProps.getAngularInertia();
		double dt = TIME_STEP.getVal();
		
		double fx = netAbsForce.getX();
		double fy = netAbsForce.getY();
		
		double dvx = fx / m * dt;
		double dvy = fy / m * dt;
		vel.add(dvx, dvy);
		
		double domega = netTorque.getVal() / I * dt;
		angularVel.add(domega);
	}
	
	private void updatePos() {
		opos.incrementByScaled(TIME_STEP.getVal(), vel, angularVel.getVal());
	}
	
	private void collisionOverride() {
		if (isColliding.getVal()) {
			vel.rotateBy(Math.PI);;
			angularVel.setVal(0.);
		}
	}
	
	public OrientedPos_ROI getOrientedPos() {
		return this.opos;
	}

	public Vec2_ROI getVel() {
		return vel;
	}

	public Double_ROI getAngularVel() {
		return angularVel;
	}
	
}
