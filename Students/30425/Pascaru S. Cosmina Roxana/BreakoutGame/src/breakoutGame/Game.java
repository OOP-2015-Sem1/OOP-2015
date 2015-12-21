package breakoutGame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	public static String WINDOW_TITLE = "Breakout Game";
	public static int WINDOW_WIDTH = 800;
	public static int WINDOW_HEIGHT = 600;
	public static Dimension GAME_DIMENSION = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

	// Drawings 
	public Canvas canvas;
	BufferedImage img = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
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
		startGame();
	}

	private void init() {
		
		// Create frame
		frame = new JFrame(WINDOW_TITLE);
		frame.setVisible(true);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create Canvas
		canvas = new Canvas();
		canvas.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		canvas.setPreferredSize(GAME_DIMENSION);
		canvas.setMaximumSize(GAME_DIMENSION);
		canvas.setMinimumSize(GAME_DIMENSION);

		frame.add(canvas, BorderLayout.CENTER);

		// Initiate entities
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

		// Initiate inputs
		keys = new KeyboardHandler(canvas);
		canvas.requestFocus();

		// Initiate background
		canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();

	}

	private void update() {
		paddle.update();
		ball.update();
		
		//Paddle intersection
		if (ball.entityCollider.intersects(paddle.entityCollider)){
			ball.ballYmove = -ball.ballSpeed;
		}
			

		//Brick intersection
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				
				if ((ball.entityCollider.intersects(brick[i][j].entityCollider))) {
					int ballLeft = (int) ball.entityCollider.getMinX();
					int ballHeight = (int) ball.entityCollider.getHeight();
					int ballWidth = (int) ball.entityCollider.getWidth();
					int ballTop = (int) ball.entityCollider.getMinY();

					Point pointRight = new Point(ballLeft + ballWidth , ballTop);
					Point pointLeft = new Point(ballLeft , ballTop);
					Point pointTop = new Point(ballLeft, ballTop );
					Point pointBottom = new Point(ballLeft, ballTop + ballHeight );

					if (!brick[i][j].isDestroyed()) {
						if (brick[i][j].entityCollider.contains(pointRight)) {
							ball.ballXmove = - ball.ballSpeed;
						} else if (brick[i][j].entityCollider.contains(pointLeft)) {
							ball.ballXmove = ball.ballSpeed;
						}

						if (brick[i][j].entityCollider.contains(pointTop)) {
							ball.ballYmove = ball.ballSpeed;
						} else if (brick[i][j].entityCollider.contains(pointBottom)) {
							ball.ballYmove = - ball.ballSpeed;
						}
						brick[i][j].setIsDestroyed(true);
						score++;
						
					}
				}
				
			}
		}
		

		if (ball.ballY <= 0)
			ball.ballYmove = ball.ballSpeed;

		if (ball.ballX <= 0)
			ball.ballXmove = ball.ballSpeed;

		if (ball.ballX + ball.ballDiameter >= canvas.getWidth())
			ball.ballXmove = -ball.ballSpeed;

		if (ball.ballY + ball.ballDiameter >= canvas.getHeight())
			youLost();
		
		if (score == TOTAL_SCORE)
			youWon();
		
		
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
				if(!brick[i][j].isDestroyed())
				brick[i][j].paintComponent(g);
			}
		}

		bs.show();

	}
	
	public void startGame(){
		JOptionPane.showMessageDialog(this, "Click OK to begin", "Start Game", JOptionPane.CLOSED_OPTION);
		init();
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
