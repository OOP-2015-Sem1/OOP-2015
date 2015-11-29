package controllers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import models.Snake;

public class RenderPanel extends JPanel {

	public int start = 0;
	public static final Color BLUEBEE = new Color(65,156,186);
	public static final Color GREENABEE = new Color(8,69,34);

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Snake snake = Snake.snake;

		g.setColor(BLUEBEE);

		g.fillRect(0, 0, 800, 700);

		g.setColor(GREENABEE);

		for (Point point : snake.snakeParts) {
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		}

		g.setColor(Color.BLACK);
		if (snake.obstacles != null) {
			for (Point point : snake.obstacles) {
				g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
			}
		}
		
		g.setColor(GREENABEE);
		
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);

		g.setColor(Color.RED);

		g.fillRect(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);

		String string = "Score: " + snake.score + ", Length: " + snake.tailLength + ", Time: " + snake.time / 20;

		g.setColor(Color.white);

		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);

		string = "Game Over! Press \"Space\" to exit";

		if (snake.over) {
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}

		string = "Paused!";

		if (snake.paused && !snake.over) {
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}
	}
}
