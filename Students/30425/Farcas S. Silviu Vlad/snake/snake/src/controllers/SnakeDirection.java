package controllers;

import java.awt.Point;
import java.util.Random;

import models.*;
import views.*;

public class SnakeDirection {

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	private Snake snake;
	private GameManagement game;
	private Random random;
	private int ticks;
	private Point cherry;
	private int direction = DOWN;
	private static SnakeDirection snakeDirection;

	public SnakeDirection() {
		snake = Snake.getSnake();
		random = new Random();
		ticks = 0;
	}

	public void direction() {

		ticks++;

		game = GameManagement.getGame();

		if (ticks % 2 == 0 && snake.getHead() != null && !game.getOver() && !game.getPaused()) {

			game.setTime(game.getTime() + 1);

			snake.addSnakeParts(new Point(snake.getHead().x, snake.getHead().y));

			int maximumY = StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67;
			int maximumX = StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79;

			if (direction == UP) {
				boolean noTailAndNoObstaclesAtUp = snake.noTailAndNoObstacleAt(snake.getHead().x,
						snake.getHead().y - 1);
				boolean headGreaterThanZero = (snake.getHead().y - 1 >= 0);
				Point point = new Point(snake.getHead().x, snake.getHead().y - 1);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstaclesAtUp) {
						if (headGreaterThanZero) {
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (headGreaterThanZero) {
						if (noTailAndNoObstaclesAtUp) {
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x, maximumY)) {
							snake.setHead(new Point(snake.getHead().x, maximumY));
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (direction == DOWN) {
				boolean noTailAndNoObstaclesAtDown = snake.noTailAndNoObstacleAt(snake.getHead().x,
						snake.getHead().y + 1);
				boolean headSmallerThanMax = (snake.getHead().y + 1 < maximumY);
				Point point = new Point(snake.getHead().x, snake.getHead().y + 1);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstaclesAtDown) {
						if (headSmallerThanMax) {
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (headSmallerThanMax) {
						if (noTailAndNoObstaclesAtDown) {
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x, 0)) {
							snake.setHead(new Point(snake.getHead().x, 0));
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (direction == LEFT) {
				boolean noTailAndNoObstacleAtLeft = snake.noTailAndNoObstacleAt(snake.getHead().x - 1,
						snake.getHead().y);
				Point point = new Point(snake.getHead().x - 1, snake.getHead().y);
				boolean headGreaterThanZero = (snake.getHead().x - 1 >= 0);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstacleAtLeft) {
						if  (headGreaterThanZero){
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (headGreaterThanZero) {
						if (noTailAndNoObstacleAtLeft) {
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(maximumX, snake.getHead().y)) {
							snake.setHead(new Point(maximumX, snake.getHead().y));
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (direction == RIGHT)

			{
				boolean headSmallerThanMax = (snake.getHead().x + 1 < maximumX);
				Point point = new Point(snake.getHead().x + 1, snake.getHead().y);
				boolean noTailAndNoObstacleAtRight = snake.noTailAndNoObstacleAt(snake.getHead().x + 1, snake.getHead().y);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstacleAtRight) {
						if (headSmallerThanMax) {
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (snake.getHead().x + 1 < maximumX) {
						if (noTailAndNoObstacleAtRight) {
							snake.setHead(point);
						} else {
							game.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(0, snake.getHead().y)) {
							snake.setHead(new Point(0, snake.getHead().y));
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (snake.getSnakeParts().size() > snake.tailLength)

			{
				snake.removeSnakeParts(0);

			}

			if (snake.getCherry() != null)

			{
				if (snake.getHead().equals(snake.getCherry())) {
					game.setScore(game.getScore() + 10);
					snake.tailLength++;
					cherry = snake.getCherry();
					do {
						cherry.setLocation(
								random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
						snake.setCherry(cherry);
					} while (snake.noCherryAtObstacle() == false);
				}
			}
		}

		GameManagement.setGame(game);
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return this.direction;
	}

	public static SnakeDirection getSnakeDirection() {
		return snakeDirection;
	}

	public static void setSnakeDirection(SnakeDirection snakeDir) {
		snakeDirection = snakeDir;
	}

}
