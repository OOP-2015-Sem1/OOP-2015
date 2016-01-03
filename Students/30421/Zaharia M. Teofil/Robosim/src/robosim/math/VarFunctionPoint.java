package robosim.math;

import robosim.util.Double_ROI;

public class VarFunctionPoint implements
	VarFunctionPoint_ROI {
	
	private final double x;
	private final Double_ROI y;

	public VarFunctionPoint(double x, Double_ROI y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y.getVal();
	}
	
	public double getSlope() {
		return y.getVal() / x;
	}
	
}
