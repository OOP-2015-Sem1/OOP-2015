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

	private Point head, cherry;

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

			if (direction == UP) {
				boolean noTailAndNoObstaclesAtUp = noTailAndNoObstacleAt(head.x, head.y - 1, game);
				boolean headGreaterThanZero = (head.y - 1 >= 0);
				Point point = new Point(head.x, head.y - 1);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstaclesAtUp) {
						if (headGreaterThanZero) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (headGreaterThanZero) {
						if (noTailAndNoObstaclesAtUp) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						if (noTailAndNoObstacleAt(head.x, maximumY, game)) {
							head = new Point(head.x, maximumY);
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (direction == DOWN) {
				boolean noTailAndNoObstaclesAtDown = noTailAndNoObstacleAt(head.x, head.y + 1, game);
				boolean headSmallerThanMax = (head.y + 1 < maximumY);
				Point point = new Point(head.x, head.y + 1);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstaclesAtDown) {
						if (headSmallerThanMax) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (headSmallerThanMax) {
						if (noTailAndNoObstaclesAtDown) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						if (noTailAndNoObstacleAt(head.x, 0, game)) {
							head = new Point(head.x, 0);
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (direction == LEFT) {
				boolean noTailAndNoObstacleAtLeft = noTailAndNoObstacleAt(head.x - 1, head.y, game);
				Point point = new Point(head.x - 1, head.y);
				boolean headGreaterThanZero = (head.x - 1 >= 0);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstacleAtLeft) {
						if (headGreaterThanZero) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (headGreaterThanZero) {
						if (noTailAndNoObstacleAtLeft) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						if (noTailAndNoObstacleAt(maximumX, head.y, game)) {
							head = new Point(maximumX, head.y);
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (direction == RIGHT)

			{
				boolean headSmallerThanMax = (head.x + 1 < maximumX);
				Point point = new Point(head.x + 1, head.y);
				boolean noTailAndNoObstacleAtRight = noTailAndNoObstacleAt(head.x + 1, head.y, game);
				if (!StartMenu.goThroughWalls) {
					if (noTailAndNoObstacleAtRight) {
						if (headSmallerThanMax) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						game.setOver(true);
					}
				} else {
					if (head.x + 1 < maximumX) {
						if (noTailAndNoObstacleAtRight) {
							head = point;
						} else {
							game.setOver(true);
						}
					} else {
						if (noTailAndNoObstacleAt(0, head.y, game)) {
							head = new Point(0, head.y);
						} else {
							game.setOver(true);
						}
					}
				}
			}

			if (snakeParts.size() > tailLength)

			{
				snakeParts.remove(0);

			}

			if (cherry != null)

			{
				if (head.equals(cherry)) {
					game.setScore(game.getScore() + 10);
					tailLength++;
					do {
						cherry.setLocation(
								random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
					} while (noCherryAtObstacle(game) == false);
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

	public boolean noCherryAtObstacle(GameManagement game) {
		if (game.getObstacles() == null) {
			return true;
		}
		for (Point point : game.getObstacles()) {
			if (cherry.equals(point)) {
				return false;
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

	public Point getCherry() {
		return cherry;
	}

	public void setCherry(Point cherry) {
		this.cherry = cherry;
	}

	public void setDirection(int direction){
		this.direction=direction;
	}
	
	public int getDirection(){
		return direction;
	}
	
}