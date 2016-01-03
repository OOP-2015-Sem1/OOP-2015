package robosim.physics.collision;

import robosim.boundedobj.BoundedObj;
import robosim.math.Vec2;
import robosim.math.Vec2_ROI;

/**
 * used for moving BoundedObj
 * recomputes Vec2[] boundary of the obj
 * used to check intersection with Shapes of
 * static obstacles 
 *
 */

public class Vec2Poly {
	private static final int pointCount = 4;
	private static final double[] sx = {-1, -1, 1, 1};
	private static final double[] sy = {1, -1, -1, 1};
	
	// in
	private final BoundedObj targetRect;
	// out
	private final Vec2[] point;
	
	public Vec2Poly(BoundedObj targetRect) {
		this.targetRect = targetRect;
		point = new Vec2[pointCount];
		
		for (Vec2 p : point) {
			p = new Vec2();
			System.out.println(p.toString());
		}
	}
	
	public void update() {
		double w = targetRect.getWidth();
		double h = targetRect.getHeight();
		
		int i = 0;
		
		for (Vec2 p : point) {
			p.set(sx[i] * w/2, sy[i] * h/2);
			++i;
			p.rotateBy(targetRect.getTheta());
			p.add(targetRect.getX(), targetRect.getY());
		}
	}
	
	public Vec2_ROI[] getPoints() {
		return point;
	}
	
	
}
