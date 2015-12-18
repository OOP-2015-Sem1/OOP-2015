package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import entities.BodyParts;
import entities.Food;

public class Screen extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	private Thread thread;
	private boolean running = false;
	private boolean inGame = true;
	ControlsPanel panelScore = new ControlsPanel();
	private int xCoor = 10, yCoor = 10;
	private int size = 5;
	private int initSize = 5;
	private int ticks = 0;

	private Random r;
	private BodyParts b;
	private ArrayList<BodyParts> snake;
	private Food ap;
	private ArrayList<Food> apples;

	public static int score=5;
	public static int speed =99999999;

	public Screen() {
		setFocussable(true);

		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		r = new Random();

		snake = new ArrayList<BodyParts>();
		apples = new ArrayList<Food>();

		start();
	}

	private void setFocussable(boolean c) {
		// TODO Auto-generated method stub

	}

	public void tick() {
		if (snake.size() == 0) {
			b = new BodyParts(xCoor, yCoor, 40);
			snake.add(b);
		}

		if (apples.size() == 0) {
			int xCoor = r.nextInt(19);
			int yCoor = r.nextInt(19);

			ap = new Food(xCoor, yCoor, 40);
			apples.add(ap);
		}
		for (int i = 0; i < apples.size(); i++) {
			if (xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
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
			if (Constants.right)
				xCoor++;
			if (Constants.left)
				xCoor--;
			if (Constants.up)
				yCoor--;
			if (Constants.down)
				yCoor++;
			ticks = 0;
			b = new BodyParts(xCoor, yCoor, 40);
			snake.add(b);
		}
		if (snake.size() > size) {
			snake.remove(0);
		}

	}

	public void paint(Graphics g) {

		g.clearRect(0, 0, WIDTH, HEIGHT);

		g.setColor(new Color(10, 80, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.BLACK);
		for (int i = 0; i < WIDTH / 40; i++) {
			g.drawLine(i * 40, 0, i * 40, HEIGHT);
		}
		for (int i = 0; i < HEIGHT / 40; i++) {
			g.drawLine(0, i * 40, WIDTH, i * 40);
		}

		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);

		}
		for (int i = 0; i < apples.size(); i++) {

			apples.get(i).draw(g);
		}

		if (inGame == false) {
			g.clearRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("GAME  OVER", 350, 400);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		while (running) {
			tick();
			repaint();
			score = snake.size() - initSize;
			panelScore.setScore(score);
		}
	}

}
