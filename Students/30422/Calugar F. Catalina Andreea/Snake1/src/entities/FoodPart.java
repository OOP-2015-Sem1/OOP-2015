package entities;

import java.awt.Color;
import java.awt.Graphics;

public class FoodPart extends SnakeBodyPart {

	public FoodPart(int xCoor, int yCoor, int tileSize) {
		super(xCoor, yCoor, tileSize);
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(getxCoor() * getWidth(), getyCoor() * getHeight(), getWidth(), getHeight());
	}

}
