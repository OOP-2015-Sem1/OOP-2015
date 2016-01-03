package test.physics.traction;

import org.junit.Assert;
import org.junit.Test;

import robosim.math.VarFunctionPoint;
import robosim.physics.wheel.VarLinFunction;
import robosim.util.DoubleConst;
import robosim.util.DoubleRef;
import robosim.util.Double_ROI;

public class SlipFunctionTest {



	@Test
	public void test() {
		
		VarFunctionPoint p1 = new VarFunctionPoint(new DoubleConst(1), new DoubleConst(1));
		VarFunctionPoint p2 = new VarFunctionPoint(new DoubleConst(2), new DoubleConst(1));
		
		DoubleRef x = new DoubleRef();
		DoubleRef fx = new DoubleRef();
		
		VarLinFunction sf = new VarLinFunction(p1, p2, x, fx);
		
		x.setVal(5);
		
		sf.update();
		Assert.assertTrue(fx.getVal() == 1);
		
	}

}
