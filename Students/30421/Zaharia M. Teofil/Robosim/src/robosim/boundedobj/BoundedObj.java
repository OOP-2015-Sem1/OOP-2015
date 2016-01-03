package robosim.boundedobj;

import robosim.motion.OrientedPos_ROI;

public class BoundedObj {
	// in
	protected final double width, height;
	protected final OrientedPos_ROI oPos;

	
	public BoundedObj(
			OrientedPos_ROI oPos,
			double width, 
			double height) {
		this.width = width;
		this.height = height;
		this.oPos = oPos;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	public double getX() {
		return oPos.getX();
	}
	
	public double getY() {
		return oPos.getY();
	}
	
	public double getTheta() {
		return oPos.getTheta();
	}
	
	
}
