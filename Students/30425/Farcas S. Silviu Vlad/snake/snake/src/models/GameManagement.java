package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import controllers.*;
import views.*;

public class GameManagement {

	private boolean over = false, paused;

	private RenderPanel renderPanel;

	private int time, score;

	private SnakeTimer snakeTimer;

	private ArrayList<Point> obstacles = new ArrayList<Point>();

	private Snake snake;

	private Point cherry;

	public GameManagement(Snake snake) {
		this.snake = snake;
	}

	public void startGame() {
		renderPanel = new RenderPanel(snake, this);
		over = false;
		paused = false;
		time = 0;
		score = 0;
		snake.setHead(new Point(0, -1));
		Random random = new Random();
		snake.getSnakeParts().clear();
		cherry = new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
				random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
		if (StartMenu.easy == 1) {
			obstacles = null;
		}
		if (StartMenu.normal == 1) {
			for (int i = 0; i < StartMenu.small * 20 + StartMenu.medium * 40 + StartMenu.large * 80; i++) {
				obstacles.add(
						new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
			}
		}
		if (StartMenu.hard == 1) {
			for (int i = 0; i < StartMenu.small * 40 + StartMenu.medium * 80 + StartMenu.large * 160; i++) {
				obstacles.add(
						new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
			}
		}
		do {
			cherry = new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
					random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
		} while (noCherryAtObstacle() == false);
		getGameFrame().addKeyListener(new SnakeController(snake, this));
		snakeTimer = new SnakeTimer(this, snake);
		snakeTimer.timer.start();
	}

	public boolean noCherryAtObstacle() {
		if (obstacles == null) {
			return true;
		}
		for (Point point : obstacles) {
			if (cherry.equals(point)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean getOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public boolean getPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public RenderPanel getRenderPanel() {
		return renderPanel;
	}

	public void setRenderPanel(RenderPanel renderPanel) {
		this.renderPanel = renderPanel;
	}

	public void setObstacles(ArrayList<Point> obstacles) {
		this.obstacles = obstacles;
	}

	public ArrayList<Point> getObstacles() {
		return obstacles;
	}

	public void addObstacles(Point part) {
		obstacles.add(part);
	}

	public Point getCherry() {
		return cherry;
	}

	public void setCherry(Point cherry) {
		this.cherry = cherry;
	}
	public JFrame getGameFrame(){
		return renderPanel.getFrame();
	}
}
