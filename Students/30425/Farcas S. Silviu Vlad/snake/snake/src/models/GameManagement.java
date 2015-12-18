package models;

import java.awt.Point;
import java.util.Random;

import controllers.*;
import views.*;

public class GameManagement {

	private boolean over = false, paused;

	private int time, score;

	private SnakeTimer snakeTimer;

	private SnakeDirection snakeDirection;

	private Snake snake;

	private static GameManagement game;

	public GameManagement() {
		snake = Snake.getSnake();
		snakeDirection = SnakeDirection.getSnakeDirection();
	}

	public void startGame() {
		over = false;
		paused = false;
		time = 0;
		score = 0;
		snakeDirection.setDirection(SnakeDirection.DOWN);
		snake.setHead(new Point(0, -1));
		Random random = new Random();
		snake.getSnakeParts().clear();
		snake.setCherry(new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
				random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
		if (StartMenu.easy == 1) {
			snake.setObstacles(null);
		}
		if (StartMenu.normal == 1) {
			for (int i = 0; i < StartMenu.small * 20 + StartMenu.medium * 40 + StartMenu.large * 80; i++) {
				snake.addObstacles(
						new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
			}
		}
		if (StartMenu.hard == 1) {
			for (int i = 0; i < StartMenu.small * 40 + StartMenu.medium * 80 + StartMenu.large * 160; i++) {
				snake.addObstacles(
						new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
			}
		}
		do {
			snake.setCherry(
					new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
							random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
		} while (snake.noCherryAtObstacle() == false);
		RenderPanel.jframe.addKeyListener(new SnakeKey());
		Snake.setSnake(snake);
		snakeTimer = new SnakeTimer();
		snakeTimer.timer.start();
	}

	public static GameManagement getGame() {
		return game;
	}

	public static void setGame(GameManagement gameManagement) {
		game = gameManagement;
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
}
