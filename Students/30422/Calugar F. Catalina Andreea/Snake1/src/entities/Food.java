package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Food extends BodyPart {
	Image image;

	public Food(int xCoor, int yCoor, int tileSize) {
		super(xCoor, yCoor, tileSize);
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(getxCoor() * getWidth(), getyCoor() * getHeight(), getWidth(), getHeight());
		ImageIcon i = new ImageIcon("image/apple.png");
		image = i.getImage();
		g.drawImage(image, 200, 300, 40, 40, null);
	}

}
