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

	private RenderPanel renderPanel;

	private ArrayList<Point> snakeParts = new ArrayList<Point>();

	private ArrayList<Point> obstacles = new ArrayList<Point>();

	public int tailLength = 14;

	private Point head, cherry;

	public Snake() {
		setRenderPanel(new RenderPanel());
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
	
	public void setObstacles(ArrayList<Point> obstacles){
		this.obstacles=obstacles;
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

	public void repaintRenderPanel(){
		renderPanel.repaint();
	}
	

}