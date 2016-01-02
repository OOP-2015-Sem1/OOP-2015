package GameShapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Brick {

	private static final int BRICK_HEIGHT = 20;
	private static final int BRICK_WIDTH = 110;
	private int x;
	private int y;
	private boolean brickIsDead;
	public Brick(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public void Paint(Graphics g, Color brickcolor, int x, int y, Ball ball) {

		Graphics2D gdr = (Graphics2D) g;
		gdr.setColor(brickcolor);
		gdr.fillRoundRect(x, y, BRICK_WIDTH - 3, BRICK_HEIGHT - 3, 9, 9);
	}
	
	

	public int getY() {

		return y;
	}

	public int getX() {
		return x;
	}

	public boolean getVisibility() {
		return brickIsDead;
	}

	public void setVisiblity(boolean TypeOFVisibility) {
		this.brickIsDead = TypeOFVisibility;
	}
	
	
}
