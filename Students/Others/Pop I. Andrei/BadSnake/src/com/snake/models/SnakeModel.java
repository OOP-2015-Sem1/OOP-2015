package com.snake.models;

import java.awt.Point;
import java.util.ArrayList;

public abstract class SnakeModel {
	
	protected String currDir;
	protected ArrayList<Point> snakePoints;
	protected int len;
	protected boolean canGoThroughWalls;

	protected Point nextPositionOfHead = new Point();
	
	abstract public boolean move(String direction, Apple currentApple);
	abstract public boolean headIsEatingApple(Apple currentApple);
	abstract public boolean headIsEatingApple(Apple currentApple, Point newHead);
	abstract protected void recalculatePositions(Point newHead);

}
