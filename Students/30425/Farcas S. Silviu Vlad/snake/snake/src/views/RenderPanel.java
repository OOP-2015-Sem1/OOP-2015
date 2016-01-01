package views;

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

	public static final Color BLUEBEE = new Color(65,156,186);
	public static final Color GREENABEE = new Color(8,69,34);
	private Dimension dim;
	private JFrame jframe;
	private Snake snake;
	private GameManagement game;

	public RenderPanel(Snake snake, GameManagement game){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(StartMenu.small * 400 + StartMenu.medium * 600 + StartMenu.large * 800,
				StartMenu.small * 300 + StartMenu.medium * 500 + StartMenu.large * 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(this);
		this.snake=snake;
		this.game=game;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(BLUEBEE);

		g.fillRect(0, 0, 800, 700);

		g.setColor(GREENABEE);

		for (Point point : snake.getSnakeParts()) {
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		}

		g.setColor(Color.BLACK);
		if (game.getObstacles() != null) {
			for (Point point : game.getObstacles()) {
				g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
			}
		}
		
		g.setColor(GREENABEE);
		
		g.fillRect(snake.getHead().x * Snake.SCALE, snake.getHead().y * Snake.SCALE, Snake.SCALE, Snake.SCALE);

		g.setColor(Color.RED);

		g.fillRect(game.getCherry().x * Snake.SCALE, game.getCherry().y * Snake.SCALE, Snake.SCALE, Snake.SCALE);

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
	
	public JFrame getFrame(){
		return jframe;
	}
	public void setFrame(JFrame jframe){
		this.jframe=jframe;
	}
}
