package entities;

import java.awt.Color;
import java.awt.Graphics;

public class BodyParts {

	private int xCoor, yCoor, width, height;

	public BodyParts(int xCoor, int yCoor, int tileSize) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		width = tileSize;
		height = tileSize;
	}

	public void tick() {

	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(xCoor * width, yCoor * height, width, height);
		g.setColor(Color.GREEN);
		g.fillRect(xCoor * width + 2, yCoor * height + 2, width - 4, height - 4);
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
}
