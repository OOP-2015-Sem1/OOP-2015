
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GameShapes.Ball;
import GameShapes.Brick;
import GameShapes.Paddle;

public class Screen extends JPanel implements Runnable {

	private int MENU_WIDTH = 915;
	private int MENU_HEIGHT = 850;
	private volatile boolean running = false;
	private Thread thread;
	private Container c;
	private Ball ball;
	protected Paddle paddle;
	public Graphics g;
	private Brick[][] brick = new Brick[1000][1000];

	public Screen() {
		createGameComponents();
		start();
	}

	public void start() {

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		running = true;
		while (running == true) {
			repaint();
			update();
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		ball.Paint(g);
		for (int j = 35; j <= 235; j += 20)
			switch (j) {

			case 55:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.red, i, j, ball);
					}
				}
				break;
			case 75:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.red, i, j, ball);
					}
				}
				break;

			case 95:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.orange, i, j, ball);
					}
				}
				break;
			case 115:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.orange, i, j, ball);
					}
				}
				break;
			case 135:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.yellow, i, j, ball);
					}
				}
				break;
			case 155:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.yellow, i, j, ball);
					}
				}
				break;
			case 175:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.green, i, j, ball);
					}
				}
				break;
			case 195:
				for (int i = 15; i <= 785; i += 110) {
					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.green, i, j, ball);
					}
				}
				break;
			case 215:
				for (int i = 15; i <= 785; i += 110) {

					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.cyan, i, j, ball);
					}
				}
				break;

			case 235:

				for (int i = 15; i <= 785; i += 110) {

					if (brick[i][j].getVisibility() == false) {
						brick[i][j] = new Brick(i, j);
						brick[i][j].Paint(g, Color.cyan, i, j, ball);
					}
				}
				break;
			}
		paddle.Paint(g);
	}

	private void update() {

		ball.move(paddle, brick);

	}

	private void createBricks() {
		for (int i = 0; i < 915; i++)
			for (int j = 0; j < 915; j++) {
				brick[i][j] = new Brick(i, j);
				brick[i][j].setVisiblity(false);
			}
	}

	private void createGameComponents() {
		setBackground(Color.white);
		JFrame menu = new JFrame("Breakout");
		menu.setSize(MENU_WIDTH, MENU_HEIGHT);
		menu.setVisible(true);

		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ball = new Ball(200, 295, Color.BLACK, menu);
		paddle = new Paddle(400, 780, menu);
		c = menu.getContentPane();
		c.add(this);
		paddle.movePaddle(menu);
		createBricks();
	}

}
