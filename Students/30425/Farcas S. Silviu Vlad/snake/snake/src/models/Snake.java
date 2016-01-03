package models;

import views.*;
import java.awt.Point;

import java.util.ArrayList;
import java.util.Random;

public class Snake {

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;

	private int direction = DOWN;

	private int ticks;

	private ArrayList<Point> snakeParts = new ArrayList<Point>();

	public int tailLength = 14;

	private Point head;

	public Snake() {
		ticks = 0;
	}

	public void direction(GameManagement game) {

		Random random = new Random();

		ticks++;

		if (ticks % 2 == 0 && head != null && !game.getOver() && !game.getPaused()) {

			game.setTime(game.getTime() + 1);

			snakeParts.add(new Point(head.x, head.y));

			int maximumY = StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67;
			int maximumX = StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79;
			Point point = null;
			if (direction == UP) {
				point = new Point(head.x, head.y - 1);
			}
			if (direction == DOWN) {
				point = new Point(head.x, head.y + 1);
			}
			if (direction == LEFT) {
				point = new Point(head.x - 1, head.y);
			}
			if (direction == RIGHT) {
				point = new Point(head.x + 1, head.y);
			}

			if (!noTailAndNoObstacleAt(point.x, point.y, game)) {
				game.setOver(true);
			}

			if (StartMenu.goThroughWalls) {
				if (point.x < 0) {
					point.x = maximumX;
				}
				if (point.y < 0) {
					point.y = maximumY;
				}
				if (point.x > maximumX) {
					point.x = 0;
				}
				if (point.y > maximumY) {
					point.y = 0;
				}
			} else if (point.x < 0 || point.x > maximumX || point.y < 0 || point.y > maximumY) {
				game.setOver(true);
			}
			if (!game.getOver()) {
				head = point;
			}
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);

			}
			if (game.getCherry() != null) {
				if (head.equals(game.getCherry())) {
					game.setScore(game.getScore() + 10);
					tailLength++;
					do {
						game.getCherry()
								.setLocation(random.nextInt(
										StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
					} while (game.noCherryAtObstacle() == false);
				}
			}
		}

	}

	public boolean noTailAndNoObstacleAt(int x, int y, GameManagement game) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		if (game.getObstacles() != null) {
			for (Point point : game.getObstacles()) {
				if (point.equals(new Point(x, y))) {
					return false;
				}
			}
		}
		return true;
	}

	public ArrayList<Point> getSnakeParts() {
		return snakeParts;
	}

	public void addSnakeParts(Point part) {
		snakeParts.add(part);
	}

	public void removeSnakeParts(int index) {
		snakeParts.remove(index);
	}

	public Point getHead() {
		return head;
	}

	public void setHead(Point head) {
		this.head = head;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

}