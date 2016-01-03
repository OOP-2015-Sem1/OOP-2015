package com.snake.model;

import static com.snake.ui.SnakeFrame.BOARD_SIZE;

import java.awt.Point;
import java.util.Random;

public class Apple extends Point {

	private static final long serialVersionUID = 2210411650970538925L;

	public void generateNewPosition(Snake snake) {
		Random r = new Random();
		this.x = r.nextInt(BOARD_SIZE);
		this.y = r.nextInt(BOARD_SIZE);
		while (appleIntersectsWithSnake(snake)) {
			generateNewPosition(snake);
		}
	}

	private boolean appleIntersectsWithSnake(Snake snake) {
		boolean intersects = false;

		for (int i = 0; i < snake.getLength(); i++) {
			if (snake.getPositions().get(i).x == this.x
					&& snake.getPositions().get(i).y == this.y) {
				return true;
			}
		}
		return intersects;
	}

}
