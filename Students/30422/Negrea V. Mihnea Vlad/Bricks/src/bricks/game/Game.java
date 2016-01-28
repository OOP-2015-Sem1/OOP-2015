package bricks.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import Components.Paddle;
import Components.Ball;

public class Game implements ActionListener, KeyListener {

	//public static Game game;
	//public Game game;

	public Renderer renderer;

	public int width = 800, height = 850;

	public Paddle paddle;


	public boolean left, right;

	public Ball ball;

	public int gameStatus = 0;
	
	Mario mario = new Mario();

	public Game() {
		Timer timer = new Timer(20, this);
		JFrame frame = new JFrame("Mario Breakout");

		renderer = new Renderer(this);

		frame.setSize(width + 16, height + 39);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(renderer);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(this);



		start();
		timer.start();
	}

	public void start() {
		paddle = new Paddle(this);

		ball = new Ball(width / 2, height / 2);

	}



	public void update() {

		if (left) {
			paddle.move(true);
		}
		if (right) {
			paddle.move(false);
		}
		ball.move(this, paddle);

	}

	public void render(Graphics2D g) {
		

		if (gameStatus == 0) {
			g.setColor(new Color(20, 169, 20));
			g.fillRect(0, 0, width, height);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g.setColor(new Color(102, 0, 0));
			g.setFont(new Font("Arial", 1, 72));
			g.drawString("Mario Breakout", 145, 120);

			g.setFont(new Font("Arial", 1, 52));
			g.drawString("Press SPACE to Play/Pause", 60, 320);
			g.setFont(new Font("Arial", 1, 32));
			g.drawString("(use arrows to play)", 240, 360);

		}

		if (gameStatus == 2 || gameStatus == 1) {

			g.setColor(new Color(96, 96, 96));
			g.fillRect(0, 0, width, height);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 22));
			g.drawString("Score: " + mario.getScore(), 10, 23);
			g.drawString("Lives: " + ball.getLives(), 710, 23);

			paddle.render(g);

			ball.render(g);

			mario.createMario(g, ball);
		}
		if (mario.getScore() == 21300 || ball.getLives() == 0) {
			gameStatus = 3;
		}

		if (gameStatus == 3) {
			if (mario.getScore() == 21300) {

				g.setColor(new Color(20, 169, 20));
				g.fillRect(0, 0, width, height);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g.setColor(new Color(102, 0, 0));
				g.setFont(new Font("Arial", 1, 100));
				g.drawString("WINNER!!!", 160, 200);
				g.setFont(new Font("Arial", 1, 42));
				g.drawString("Press SPACE to play again", 135, 320);

			}
			if (ball.getLives() == 0) {
				g.setColor(new Color(20, 169, 20));
				g.fillRect(0, 0, width, height);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g.setColor(new Color(102, 0, 0));
				g.setFont(new Font("Arial", 1, 72));
				g.drawString("YOU LOST", 210, 180);
				g.drawString("Your score: " + mario.getScore(), 160, 260);
				g.setFont(new Font("Arial", 1, 42));
				g.drawString("Press SPACE to play again", 135, 320);

			}

		}

		if (gameStatus == 1) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 72));
			g.drawString("PAUSE", 280, 600);

		}

	}

	public void actionPerformed(ActionEvent e) {

		if (gameStatus == 2) {
			update();
		}

		renderer.repaint();

	}

	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();
		if (id == KeyEvent.VK_LEFT) {
			left = true;
		} else if (id == KeyEvent.VK_RIGHT) {
			right = true;
		} else if (id == KeyEvent.VK_SPACE) {
			if (gameStatus == 0) {
				gameStatus = 2;
			} else if (gameStatus == 2) {
				gameStatus = 1;
			} else if (gameStatus == 1) {
				gameStatus = 2;
			} else if (gameStatus == 3) {
				gameStatus = 2;
				ball.setLives(3);
				mario.setScore(0);
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();
		if (id == KeyEvent.VK_LEFT) {
			left = false;
		} else if (id == KeyEvent.VK_RIGHT) {
			right = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
