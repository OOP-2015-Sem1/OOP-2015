package robosim.physics.wheel;

import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class WheelTraction {
	// in
	private final Double_ROI
		vLong,
		vLat,
		angularV;
	private final WheelSpec
		wheelSpec;
	// out
	private final Double_ROI
		longForce,
		latForce;
	// interior
	private final DoubleRef
		slipRatio,
		slipAngleTan;
	// characteristic
	private final VarLinFunction 
		longTraction,
		latTraction;
	
	public WheelTraction(
			Double_ROI normalForce, 
			Double_ROI vLong, 
			Double_ROI vLat, 
			Double_ROI angularV,
			WheelSpec wheelSpec ) {
		
		this.vLong = vLong;
		this.vLat = vLat;
		this.angularV = angularV;
		this.wheelSpec = wheelSpec;
		
		slipRatio = new DoubleRef();
		slipAngleTan = new DoubleRef();
		
		longTraction = new VarLinFunction(
				slipRatio, 
				normalForce,
				1.2, 1.0, 0.06, 1.);
		latTraction = new VarLinFunction(
				slipAngleTan,
				normalForce,
				1.1, 1.0, 0.0524, 1.);
		
		longForce = longTraction.getFx();
		latForce = latTraction.getFx();
		
		this.update();
	}
	
	public void update() {
		updateSlipAngleTan();
		updateSlip();
		longTraction.update();
		latTraction.update();
	}
	
	private void updateSlipAngleTan() {
		slipAngleTan.setVal(vLat.getVal() / vLong.getVal());
	}
	
	private void updateSlip() {
		slipRatio.setVal((angularV.getVal() * wheelSpec.getRadius() -  vLong.getVal())
				/ vLong.getAbs());
	}
	
	public Double_ROI getLongForce() {
		return longForce;
	}
	
	public Double_ROI getLatForce() {
		return latForce;
	}
	
}
