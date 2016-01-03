package gui;

import static entities.Constants.DOWN;
import static entities.Constants.LEFT;
import static entities.Constants.RIGHT;
import static entities.Constants.UP;

import java.awt.Graphics;
import java.util.ArrayList;

import entities.Constants;
import entities.SnakeBodyPart;

public class Snake {
	private SnakeBodyPart bodyPart;
	private ArrayList<SnakeBodyPart> snake;
	public static String currentDirection = RIGHT;
	public int xCoor = 10;
	public int yCoor = 10;

	public Snake() {
		snake = new ArrayList<SnakeBodyPart>();
	}

	public void makeSnake(int xCoor, int yCoor) {
		bodyPart = new SnakeBodyPart(xCoor, yCoor, Constants.DIMENSION / Constants.BOARD_SIZE);
		snake.add(bodyPart);

	}

	public void moveSnake(String direction) {

		if (direction.equals(RIGHT) && !getCurrentDirection().equals(LEFT)) {
			currentDirection = RIGHT;
			xCoor++;

		}
		if (direction.equals(LEFT) && !getCurrentDirection().equals(RIGHT)) {
			currentDirection = LEFT;
			xCoor--;

		}
		if (direction.equals(UP) && !getCurrentDirection().equals(DOWN)) {
			currentDirection = UP;
			yCoor--;

		}
		if (direction.equals(DOWN) && !getCurrentDirection().equals(UP)) {
			currentDirection = DOWN;
			yCoor++;

		}
	}

	public void setWallConditions() {
		if (xCoor < 0) {
			xCoor = Constants.BOARD_SIZE - 1;
		}
		if (xCoor > Constants.BOARD_SIZE - 1) {
			xCoor = 0;
		}
		if (yCoor < 0) {
			yCoor = Constants.BOARD_SIZE - 1;
		}
		if (yCoor > Constants.BOARD_SIZE - 1) {
			yCoor = 0;
		}
	}

	public void paint(Graphics g) {
		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
		}
	}

	public ArrayList<SnakeBodyPart> getSnake() {
		return snake;
	}

	public int getxCoor() {
		return xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public String getCurrentDirection() {
		return currentDirection;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

}
