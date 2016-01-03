package robosim.util;

import robosim.math.Vec2_ROI;

public class Vec2ToDoubleRef {
	// in
	private final Vec2_ROI v;
	// out
	private final DoubleRef x, y;
	
	public Vec2ToDoubleRef(Vec2_ROI v, DoubleRef x, DoubleRef y) {
		this.v = v;
		
		this.x = new DoubleRef();
		this.y = new DoubleRef();
		update();
	}
	
	public void update() {
		x.setVal(v.getX());
		y.setVal(v.getY());
	}
	
	public Double_ROI getXref() {
		return x;
	}
	
	public Double_ROI getYref() {
		return y;
	}
	
}
