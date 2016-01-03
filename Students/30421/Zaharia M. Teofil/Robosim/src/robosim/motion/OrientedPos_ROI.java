package robosim.motion;

import robosim.math.Vec2;
import robosim.math.Vec2_ROI;

public interface OrientedPos_ROI {
	public OrientedPos_ROI get();
	public Vec2_ROI getPos();
	public double getX();
	public double getAbsX();
	public double getAbsY();
	public double getY();
	public double getTheta();
	public Vec2 getAbsPos();
	public double getAbsOrientation();
	public Vec2 getPointInThisRef(Vec2_ROI point);
	public OrientedPos_ROI getRef();
}
