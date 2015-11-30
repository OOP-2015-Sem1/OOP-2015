package com.snake.models;

import java.awt.Point;
import java.util.ArrayList;

import com.snake.components.SnakeFrame;
import com.snake.pack.Constants;

public class Snake extends SnakeModel{


	public Snake(boolean canGoThroughWalls) {
		this.canGoThroughWalls = canGoThroughWalls;

		snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(2, 3));
		snakePoints.add(new Point(3, 3));
		snakePoints.add(new Point(4, 3));
		snakePoints.add(new Point(5, 3));
		snakePoints.add(new Point(6, 3));
		this.setLength(snakePoints.size());

		currDir = Constants.UP;
	}

	public boolean move(String direction, Apple currentApple) {

		if (direction.equals(Constants.UP)) {

			Point newPoint = new Point(snakePoints.get(0).x - 1, snakePoints.get(0).y);
			if (!canGetThere(newPoint)) {
				nextPositionOfHead = newPoint;
				if (headIsEatingApple(currentApple)) {
					snakePoints.add(currentApple);
					len++;
				}

				recalculatePositions(nextPositionOfHead);
				currDir = Constants.UP;
				return true;
			} else
				return false;

		} else if (direction.equals(Constants.RIGHT)) {

			Point newPoint = new Point(snakePoints.get(0).x, snakePoints.get(0).y + 1);
			if (!canGetThere(newPoint)) {
				nextPositionOfHead = newPoint;
				if (headIsEatingApple(currentApple)) {
					snakePoints.add(currentApple);
					len++;
				}

				recalculatePositions(nextPositionOfHead);
				currDir = Constants.RIGHT;
				return true;
			} else
				return false;

		} else if (direction.equals(Constants.LEFT)) {

			Point newPoint = new Point(snakePoints.get(0).x, snakePoints.get(0).y - 1);
			if (!canGetThere(newPoint)) {
				nextPositionOfHead = newPoint;
				if (headIsEatingApple(currentApple)) {
					snakePoints.add(currentApple);
					len++;
				}

				recalculatePositions(nextPositionOfHead);
				currDir = Constants.LEFT;
				return true;
			} else
				return false;

		} else if (direction.equals(Constants.DOWN)) {

			Point newPoint = new Point(snakePoints.get(0).x + 1, snakePoints.get(0).y);
			if (!canGetThere(newPoint)) {
				nextPositionOfHead = newPoint;
				if (headIsEatingApple(currentApple)) {
					snakePoints.add(currentApple);
					len++;
				}

				recalculatePositions(nextPositionOfHead);
				currDir = Constants.DOWN;
				return true;
			} else
				return false;

		} else if (direction.equals(Constants.STEP)) {
			return move(currDir, currentApple);
		} else
			return false;
	}

	public boolean headIsEatingApple(Apple currentApple) {
		return (nextPositionOfHead.x == currentApple.x && nextPositionOfHead.y == currentApple.y);
	}

	public boolean headIsEatingApple(Apple currentApple, Point newHead) {
		return (newHead.x == currentApple.x && newHead.y == currentApple.y);
	}

	protected void recalculatePositions(Point newHead) {
		for (int i = len - 1; i > 0; i--) {
			snakePoints.set(i, new Point(snakePoints.get(i - 1).x, snakePoints.get(i - 1).y));
		}
		normalizePoint(newHead);
		snakePoints.set(0, newHead);
	}

	private void normalizePoint(Point newHead) {

		if (newHead.x < 0) {
			newHead.x = SnakeFrame.BOARD_SIZE - 1;
		}
		if (newHead.y < 0) {
			newHead.y = SnakeFrame.BOARD_SIZE - 1;
		}
		if (newHead.x > SnakeFrame.BOARD_SIZE - 1) {
			newHead.x = 0;
		}
		if (newHead.y > SnakeFrame.BOARD_SIZE - 1) {
			newHead.y = 0;
		}
	}

	private boolean canGetThere(Point newPoint) {
		boolean getThere = false;

		for (int i = 1; i < len; i++) {
			if (newPoint.x == snakePoints.get(i).x && newPoint.y == snakePoints.get(i).y) {
				getThere = true;
			}
		}

		if (!canGoThroughWalls) {
			if (newPoint.x < 0 || newPoint.x >= SnakeFrame.BOARD_SIZE
					|| newPoint.y < 0 || newPoint.y >= SnakeFrame.BOARD_SIZE) {
				getThere = true;
			}
		}

		return getThere;
	}

	public ArrayList<Point> getTheSnake() {
		return snakePoints;
	}

	public int getLength() {
		return len;
	}

	public void setLength(int length) {
		this.len = length;
	}
}
