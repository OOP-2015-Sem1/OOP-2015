package robosim.util;

public class DoubleConst implements 
	Double_ROI {
	private final double val;

	public DoubleConst(double val) {
		this.val = val;
	}
	
	public double getVal() {
		return val;
	}
	
	public double getAbs() {
		return Math.abs(val);
	}
}
