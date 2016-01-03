package entities;

import java.awt.Color;
import java.awt.Graphics;

public class SnakeBodyPart {

	private int xCoor, yCoor, width, height;

	public SnakeBodyPart(int xCoor, int yCoor, int tileSize) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		setWidth(tileSize);
		setHeight(tileSize);
	}

	public void tick() {
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(xCoor * getWidth(), yCoor * getHeight(), getWidth(), getHeight());
		g.setColor(Color.GREEN);
		g.fillRect(xCoor * getWidth() + 2, yCoor * getHeight() + 2, getWidth() - 2, getHeight() - 2);
	}

	public int getxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
