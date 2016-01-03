package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import entities.Constants;

public class Screen extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private boolean running = false;
	private boolean inGame = true;

	private int xCoorCurrent = 10;
	private int yCoorCurrent = 10;
	private int ticks = 0;
	public static int speed = 10000000; // default as EASY

	private DrawGameGraphics gamePanel = new DrawGameGraphics();
	private Food food = new Food();
	private Snake snake = new Snake();
	private PaintSnakeBoard paintBoard;

	public Screen() {
		start();
	}

	public void tick() {

		xCoorCurrent = snake.getxCoor();
		yCoorCurrent = snake.getyCoor();

		food.tickFood(this.xCoorCurrent, this.yCoorCurrent);

		if (snake.getSnake().size() == 0) {
			snake.makeSnake(this.xCoorCurrent, this.yCoorCurrent);
		}

		for (int i = 0; i < snake.getSnake().size(); i++) {
			if (xCoorCurrent == snake.getSnake().get(i).getxCoor()
					&& yCoorCurrent == snake.getSnake().get(i).getyCoor()) {
				if (i != snake.getSnake().size() - 1) {
					inGame = false;
				}
			}
		}

		if (Constants.canWeGoThroughWalls) {
			snake.setWallConditions();
		} else {
			if (xCoorCurrent < 0 || xCoorCurrent > Constants.BOARD_SIZE - 1 || yCoorCurrent < 0
					|| yCoorCurrent > Constants.BOARD_SIZE - 1) {
				inGame = false;
			}
		}
		if (!inGame) {
			stop();
		}
		ticks++;

		if (ticks > speed) {

			move(snake.getCurrentDirection());
			snake.makeSnake(snake.getxCoor(), snake.getyCoor());
			ticks = 0;
		}
		if (snake.getSnake().size() > food.getSnakeSize()) {
			snake.getSnake().remove(0);
		}

	}

	public void move(String direction) {
		snake.moveSnake(snake.getCurrentDirection());
	}

	public void paint(Graphics g) {
		paintBoard = new PaintSnakeBoard();
		paintBoard.paint(g);
		snake.paint(g);
		food.paint(g);

		if (inGame == false) {
			if (food.getScore() < 395) {
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
		food.setScore(0);
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