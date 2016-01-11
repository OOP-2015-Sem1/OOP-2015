package bricks.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener {

	public static Game game;

	public Renderer renderer;

	public int width = 800, height = 850;
	
	public Paddle paddle;

	public static Ball ball;

	public boolean left, right;

	public Game() {
		Timer timer = new Timer(20, this);
		JFrame frame = new JFrame("Mario Breakout");

		renderer = new Renderer();

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

	public static void main(String[] args) {
		game = new Game();
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
		g.setColor(new Color(96, 96, 96));
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		paddle.render(g);

		ball.render(g);

		Mario.createMario(g);
		
		
		
	}

	public void actionPerformed(ActionEvent e) {

		update();

		renderer.repaint();

	}

	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();
		if (id == KeyEvent.VK_LEFT) {
			left = true;
		} else if (id == KeyEvent.VK_RIGHT) {
			right = true;
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

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
