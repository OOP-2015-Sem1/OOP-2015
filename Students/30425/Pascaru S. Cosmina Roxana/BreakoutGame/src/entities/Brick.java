package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick extends Entity {

	// brick details
	int brickX = 100, brickY = 50, brickSpace = 70;
	int brickWidth = 60, brickHeight = 16;
	public Color brickColor;
	private boolean hit;
	private boolean destroyed = false;

	public int nrOfHits = 0;

	// constructor
	public Brick(Color color, int x, int y) {
		brickX = x;
		brickY = y;
		brickColor = color;
		surface = new Rectangle(brickX, brickY, brickWidth, brickHeight);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(brickColor);
		g.fillRect(brickX, brickY, brickWidth, brickHeight);
	}

	@Override
	public void update() {

	}

	public void wasHit(boolean var) {
		hit = true;
	}

	public boolean isHit() {
		return hit;
	}

	public void setIsDestroyed(boolean var) {
		destroyed = var;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void changeColor(Color color) {
		brickColor = color;
	}

}
