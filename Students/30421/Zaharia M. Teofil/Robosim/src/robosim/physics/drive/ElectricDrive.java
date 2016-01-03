package robosim.physics.drive;

import robosim.physics.wheel.Wheel;
import robosim.physics.wheel.WheelSpec;
import robosim.util.Double_ROI;

public class ElectricDrive {
	// interior
	private final ElectricMotor motor;
	private final Wheel wheel;
	
	// out
	private final Double_ROI fLong, fLat;
	
	public ElectricDrive(
			Double_ROI voltage, 
			Double_ROI TIME_STEP,
			Double_ROI vLong,
			Double_ROI vLat,
			Double_ROI fNormal) {
		motor = new ElectricMotor(0.1, 0.2, voltage, TIME_STEP, 0.5);
		WheelSpec wspec = new WheelSpec(0.1, 0.2);
		wheel = new Wheel(TIME_STEP, wspec, vLong, vLat, fNormal, motor.getAngularV());
				
		fLong = wheel.getFLong();
		fLat = wheel.getFLat();
	}
	
	public void update() {
		wheel.update();
		motor.update(wheel.getTorque().getVal());
	}

	public Double_ROI getfLong() {
		return fLong;
	}

	public Double_ROI getfLat() {
		return fLat;
	}
}
