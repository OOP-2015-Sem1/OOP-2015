package robosim.util;

import robosim.math.Vec2;
import robosim.math.Vec2_ROI;

public class Vec2DFF {
	private Vec2_ROI updateSrc;
	private final Vec2 actual;
	
	public Vec2DFF() {
		actual = new Vec2();
	}
	
	public void coupleTo(Vec2_ROI updateSrc) {
		this.updateSrc = updateSrc;
	}
	
	public void update() {
		actual.copy(updateSrc);
	}
	
	public Vec2_ROI getRef() {
		return actual;
	}
}
