package breakoutGame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Game thread
	Thread thread = new Thread(this);
	boolean running = false;

	// Window
	JFrame frame;
	private static String WINDOW_TITLE = "Breakout Game";
	public static int WINDOW_WIDTH = 800;
	public static int WINDOW_HEIGHT = 600;
	public static Dimension GAME_DIMENSION = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

	// Drawings
	public Canvas canvas;
	BufferedImage img = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
	BufferStrategy bs;
	public Graphics2D g;

	// Entities
	public Paddle paddle = new Paddle(this);
	public Ball ball = new Ball();
	public Brick[][] brick = new Brick[9][5];

	// Total score
	public static final int TOTAL_SCORE_LEVEL1 = 45;
	public static final int TOTAL_SCORE_LEVEL2 = 37;
	public static final int TOTAL_SCORE_LEVEL3 = 45;

	// Inputs
	public KeyboardHandler keys;

	// Levels
	Level currentLevel = new Level1(this, ball, brick, paddle);
	int level = 0;

	// Score panel
	JPanel score = new JPanel();
	public static Dimension SCORE_PANEL_DIMENSION = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT / 27);
	JLabel scoreLabel = new JLabel("Score: 0");
	JLabel lifeLabel = new JLabel("Left lives: 0");

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

		score.setBackground(Color.GRAY);
		score.setPreferredSize(SCORE_PANEL_DIMENSION);
		score.setLayout(new BorderLayout());
		
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 14));
		scoreLabel.setForeground(Color.BLACK);

		lifeLabel.setFont(new Font("Serif", Font.BOLD, 14));
		lifeLabel.setForeground(Color.BLACK);

		score.add(scoreLabel, BorderLayout.WEST);
		score.add(lifeLabel, BorderLayout.EAST);

		frame.add(score, BorderLayout.SOUTH);

		// Level
		currentLevel.init();

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

		// Paddle intersection
		if (ball.surface.intersects(paddle.surface)) {
			ball.ballYmove = -ball.ballSpeed;
		}

		// Update level
		currentLevel.update();
		
		//Update score panel
		scoreLabel.setText("Score: " + currentLevel.getScore());
		lifeLabel.setText("Left lives: " + currentLevel.getLives());

		if (ball.ballY <= 0)
			ball.ballYmove = ball.ballSpeed;

		if (ball.ballX <= 0)
			ball.ballXmove = ball.ballSpeed;

		if (ball.ballX + ball.ballDiameter >= canvas.getWidth())
			ball.ballXmove = -ball.ballSpeed;

		if (ball.ballY + ball.ballDiameter >= canvas.getHeight()) {
			if (currentLevel.getLives() > 0) {
				tryAgain();
			} else {
				youLost();
			}
		}

		if (currentLevel.isComplete()) {
			level++;
			goToNextLevel();
		}

	}

	private void paintComponents() {
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		g.drawImage(img, 0, 0, null);

		// Paint entities
		paddle.paintComponent(g);
		ball.paintComponent(g);

		// Paint current level components
		currentLevel.paintComponents(g);

		bs.show();

	}

	public void startGame() {
		JOptionPane.showMessageDialog(this,
				"You can die " + currentLevel.getLives() + " time(s)." + "Click OK to start!", "Start Game",
				JOptionPane.CLOSED_OPTION);
		currentLevel.setComponentsSpeed();
		currentLevel.resetComponentsPosition();
		init();
	}

	public void restartGame() {
		currentLevel.resetScore();
		currentLevel.setComponentsSpeed();
		currentLevel.resetComponentsPosition();
		init();
	}

	public void youLost() {
		JOptionPane.showMessageDialog(this, "More luck next time ...", "Game Over", JOptionPane.YES_OPTION);
		stop();
	}

	public void youWon() {
		JOptionPane.showMessageDialog(this, "You destroyed all the bricks!", "Congratlations",
				JOptionPane.CLOSED_OPTION);
		stop();
	}

	public void tryAgain() {
		int currentLives = currentLevel.getLives();
		currentLevel.setLives(--currentLives);
		JOptionPane.showMessageDialog(this, "Try again", "Ooops", JOptionPane.CLOSED_OPTION);
		restartGame();
	}

	public void goToNextLevel() {
		JOptionPane.showMessageDialog(this, "Click OK to move on to the next level", "Congratlations",
				JOptionPane.CLOSED_OPTION);

		switch (level) {
		case 1:
			currentLevel = new Level2(this, ball, brick, paddle);
			startGame();
			break;

		case 2:
			currentLevel = new Level3(this, ball, brick, paddle);
			startGame();
			break;

		case 3:
			youWon();
			break;
		}
	}
}
