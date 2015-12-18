package controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import models.*;
import views.*;

public class RenderPanel extends JPanel {

	public int start = 0;
	public static final Color BLUEBEE = new Color(65,156,186);
	public static final Color GREENABEE = new Color(8,69,34);
	private Dimension dim;
	public static JFrame jframe;

	public RenderPanel(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(StartMenu.small * 400 + StartMenu.medium * 600 + StartMenu.large * 800,
				StartMenu.small * 300 + StartMenu.medium * 500 + StartMenu.large * 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Snake snake = Snake.getSnake();
		
		GameManagement game = GameManagement.getGame();

		g.setColor(BLUEBEE);

		g.fillRect(0, 0, 800, 700);

		g.setColor(GREENABEE);

		for (Point point : snake.getSnakeParts()) {
			g.fillRect(point.x * SnakeDirection.SCALE, point.y * SnakeDirection.SCALE, SnakeDirection.SCALE, SnakeDirection.SCALE);
		}

		g.setColor(Color.BLACK);
		if (snake.getObstacles() != null) {
			for (Point point : snake.getObstacles()) {
				g.fillRect(point.x * SnakeDirection.SCALE, point.y * SnakeDirection.SCALE, SnakeDirection.SCALE, SnakeDirection.SCALE);
			}
		}
		
		g.setColor(GREENABEE);
		
		g.fillRect(snake.getHead().x * SnakeDirection.SCALE, snake.getHead().y * SnakeDirection.SCALE, SnakeDirection.SCALE, SnakeDirection.SCALE);

		g.setColor(Color.RED);

		g.fillRect(snake.getCherry().x * SnakeDirection.SCALE, snake.getCherry().y * SnakeDirection.SCALE, SnakeDirection.SCALE, SnakeDirection.SCALE);

		String string = "Score: " + game.getScore() + ", Length: " + snake.tailLength + ", Time: " + game.getTime() / 20;

		g.setColor(Color.white);

		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);

		string = "Game Over! Press \"Space\" to exit";

		if (game.getOver()) {
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) dim.getHeight() / 4);
		}

		string = "Paused!";

		if (game.getPaused() && !game.getOver()) {
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) dim.getHeight() / 4);
		}
	}
}
