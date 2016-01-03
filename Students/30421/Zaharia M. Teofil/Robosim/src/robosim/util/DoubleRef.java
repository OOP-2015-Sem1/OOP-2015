package robosim.util;

public class DoubleRef implements Double_ROI {
	private double val;
	
	public DoubleRef(double val) {
		this.val = val;
	}
	
	public DoubleRef() {
		this(0.);
	}
	
	public void zero() {
		val = 0.;
	}

	public double getVal() {
		return val;
	}
	
	public void setVal(double newVal) {
		val = newVal;
	}
	
	public void add(double toAdd) {
		val += toAdd;
	}
	
	public void add(Double_ROI toAdd) {
		add(toAdd.getVal());
	}
	
	public void addScaled(double scale, double val) {
		val += scale * val;
	}
	
	public void addScaled(Double_ROI scale, double val) {
		add(scale.getVal() * val);
	}
	
	public void addScaled(Double_ROI scale, Double_ROI toAdd) {
		add(scale.getVal() * toAdd.getVal());
	}
	
	public void copy(Double_ROI rhs) {
		setVal(rhs.getVal());
	}

	public double getAbs() {
		return Math.abs(val);
	}
	
}
