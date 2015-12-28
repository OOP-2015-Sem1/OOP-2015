package com.snake.models;

import static com.snake.components.SnakeFrame.BOARD_SIZE;

import java.awt.Point;
import java.util.Random;

public class Apple extends Point implements Apple_I {

	private static final long serialVersionUID = 2210411650970538925L;

	public void generateNewPosition(Snake theSnake) {
		Random r = new Random();
		this.x = r.nextInt(BOARD_SIZE);
		this.y = r.nextInt(BOARD_SIZE);
		while (arrivedAtApple(theSnake)) {
			generateNewPosition(theSnake);
		}
	}

	public boolean arrivedAtApple(Snake theSnake) {
		boolean didntGetThere = false;

		for (int i = 0; i < theSnake.getLength(); i++) {
			if (theSnake.getTheSnake().get(i).x == this.x
					&& theSnake.getTheSnake().get(i).y == this.y) {
				return true;
			}
		}
		return didntGetThere;
	}

}
