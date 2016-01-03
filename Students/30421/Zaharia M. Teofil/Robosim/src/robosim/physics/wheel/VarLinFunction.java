package robosim.physics.wheel;

import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class VarLinFunction {
	// in
	private final Double_ROI x, ctrl;
	
	// out
	private final DoubleRef fx;
	
	// constants
	private final double sCtrl1, sCtrl2;
	private final double xCtrl1, xCtrl2;
	// control point coefficients
		
	public VarLinFunction(
			Double_ROI x, Double_ROI ctrl, 
			double sCtrl1, double sCtrl2, double xCtrl1,
			double xCtrl2) {
		this.x = x;
		this.ctrl = ctrl;
		this.fx = new DoubleRef();
		
		this.sCtrl1 = sCtrl1;
		this.sCtrl2 = sCtrl2;
		this.xCtrl1 = xCtrl1;
		this.xCtrl2 = xCtrl2;
	}

	public void update() {
		double absX = x.getAbs();
		double ctrlVar = ctrl.getVal();
		double slope, startX, startY;
		
		if (absX < xCtrl1) {
			slope = sCtrl1 * ctrlVar / xCtrl1;
			startX = 0;
			startY = 0;
		} else {
			slope = (sCtrl2 - sCtrl1) * ctrlVar / (xCtrl2 - xCtrl1);
			startX = xCtrl2;
			startY = sCtrl2 * ctrlVar;
		}
		
		fx.setVal((startY + (absX - startX) * slope) * Math.signum(x.getVal()));
	}
	

	public Double_ROI getFx() {
		return fx;
	}
}
