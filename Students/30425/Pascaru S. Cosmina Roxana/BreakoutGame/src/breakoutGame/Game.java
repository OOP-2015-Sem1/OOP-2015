package breakoutGame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Game extends Canvas implements Runnable {

	// Game thread 
	Thread thread = new Thread(this);
	boolean running = false;

	// Window 
	JFrame frame;
	public static String windowTitle = "Breakout Game";
	public static int windowWidth = 800;
	public static int windowHeight = 600;
	public static Dimension gameDimension = new Dimension(windowWidth, windowHeight);

	// Rendering 
	public Canvas canvas;
	BufferedImage img = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
	BufferStrategy bs;
	Graphics2D g;

	// Entities
	public Paddle paddle;
	Ball ball;

	// Score variable
	public int score;
	public static final int TOTAL_SCORE = 45;

	public Brick[][] brick = new Brick[9][5];

	// Brick details
	private int brickWidth = 60;
	private int brickX = 100;
	private int brickY = 50;
	private int brickSpace = 2;
	private int brickHeight = 16;

	// Inputs
	public KeyboardHandler keys;

	@Override
	public void run() {
		while (running) {
			update();
			paintComponents();
			try {
				Thread.sleep(1000 / 60); 
			} catch (InterruptedException e) {

				e.printStackTrace();
			}		
		}
	}

	public synchronized void start() {
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		try {
			running = false;
			g.dispose();
			thread.join();
			System.exit(ABORT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Game() {
		init();
	}

	private void init() {
		
		// Create frame
		frame = new JFrame(windowTitle);
		frame.setVisible(true);
		frame.setSize(windowWidth, windowHeight);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create Canvas
		canvas = new Canvas();
		canvas.setSize(windowWidth, windowHeight);
		canvas.setPreferredSize(gameDimension);
		canvas.setMaximumSize(gameDimension);
		canvas.setMinimumSize(gameDimension);

		frame.add(canvas, BorderLayout.CENTER);

		// Init entities
		score=0;
		paddle = new Paddle(this);
		ball = new Ball(this);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				switch (j) {
				case 0:
					brick[i][j] = new Brick(this, Color.RED, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 1:
					brick[i][j] = new Brick(this, Color.ORANGE, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 2:
					brick[i][j] = new Brick(this, Color.YELLOW, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 3:
					brick[i][j] = new Brick(this, Color.CYAN, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 4:
					brick[i][j] = new Brick(this, Color.GREEN, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;

				}
			}
		}

		// Init inputs
		keys = new KeyboardHandler(canvas);
		canvas.requestFocus();

		// Init rendering 
		canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();

	}

	private void update() {
		paddle.update();
		ball.update();
	}

	private void paintComponents() {
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		g.drawImage(img, 0, 0, null);

		paddle.paintComponent(g);
		ball.paintComponent(g);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				brick[i][j].paintComponent(g);
			}
		}

		bs.show();

	}
	
	public void youLost(){
		JOptionPane.showMessageDialog(this, "More luck next time ...", "Game Over", JOptionPane.YES_OPTION);
		stop();
	}
	
	public void youWon(){
		
		JOptionPane.showMessageDialog(this, "You destroyed all the bricks!", "Congratlations", JOptionPane.CLOSED_OPTION);
		stop();
	}
	
	
}
