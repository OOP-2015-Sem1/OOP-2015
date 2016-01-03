package anoyingame.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class ShapeAnimation extends JPanel {

	private Random shapeRandom = new Random();
	private Random colorRandom = new Random();
	private int shape = shapeRandom.nextInt(6) + 1;
	private int color = colorRandom.nextInt(6) + 1;

	public void setRandomShape() {
		shape = shapeRandom.nextInt(6) + 1;
	}

	public void setRandomColor() {
		color = colorRandom.nextInt(6) + 1;
	}

	public int getShape() {
		return shape;
	}

	public int getColor() {
		return color;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		switch (color) {
		case 1:
			g.setColor(Color.BLUE);
			break;
		case 2:
			g.setColor(Color.GREEN);
			break;
		case 3:
			g.setColor(Color.RED);
			break;
		case 4:
			g.setColor(Color.YELLOW);
			break;
		case 5:
			g.setColor(Color.MAGENTA);
			break;
		case 6:
			g.setColor(Color.ORANGE);
			break;
		}

		switch (shape) {
		case 1:
			g.fillRect(105, 150, 200, 100);
			break;
		case 2:
			g.fillPolygon(new int[] { 100, 200, 300 }, new int[] { 300, 150, 300 }, 3);
			break;
		case 3:
			g.fillRect(135, 150, 100, 100);
			break;
		case 4:
			g.fillOval(135, 150, 100, 100);
			break;
		case 5:
			g.fillOval(100, 150, 200, 80);
			break;
		case 6:
			g.fillRoundRect(145, 150, 100, 120, 20, 20);
			break;
		}
	}
}
