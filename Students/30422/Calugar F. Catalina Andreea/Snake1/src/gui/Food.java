package gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import entities.Constants;
import entities.FoodPart;
import entities.SnakeBodyPart;

public class Food {
	private SnakeBodyPart food;
	private ArrayList<FoodPart> apple;

	public int score = 0;
	private int snakeSize = 5;

	Food() {
		apple = new ArrayList<FoodPart>();
	}

	public void tickFood(int xCoor, int yCoor) {

		if (apple.size() == 0) {
			Random random = new Random();
			int xCoorFood = random.nextInt(Constants.BOARD_SIZE - 1);
			int yCoorFood = random.nextInt(Constants.BOARD_SIZE - 1);
			food = new FoodPart(xCoorFood, yCoorFood, Constants.DIMENSION / Constants.BOARD_SIZE);
			apple.add((FoodPart) food);
		}
		for (int i = 0; i < apple.size(); i++) {
			if (xCoor == apple.get(i).getxCoor() && yCoor == apple.get(i).getyCoor()) {
				snakeSize++;
				setSnakeSize(snakeSize);
				apple.remove(i);
				i--;
				score++;
				setScore(score);
				GameFrame.showScoreButton.setText(String.valueOf("Score: " + score));
			}
		}
	}

	public void paint(Graphics g) {
		for (int i = 0; i < apple.size(); i++) {
			apple.get(i).draw(g);

		}
	}

	public ArrayList<FoodPart> getApple() {
		return apple;
	}

	public int getScore() {
		return score;
	}

	public int getSnakeSize() {
		return snakeSize;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setSnakeSize(int size) {
		this.snakeSize = size;
	}

}
