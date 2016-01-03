package robosim.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import robosim.motion.OrientedPos_ROI;

public class RectDrawer {

	// in
	private final OrientedPos_ROI opos;
	
	// characteristics
	private final int widthPx, heightPx;

	private int xPx, yPx;
	private double theta;

	private final static double 
	sx = GraphicsConstants.sx, 
	sy = GraphicsConstants.sy;
	
	public RectDrawer(
			OrientedPos_ROI opos, 
			double width, 
			double height) {
		
		this.opos = opos;
		
		updatePos();
		widthPx = (int)(sx * width);
		heightPx = (int)(sy * height);
	}
	
	public void updatePos() {		
		xPx = (int)(sx * opos.getAbsX());
		yPx = (int)(sy * opos.getAbsY());
		theta = opos.getAbsOrientation();
	}
	
	public void drawTo(Graphics2D g) {
		AffineTransform base = g.getTransform();
		
		updatePos();
		
		g.translate(xPx, yPx);
		g.rotate(theta);
		g.drawRect(-widthPx/2, -heightPx/2, widthPx, heightPx);
		
		g.setTransform(base);
	}
	
}
