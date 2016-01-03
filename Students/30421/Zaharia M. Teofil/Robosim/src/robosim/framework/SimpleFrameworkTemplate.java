package robosim.framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import robosim.boundedobj.BoundedObj;
import robosim.graphics.GraphicsConstants;
import robosim.graphics.RectDrawer;
import robosim.graphics.RobotDrawer;
import robosim.math.Vec2;
import robosim.motion.OrientedPos;

public class SimpleFrameworkTemplate extends SimpleFramework {
	private static final long serialVersionUID = 3141427106095442231L;

	public SimpleFrameworkTemplate() {
		appBackground = Color.WHITE;
		appBorder = Color.LIGHT_GRAY;
		appFont = new Font("Courier New", Font.PLAIN, 14);
		appBorderScale = 0.9f;
		appFPSColor = Color.BLACK;
		appWidth = GraphicsConstants.SCREEN_WIDTH;
		appHeight = GraphicsConstants.SCREEN_HEIGHT;
		appMaintainRatio = false;
		appSleep = 20L;
		appTitle = "FramworkTemplate";
		appWorldWidth = 2.0f;
		appWorldHeight = 2.0f;
	}

	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);
	}
	private static double x = 0, y = 0;
	@Override
	protected void render(Graphics g) {
		super.render(g);
		double pi = Math.PI;
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.translate((appWidth - 1) / 2, (appHeight - 1) / 2);
		g2.scale(200, -200);
		//g2.scale(30, -30);
		AffineTransform base = g2.getTransform();
		
		OrientedPos op = new OrientedPos(new Vec2(x, y+=0.00), pi/8, null);
		BoundedObj bo= new BoundedObj(0.04, 0.01, op);
		bo.updateShape();
		g2.setColor(Color.BLUE);
		g2.draw(bo.getShape());
	}

	@Override
	protected void terminate() {
		super.terminate();
	}

	public static void main(String[] args) {
		launchApp(new SimpleFrameworkTemplate());
	}
}