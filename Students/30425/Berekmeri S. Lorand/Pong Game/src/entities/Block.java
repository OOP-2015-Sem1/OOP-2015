package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import game.Game;

public class Block {
	private int x;
	private int y;
	private int y_speed = 0;
	private final static int WIDTH = 30;
	private final static int HEIGHT = 120;

	public Block(int requiredX, int requiredY, int initialSpeed) {
		x = requiredX;
		y = requiredY;
		y_speed = initialSpeed;
	}

	public void update() {
		System.out.println("Update"+ y_speed);
		y = y + y_speed;
		if (x < 0) {
			y_speed = -y_speed;
		}
		if (x + HEIGHT > Game.gui_HEIGHT) {
			y_speed = -y_speed;
		}

	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(x, y, WIDTH, HEIGHT);
	}

	public void sety_speed(int speed) {
		y_speed = speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int get_width() {
		return WIDTH;
	}

	public int get_height() {
		return HEIGHT;
	}
}
