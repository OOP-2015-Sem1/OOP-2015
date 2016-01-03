package robosim.physics.wheel;

import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class NormalToTraction {
	// in
	private final Double_ROI normal;
	private final double coeff;
	
	// out
	private final DoubleRef tractionForce;

	public NormalToTraction(
			Double_ROI normal, 
			final double coeff) {
		this.normal = normal;
		this.coeff = coeff;
		
		this.tractionForce = new DoubleRef();
		this.process();
	}
	
	public void process() {
		tractionForce.setVal(coeff * normal.getVal());
	}
	
	public Double_ROI getForce() {
		return tractionForce;
	}
	
}
