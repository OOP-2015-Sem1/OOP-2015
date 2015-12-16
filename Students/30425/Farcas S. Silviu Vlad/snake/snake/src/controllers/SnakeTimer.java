package controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import models.*;
import controllers.*;
import views.*;

public class SnakeTimer implements ActionListener {

	public int ticks = 0;
	public Timer timer = new Timer(20, this);
	public Point head, cherry;
	public Snake snake;
	public boolean over, paused;
	public Random random;

	public SnakeTimer() {
		ticks = 0;
		snake = Snake.getSnake();
		head = snake.getHead();
		cherry = snake.getCherry();
		over = snake.getOver();
		paused = snake.getPaused();
		random=new Random();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		snake.repaintRenderPanel();
		ticks++;

		snake = Snake.getSnake();

		if (ticks % 2 == 0 && snake.getHead() != null && !snake.getOver() && !snake.getPaused()) {

			snake.time++;

			snake.addSnakeParts(new Point(snake.getHead().x, snake.getHead().y));

			if (snake.direction == Snake.UP) {
				if (StartMenu.goThroughWalls == 0) {
					if (snake.noTailAndNoObstacleAt(snake.getHead().x, snake.getHead().y - 1)) {
						if (snake.getHead().y - 1 >= 0) {
							snake.setHead(new Point(snake.getHead().x, snake.getHead().y - 1));
						} else {
							snake.setOver(true);
						}
					} else {
						snake.setOver(true);
					}
				} else {
					if (snake.getHead().y - 1 >= 0) {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x, snake.getHead().y - 1)) {
							snake.setHead(new Point(snake.getHead().x, snake.getHead().y - 1));
						} else {
							snake.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x,
								StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67)) {
							snake.setHead(new Point(snake.getHead().x,
									StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67));
						} else {
							snake.setOver(true);
						}
					}
				}
			}

			if (snake.direction == Snake.DOWN) {
				if (StartMenu.goThroughWalls == 0) {
					if (snake.noTailAndNoObstacleAt(snake.getHead().x, snake.getHead().y + 1)) {
						if (snake.getHead().y + 1 < StartMenu.small * 27 + StartMenu.medium * 47
								+ StartMenu.large * 67) {
							snake.setHead(new Point(snake.getHead().x, snake.getHead().y + 1));
						} else {
							snake.setOver(true);
						}
					} else {
						snake.setOver(true);
					}
				} else {
					if (snake.getHead().y + 1 < StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67) {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x, snake.getHead().y + 1)) {
							snake.setHead(new Point(snake.getHead().x, snake.getHead().y + 1));
						} else {
							snake.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x, 0)) {
							snake.setHead(new Point(snake.getHead().x, 0));
						} else {
							snake.setOver(true);
						}
					}
				}
			}

			if (snake.direction == Snake.LEFT) {
				if (StartMenu.goThroughWalls == 0) {
					if (snake.noTailAndNoObstacleAt(snake.getHead().x - 1, snake.getHead().y)) {
						if (snake.getHead().x - 1 >= 0) {
							snake.setHead(new Point(snake.getHead().x - 1, snake.getHead().y));
						} else {
							snake.setOver(true);
						}
					} else {
						snake.setOver(true);
					}
				} else {
					if (snake.getHead().x - 1 >= 0) {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x - 1, snake.getHead().y)) {
							snake.setHead(new Point(snake.getHead().x - 1, snake.getHead().y));
						} else {
							snake.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(
								StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79,
								snake.getHead().y)) {
							snake.setHead(new Point(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79,
									snake.getHead().y));
						} else {
							snake.setOver(true);
						}
					}
				}
			}
			if (snake.direction == Snake.RIGHT)

			{
				if (StartMenu.goThroughWalls == 0) {
					if (snake.noTailAndNoObstacleAt(snake.getHead().x + 1, snake.getHead().y)) {
						if (snake.getHead().x + 1 < StartMenu.small * 39 + StartMenu.medium * 59
								+ StartMenu.large * 79) {
							snake.setHead(new Point(snake.getHead().x + 1, snake.getHead().y));
						} else {
							snake.setOver(true);
						}
					} else {
						snake.setOver(true);
					}
				} else {
					if (snake.getHead().x + 1 < StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79) {
						if (snake.noTailAndNoObstacleAt(snake.getHead().x + 1, snake.getHead().y)) {
							snake.setHead(new Point(snake.getHead().x + 1, snake.getHead().y));
						} else {
							snake.setOver(true);
						}
					} else {
						if (snake.noTailAndNoObstacleAt(0, snake.getHead().y)) {
							snake.setHead(new Point(0, snake.getHead().y));
						} else {
							snake.setOver(true);
						}
					}
				}
			}

			if (snake.getSnakeParts().size() > snake.tailLength)

			{
				snake.removeSnakeParts(0);

			}

			if (cherry != null)

			{
				if (snake.getHead().equals(cherry)) {
					snake.score += 10;
					snake.tailLength++;
					do {
						cherry.setLocation(
								random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
						snake.setCherry(cherry);
					} while (snake.noCherryAtObstacle() == false);
				}
			}
		}	
		if (snake.getOver()) {
			timer.stop();
		}
		Snake.setSnake(snake);
	}

}
