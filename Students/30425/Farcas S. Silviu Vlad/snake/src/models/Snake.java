package models;

import java.awt.Dimension;
import models.*;
import controllers.*;
import views.*;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {

	public int menu = 0;

	public static Snake snake;

	public JFrame jframe;

	public RenderPanel renderPanel;

	public Timer timer = new Timer(20, this);

	public ArrayList<Point> snakeParts = new ArrayList<Point>();

	public ArrayList<Point> obstacles = new ArrayList<Point>();

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;

	public int ticks = 0, direction = DOWN, score, tailLength = 10, time;

	public Point head, cherry;

	public Random random;

	public boolean over = false, paused;

	public Dimension dim;

	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(StartMenu.small * 400 + StartMenu.medium * 600 + StartMenu.large * 800,
				StartMenu.small * 300 + StartMenu.medium * 500 + StartMenu.large * 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		jframe.add(renderPanel = new RenderPanel());
		startGame();

	}

	public void startGame() {
		over = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 14;
		ticks = 0;
		direction = DOWN;
		head = new Point(0, -1);
		random = new Random();
		snakeParts.clear();
		cherry = new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
				random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
		if (StartMenu.easy == 1) {
			obstacles = null;
		}
		if (StartMenu.normal == 1) {
			for (int i = 0; i < StartMenu.small * 20 + StartMenu.medium * 40 + StartMenu.large * 80; i++) {
				obstacles.add(
						new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
			}
		}
		if (StartMenu.hard == 1) {
			for (int i = 0; i < StartMenu.small * 40 + StartMenu.medium * 80 + StartMenu.large * 160; i++) {
				obstacles.add(
						new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66)));
			}
		}
		do {
			cherry = new Point(random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
					random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
		} while (noCherryAtObstacle() == false);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		renderPanel.repaint();
		ticks++;

		if (ticks % 2 == 0 && head != null && !over && !paused) {

			time++;

			snakeParts.add(new Point(head.x, head.y));

			if (direction == UP) {
				if (StartMenu.goThroughWalls == 0) {
					if (noTailAndNoObstacleAt(head.x, head.y - 1)) {
						if (head.y - 1 >= 0) {
							head = new Point(head.x, head.y - 1);
						} else {
							over = true;
						}
					} else {
						over = true;
					}
				} else {
					if (head.y - 1 >= 0) {
						if (noTailAndNoObstacleAt(head.x, head.y - 1)) {
							head = new Point(head.x, head.y - 1);
						} else {
							over = true;
						}
					} else {
						if (noTailAndNoObstacleAt(head.x,
								StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67)) {
							head = new Point(head.x,
									StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67);
						} else {
							over = true;
						}
					}
				}
			}

			if (direction == DOWN) {
				if (StartMenu.goThroughWalls == 0) {
					if (noTailAndNoObstacleAt(head.x, head.y + 1)) {
						if (head.y + 1 < StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67) {
							head = new Point(head.x, head.y + 1);
						} else {
							over = true;
						}
					} else {
						over = true;
					}
				} else {
					if (head.y + 1 < StartMenu.small * 27 + StartMenu.medium * 47 + StartMenu.large * 67) {
						if (noTailAndNoObstacleAt(head.x, head.y + 1)) {
							head = new Point(head.x, head.y + 1);
						} else {
							over = true;
						}
					} else {
						if (noTailAndNoObstacleAt(head.x, 0)) {
							head = new Point(head.x, 0);
						} else {
							over = true;
						}
					}
				}
			}

			if (direction == LEFT) {
				if (StartMenu.goThroughWalls == 0) {
					if (noTailAndNoObstacleAt(head.x - 1, head.y)) {
						if (head.x - 1 >= 0) {
							head = new Point(head.x - 1, head.y);
						} else {
							over = true;
						}
					} else {
						over = true;
					}
				} else {
					if (head.x - 1 >= 0) {
						if (noTailAndNoObstacleAt(head.x - 1, head.y)) {
							head = new Point(head.x - 1, head.y);
						} else {
							over = true;
						}
					} else {
						if (noTailAndNoObstacleAt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79,
								head.y)) {
							head = new Point(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79,
									head.y);
						} else {
							over = true;
						}
					}
				}
			}

			if (direction == RIGHT) {
				if (StartMenu.goThroughWalls == 0) {
					if (noTailAndNoObstacleAt(head.x + 1, head.y)) {
						if (head.x + 1 < StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79) {
							head = new Point(head.x + 1, head.y);
						} else {
							over = true;
						}
					} else {
						over = true;
					}
				} else {
					if (head.x + 1 < StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79) {
						if (noTailAndNoObstacleAt(head.x + 1, head.y)) {
							head = new Point(head.x + 1, head.y);
						} else {
							over = true;
						}
					} else {
						if (noTailAndNoObstacleAt(0, head.y)) {
							head = new Point(0, head.y);
						} else {
							over = true;
						}
					}
				}
			}

			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);

			}

			if (cherry != null) {
				if (head.equals(cherry)) {
					score += 10;
					tailLength++;
					do {
						cherry.setLocation(
								random.nextInt(StartMenu.small * 39 + StartMenu.medium * 59 + StartMenu.large * 79),
								random.nextInt(StartMenu.small * 26 + StartMenu.medium * 46 + StartMenu.large * 66));
					} while (noCherryAtObstacle() == false);
				}
			}
		}
	}

	public boolean noTailAndNoObstacleAt(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		if (obstacles != null) {
			for (Point point : obstacles) {
				if (point.equals(new Point(x, y))) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean noCherryAtObstacle() {
		if (obstacles == null) {
			return true;
		}
		for (Point point : obstacles) {
			if (cherry.equals(point)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT) {
			direction = LEFT;
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT) {
			direction = RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN) {
			direction = UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP) {
			direction = DOWN;
		}

		if (i == KeyEvent.VK_SPACE) {
			if (over) {
				jframe.dispose();
			} else {
				paused = !paused;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("Menu");
		jf.setVisible(true);
		jf.setSize(805, 700);
		StartMenu menu = new StartMenu();
		jf.add(menu);
		jf.addKeyListener(menu);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}