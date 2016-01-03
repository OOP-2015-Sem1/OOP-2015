package robosim.physics.wheel;

import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class Wheel {
	private final WheelTraction traction;
	private final WheelSpec wheelSpec;
	
	//out
	private final Double_ROI 
		fLong,
		fLat;
	private final DoubleRef
		tractionTorque;
	
	public Wheel(Double_ROI TIME_STEP, WheelSpec wheelSpec, 
			Double_ROI vLong, Double_ROI vLat,
			Double_ROI fNormal, Double_ROI angularV) {
		
		this.wheelSpec = wheelSpec;
		
		traction = new WheelTraction(fNormal, vLong, vLat, angularV, wheelSpec);
		fLong = traction.getLongForce();
		fLat = traction.getLatForce();
		tractionTorque = new DoubleRef();
		update();
	}
	
	public void update() {
		traction.update();
		tractionTorque.setVal(fLong.getVal() * wheelSpec.getRadius());
	}
	
	public Double_ROI getFLong() {
		return fLong;
	}
	
	public Double_ROI getFLat() {
		return fLat;
	}
	
	public Double_ROI getTorque() {
		return tractionTorque;
	}
	
	public double getMomentOfInertia() {
		return wheelSpec.getMomentOfInertia();
	}
}
