package robosim.util;

import robosim.math.Vec2;
import robosim.math.Vec2_ROI;

public class DoubleRefToVec2 {
	// in
	private final Double_ROI x, y;
	private final double thetaInitial, thetaFinal;
	// out
	private final Vec2 v;
	
	
	
	public DoubleRefToVec2(Double_ROI x, Double_ROI y, double initialRef, double finalRef) {
		this.x = x;
		this.y = y;
		this.thetaInitial = initialRef;
		this.thetaFinal = finalRef;

		this.v = new Vec2();
		update();	
	}

	public void update() {
		v.set(x.getVal(), y.getVal());
		v.transform(thetaInitial, thetaFinal);
	}
	
	public Vec2_ROI getVec2() {
		return v;
	}
	
	
	
}
