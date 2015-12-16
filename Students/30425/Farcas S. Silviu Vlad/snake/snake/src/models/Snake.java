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

public class Snake {

	private static Snake snake;

	private JFrame jframe;

	private RenderPanel renderPanel;

	private SnakeTimer snakeTimer;

	private ArrayList<Point> snakeParts = new ArrayList<Point>();

	private ArrayList<Point> obstacles = new ArrayList<Point>();

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;

	public int direction = DOWN, score, tailLength = 10, time;

	private Point head, cherry;

	public Random random;

	private boolean over = false, paused;

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
		setRenderPanel(new RenderPanel());
		jframe.add(renderPanel);

	}

	public void startGame() {
		over = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 14;
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
		jframe.addKeyListener(new SnakeKey());
		snakeTimer = new SnakeTimer();
		snakeTimer.timer.start();
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

	public RenderPanel getRenderPanel() {
		return renderPanel;
	}

	public void setRenderPanel(RenderPanel renderPanel) {
		this.renderPanel = renderPanel;
	}

	public static Snake getSnake() {
		return snake;
	}

	public static void setSnake(Snake snake) {
		Snake.snake = snake;
	}

	public ArrayList<Point> getSnakeParts() {
		return snakeParts;
	}

	public void addSnakeParts(Point part) {
		snakeParts.add(part);
	}

	public void removeSnakeParts(int index){
		snakeParts.remove(index);
	}
	
	public ArrayList<Point> getObstacles() {
		return obstacles;
	}

	public void addObstacles(Point part) {
		obstacles.add(part);
	}

	public Point getHead() {
		return head;
	}

	public void setHead(Point head) {
		this.head = head;
	}

	public Point getCherry() {
		return cherry;
	}

	public void setCherry(Point cherry) {
		this.cherry = cherry;
	}

	public boolean getOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public boolean getPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void repaintRenderPanel(){
		renderPanel.repaint();
	}
	
	public JFrame getJframe(){
		return jframe;
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