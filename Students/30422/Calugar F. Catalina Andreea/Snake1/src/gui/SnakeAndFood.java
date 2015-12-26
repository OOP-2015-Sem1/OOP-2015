package gui;

import static entities.Constants.DOWN;
import static entities.Constants.LEFT;
import static entities.Constants.RIGHT;
import static entities.Constants.UP;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import entities.Constants;
import entities.FoodPart;
import entities.SnakeBodyPart;

public class SnakeAndFood extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private boolean running = false;
	private boolean inGame = true;

	public int xCoor = 10;
	public int yCoor = 10;
	private int size = 5;
	private int ticks = 0;
	public int score = 0;
	public static int speed = 1000000;

	private SnakeBodyPart bodyPart;
	private ArrayList<SnakeBodyPart> snake;

	public static String currentDirection = RIGHT;
	private SnakeBodyPart food;
	private ArrayList<FoodPart> apple;
	private Random random;

	private DrawGameGraphics gamePanel = new DrawGameGraphics();

	public SnakeAndFood() {

		apple = new ArrayList<FoodPart>();
		random = new Random();
		snake = new ArrayList<SnakeBodyPart>();

		start();
	}

	public void moveSnake(String direction) {
		if (direction.equals(RIGHT) && !SnakeAndFood.currentDirection.equals(LEFT)) {
			SnakeAndFood.currentDirection = RIGHT;
			xCoor++;
		}
		if (direction.equals(LEFT) && !SnakeAndFood.currentDirection.equals(RIGHT)) {
			SnakeAndFood.currentDirection = LEFT;
			xCoor--;
		}
		if (direction.equals(UP) && !SnakeAndFood.currentDirection.equals(DOWN)) {
			SnakeAndFood.currentDirection = UP;
			yCoor--;
		}
		if (direction.equals(DOWN) && !SnakeAndFood.currentDirection.equals(UP)) {
			SnakeAndFood.currentDirection = DOWN;
			yCoor++;
		}
	}

	public void tick() {
		if (snake.size() == 0) {
			bodyPart = new SnakeBodyPart(xCoor, yCoor, Constants.DIMENSION / Constants.BOARD_SIZE);
			snake.add(bodyPart);
		}

		if (apple.size() == 0) {
			int xCoor = random.nextInt(Constants.BOARD_SIZE - 1);
			int yCoor = random.nextInt(Constants.BOARD_SIZE);

			food = new FoodPart(xCoor, yCoor, Constants.DIMENSION / Constants.BOARD_SIZE);
			apple.add((FoodPart) food);
		}
		for (int i = 0; i < apple.size(); i++) {
			if (xCoor == apple.get(i).getxCoor() && yCoor == apple.get(i).getyCoor()) {
				size++;
				score++;
				GameFrame.scr.setText(String.valueOf("Score: " + score));
				apple.remove(i);
				i--;
			}
		}

		for (int i = 0; i < snake.size(); i++) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if (i != snake.size() - 1) {
					inGame = false;
				}
			}
		}

		if (Constants.canWeGoThroughWalls) {
			if (xCoor < 0)
				xCoor = Constants.BOARD_SIZE - 1;
			if (xCoor > Constants.BOARD_SIZE - 1)
				xCoor = 0;
			if (yCoor < 0)
				yCoor = Constants.BOARD_SIZE - 1;
			if (yCoor > Constants.BOARD_SIZE - 1)
				yCoor = 0;
		} else {
			if (xCoor < 0 || xCoor > Constants.BOARD_SIZE - 1 || yCoor < 0 || yCoor > Constants.BOARD_SIZE - 1) {
				inGame = false;
			}
		}

		if (!inGame) {
			stop();
		}
		ticks++;

		if (ticks > speed) {
			moveSnake(currentDirection);
			ticks = 0;
			bodyPart = new SnakeBodyPart(this.xCoor, this.yCoor, Constants.DIMENSION / Constants.BOARD_SIZE);
			snake.add(bodyPart);
		}
		if (snake.size() > size) {
			snake.remove(0);
		}
	}

	public void paint(Graphics g) {

		gamePanel.drawPanel(g);
		for (int i = 0; i < Constants.DIMENSION / Constants.BOARD_SIZE; i++) {
			g.drawLine(i * Constants.DIMENSION / Constants.BOARD_SIZE, 0,
					i * Constants.DIMENSION / Constants.BOARD_SIZE, Constants.DIMENSION);
		}
		for (int i = 0; i < Constants.DIMENSION / Constants.BOARD_SIZE; i++) {
			g.drawLine(0, i * Constants.DIMENSION / Constants.BOARD_SIZE, Constants.DIMENSION,
					i * Constants.DIMENSION / Constants.BOARD_SIZE);
		}

		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
		}

		for (int i = 0; i < apple.size(); i++) {
			apple.get(i).draw(g);
		}

		if (inGame == false) {
			if (score < 395) {
				gamePanel.drawAtLoss(g);
			} else {
				gamePanel.drawAtWin(g);
			}
		}
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Game Loop");
		thread.start();
	}

	public void stop() {
		running = false;
		score = 0;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			tick();
			repaint();
		}
	}

}