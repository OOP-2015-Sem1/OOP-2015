package robosim.graphics;

import java.awt.Graphics2D;

import robosim.math.Vec2;
import robosim.motion.OrientedPos;
import robosim.motion.OrientedPos_ROI;

public class RobotDrawer {
	private final RectDrawer 
		robotDraw, wheelLeftDraw, wheelRightDraw;
	
	public RobotDrawer(OrientedPos_ROI robotPos, double width, double height) {
		OrientedPos wheelLeft = new OrientedPos(new Vec2(-width/4, 0), 0, robotPos);
		OrientedPos wheelRight = new OrientedPos(new Vec2(width/4, 0), 0, robotPos);

		robotDraw = new RectDrawer(robotPos, width, height);
	
		wheelLeftDraw = new RectDrawer(wheelLeft, width/4, height/3);
		wheelRightDraw = new RectDrawer(wheelRight, width/4, height/3);
	}
	
	public void drawTo(Graphics2D g) {
		robotDraw.drawTo(g);
//		wheelLeftDraw.drawTo(g);
//		wheelRightDraw.drawTo(g);
	}
}
