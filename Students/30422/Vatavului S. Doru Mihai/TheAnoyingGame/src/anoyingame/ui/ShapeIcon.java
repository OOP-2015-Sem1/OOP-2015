package anoyingame.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ShapeIcon implements Icon {

	private int shape;

	public ShapeIcon(int shape) {
		this.shape = shape;
	}

	public int getIconWidth() {
		return 20;
	}

	public int getIconHeight() {
		return 20;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(new Color(1, 125, 135));
		switch (shape) {
		case 1:
			g.fillRect(30, 15, 60, 30);
			break;
		case 2:
			g.fillPolygon(new int[] { 60, 30, 90 }, new int[] { 10, 50, 50 }, 3);
			break;
		case 3:
			g.fillRect(50, 15, 30, 30);
			break;
		case 4:
			g.fillOval(50, 15, 30, 30);
			break;
		case 5:
			g.fillOval(30, 15, 60, 24);
			break;
		case 6:
			g.fillRoundRect(50, 12, 30, 36, 8, 8);
			break;
		}
	}
}
