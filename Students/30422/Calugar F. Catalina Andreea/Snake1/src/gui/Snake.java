package gui;

import static gui.Constants.DOWN;
import static gui.Constants.LEFT;
import static gui.Constants.RIGHT;
import static gui.Constants.UP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import entities.BodyPart;
import entities.Food;

public class Snake extends JPanel implements Runnable {

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

	private Random random;
	private BodyPart bodyPart;
	private ArrayList<BodyPart> snake;
	private BodyPart food;
	private ArrayList<Food> apple;
	public static String currentDirection = RIGHT;

	public static JButton scr;

	public Snake() {

		setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		random = new Random();
		snake = new ArrayList<BodyPart>();
		apple = new ArrayList<Food>();
		scr = new JButton("Score: 0");
		scr.setEnabled(false);
		scr.setBackground(Color.WHITE);

		start();
	}

	public void moveSnake(String direction) {
		if (direction.equals(RIGHT) && !Snake.currentDirection.equals(LEFT)) {
			Snake.currentDirection = RIGHT;
			xCoor++;
		}
		if (direction.equals(LEFT) && !Snake.currentDirection.equals(RIGHT)) {
			Snake.currentDirection = LEFT;
			xCoor--;
		}
		if (direction.equals(UP) && !Snake.currentDirection.equals(DOWN)) {
			Snake.currentDirection = UP;
			yCoor--;
		}
		if (direction.equals(DOWN) && !Snake.currentDirection.equals(UP)) {
			Snake.currentDirection = DOWN;
			yCoor++;
		}
	}

	public void tick() {
		if (snake.size() == 0) {
			bodyPart = new BodyPart(xCoor, yCoor, 40);
			snake.add(bodyPart);
		}

		if (apple.size() == 0) {
			int xCoor = random.nextInt(19);
			int yCoor = random.nextInt(19);

			food = new Food(xCoor, yCoor, 40);
			apple.add((Food) food);
		}
		for (int i = 0; i < apple.size(); i++) {
			if (xCoor == apple.get(i).getxCoor() && yCoor == apple.get(i).getyCoor()) {
				size++;
				score++;
				scr.setText(String.valueOf("Score: " + this.score));
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
				xCoor = 19;
			if (xCoor > 19)
				xCoor = 0;
			if (yCoor < 0)
				yCoor = 19;
			if (yCoor > 19)
				yCoor = 0;
		} else {
			if (xCoor < 0 || xCoor > 19 || yCoor < 0 || yCoor > 19) {
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
			bodyPart = new BodyPart(this.xCoor, this.yCoor, 40);
			snake.add(bodyPart);
		}
		if (snake.size() > size) {
			snake.remove(0);
		}
	}

	public void paint(Graphics g) {

		g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		g.setColor(new Color(10, 80, 0));
		g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		g.setColor(Color.BLACK);

		for (int i = 0; i < Constants.WIDTH / 40; i++) {
			g.drawLine(i * 40, 0, i * 40, Constants.HEIGHT);
		}
		for (int i = 0; i < Constants.HEIGHT / 40; i++) {
			g.drawLine(0, i * 40, Constants.WIDTH, i * 40);
		}

		for (int i = 0; i < snake.size(); i++) {

			snake.get(i).draw(g);

		}
		for (int i = 0; i < apple.size(); i++) {
			apple.get(i).draw(g);
		}
		
		if (inGame == false) {
			if (score < 395) {
				g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
				g.setColor(Color.WHITE);
				g.drawString("GAME  OVER", 350, 400);
			} else {
				g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
				g.setColor(new Color(10, 80, 0));
				g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
				g.setColor(Color.YELLOW);
				g.drawString("YOU WON!", 350, 400);
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
