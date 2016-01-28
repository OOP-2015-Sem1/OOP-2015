package Components;

import java.awt.Color;

import java.awt.Graphics2D;

public class Brick {

	int x, y, width = 60, height = 23;

	boolean DeadOrAlive;

	public Brick(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public void render(Graphics2D g, Color color, int x, int y) {

		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean getVisibility() {
		return DeadOrAlive;
	}

	public void setVisiblity(boolean TypeOFVisibility) {
		DeadOrAlive = TypeOFVisibility;
	}

	public int getY() {
		return y;
	}

}
