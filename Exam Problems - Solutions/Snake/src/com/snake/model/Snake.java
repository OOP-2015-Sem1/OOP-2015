package com.snake.model;

import static com.snake.core.Constants.DOWN;
import static com.snake.core.Constants.LEFT;
import static com.snake.core.Constants.RIGHT;
import static com.snake.core.Constants.STEP;
import static com.snake.core.Constants.UP;
import static com.snake.ui.SnakeFrame.BOARD_SIZE;

import java.awt.Point;
import java.util.ArrayList;

import com.snake.core.GameObserver;

public class Snake {

	private String currentDirection;
	private ArrayList<Point> positions;
	private int length;
	private boolean canGoThroughWalls;
	private GameObserver gameObserver;

	public Snake(boolean canGoThroughWalls) {
		this.canGoThroughWalls = canGoThroughWalls;

		positions = new ArrayList<Point>();
		positions.add(new Point(2, 3));
		positions.add(new Point(3, 3));
		positions.add(new Point(4, 3));
		positions.add(new Point(5, 3));
		positions.add(new Point(6, 3));
		this.setLength(positions.size());

		currentDirection = UP;
	}

	public void move(String direction, Apple currentApple) {

		Point newHead = null;
		if (direction.equals(UP) && !currentDirection.equals(DOWN)) {

			newHead = new Point(positions.get(0).x - 1, positions.get(0).y);
			currentDirection = UP;

		} else if (direction.equals(RIGHT) && !currentDirection.equals(LEFT)) {

			newHead = new Point(positions.get(0).x, positions.get(0).y + 1);
			currentDirection = RIGHT;

		} else if (direction.equals(LEFT) && !currentDirection.equals(RIGHT)) {

			newHead = new Point(positions.get(0).x, positions.get(0).y - 1);
			currentDirection = LEFT;

		} else if (direction.equals(DOWN) && !currentDirection.equals(UP)) {

			newHead = new Point(positions.get(0).x + 1, positions.get(0).y);
			currentDirection = DOWN;

		} else if (direction.equals(STEP)) {
			move(currentDirection, currentApple);
		}
		if (newHead != null) {
			recalculatePositions(newHead);

			if (!(hitWall(newHead) || eatingMyself(newHead))) {
				if (headIsEatingApple(currentApple)) {
					positions.add(positions.get(length - 1));
					length++;
					if (getLength() == BOARD_SIZE * BOARD_SIZE - 1) {
						gameObserver.notifyWin();
					}
				}

			} else {
				gameObserver.notifyLoss();
			}
		}
	}

	public boolean headIsEatingApple(Apple currentApple) {
		Point head = positions.get(0);
		return (head.x == currentApple.x && head.y == currentApple.y);
	}

	public ArrayList<Point> getPositions() {
		return positions;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	private void recalculatePositions(Point newHead) {
		for (int i = length - 1; i > 0; i--) {
			positions.set(i,
					new Point(positions.get(i - 1).x, positions.get(i - 1).y));
		}
		normalizePoint(newHead);
		positions.set(0, newHead);
	}

	private void normalizePoint(Point newHead) {

		if (newHead.x < 0) {
			newHead.x = BOARD_SIZE - 1;
		}
		if (newHead.y < 0) {
			newHead.y = BOARD_SIZE - 1;
		}
		if (newHead.x > BOARD_SIZE - 1) {
			newHead.x = 0;
		}
		if (newHead.y > BOARD_SIZE - 1) {
			newHead.y = 0;
		}
	}

	private boolean hitWall(Point newPoint) {
		if (!canGoThroughWalls) {
			if (newPoint.x < 0 || newPoint.x >= BOARD_SIZE || newPoint.y < 0
					|| newPoint.y >= BOARD_SIZE) {
				return true;
			}
		}
		return false;
	}

	private boolean eatingMyself(Point newPoint) {
		for (int i = 1; i < length - 1; i++) {
			if (newPoint.x == positions.get(i).x
					&& newPoint.y == positions.get(i).y) {
				return true;
			}
		}
		return false;
	}

	public void setGameObserver(GameObserver gameObserver) {
		this.gameObserver = gameObserver;
	}

}
