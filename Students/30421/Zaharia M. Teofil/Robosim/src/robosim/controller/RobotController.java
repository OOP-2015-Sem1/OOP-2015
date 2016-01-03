package robosim.controller;

import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

/**
 * integrate drive method here 
 *
 */

public class RobotController {
	// out
	private final DoubleRef
		powerLeftDrive, powerRightDrive;

	public RobotController() {
		this.powerLeftDrive = new DoubleRef();
		this.powerRightDrive = new DoubleRef();
	}
	
	public Double_ROI getPowerLeft() {
		return powerLeftDrive;
	}
	
	public Double_ROI getPowerRight() {
		return powerRightDrive;
	}
	
	
}
