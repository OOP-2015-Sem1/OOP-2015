package robosim.motion;

import robosim.math.Vec2;
import robosim.math.Vec2_ROI;

public class OrientedPos implements 
	OrientedPos_ROI,
	OrientedPos_MotionUpdateI {
	
	private final Vec2 pos;
	private double theta;
	private final OrientedPos_ROI ref;
	
	public OrientedPos(
			Vec2 pos,
			double theta, OrientedPos_ROI ref) {
		this.pos = pos;
		this.theta = theta;
		this.ref = ref;
	}
	
	public OrientedPos() {
		this(new Vec2(0, 0), 0, null);
	}
	
	public OrientedPos(OrientedPos_ROI other) {
		this(new Vec2(other.getX(), other.getY()), other.getTheta(), other.getRef());
	}
	
	public void incrementBy(OrientedPos_ROI delta) {
		translateBy(delta.getPos());
		rotateBy(delta.getTheta());
	}
	
	public void translateBy(Vec2_ROI deltaPos) {
		pos.add(deltaPos);
	}
	
	public void translateTo(Vec2_ROI newPos) {
		pos.copy(newPos);
	}
	
	public void rotateBy(double deltaTheta) {
		theta += deltaTheta;
	}
	
	public void rotateTo(double newTheta) {
		theta = newTheta;
	}
	
	public OrientedPos_ROI get() {
		return (OrientedPos_ROI) this;
	}
	
	public Vec2_ROI getPos() {
		return (Vec2_ROI) pos;
	}
	
	
	public double getTheta() {
		return theta;
	}
	
	public Vec2 getAbsPos() {
		OrientedPos_ROI k = this;
		Vec2 absPos = new Vec2();
		
		Vec2 relPos = new Vec2();
		double relTheta = 0;
		
		while(k != null) {
			relPos.copy(k.getPos());
			
			if(k.getRef() != null) {
				relTheta = k.getRef().getTheta();
				relPos.rotateBy(-relTheta);
			}
			
			
			absPos.add(relPos);
			k = k.getRef();
		}
		
		return absPos;
	}
	
	public double getAbsOrientation() {
		OrientedPos_ROI k = this;
		double absTheta = 0;
		
		while (k != null) {
			absTheta += k.getTheta();
			k = k.getRef();
		}
		
		return absTheta;
	}
	
	public void incrementBy(double dx, double dy, double dtheta) {
		pos.add(dx, dy);
		theta += dtheta;
	}

	public void incrementByScaled(double timeStep, Vec2 dVel, double dAngularVel) {
		pos.addScaled(timeStep, dVel);
		theta += timeStep * dAngularVel;
	}

	public Vec2 getPointInThisRef(Vec2_ROI point) {
		Vec2 newPoint = new Vec2(point);
		
		newPoint.translateTo(pos);
		newPoint.rotateTo(theta);
		
		return newPoint;
	}

	@Override
	public double getX() {

		return pos.getX();
	}

	@Override
	public double getY() {

		return pos.getY();
	}

	@Override
	public double getAbsX() {

		return this.getAbsPos().getX();
	}

	@Override
	public double getAbsY() {
		return this.getAbsPos().getY();
	}

	@Override
	public OrientedPos_ROI getRef() {
		return ref;
	}
}
