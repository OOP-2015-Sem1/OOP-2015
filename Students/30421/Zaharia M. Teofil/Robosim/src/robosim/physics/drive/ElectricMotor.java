package robosim.physics.drive;

import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class ElectricMotor {
	// characteristic
	private final double
		voltageCoeff,
		angularVCoeff;
	// in
	private final Double_ROI
		voltage, TIME_STEP;
	// interior
	private final DoubleRef
		netTorque;
	// out
	private final DoubleRef
		angularV;
	private final double
		momentOfInertia;
	
	public ElectricMotor(
			final double voltageCoeff, 
			final double angularVCoeff, 
			Double_ROI voltage, 
			Double_ROI TIME_STEP, 
			double momentOfInertia) {
		
		this.voltageCoeff = voltageCoeff;
		this.angularVCoeff = angularVCoeff;
		this.voltage = voltage;
		this.TIME_STEP = TIME_STEP;
		this.momentOfInertia = momentOfInertia;
		
		this.angularV = new DoubleRef();
		this.netTorque = new DoubleRef();
		
	}

	public void update(double wheelBackTorque) {
		angularV.setVal(netTorque.getVal() / momentOfInertia * TIME_STEP.getVal());
		netTorque.setVal(voltageCoeff * voltage.getVal() - 
				angularVCoeff * angularV.getVal());
		netTorque.addScaled(-1, wheelBackTorque);
	}
	
	public Double_ROI getAngularV() {
		return angularV;
	}
}
