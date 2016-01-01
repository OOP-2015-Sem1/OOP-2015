package breakoutGame;

import java.awt.Graphics;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public abstract class Level {

	// Entities
	protected Paddle paddle;
	protected Ball ball;
	protected Brick[][] brick = new Brick[9][5];
	protected Game game;

	
	protected int score;
	protected boolean isComplete;
	
	// Brick details
	int brickWidth = 60;
	int brickX = 100;
	int brickY = 50;
	int brickSpace = 2;
	int brickHeight = 16;

	// Inputs
	public KeyboardHandler keys;

	public abstract void init();

	public abstract void update();

	public abstract void paintComponents(Graphics g);

	public abstract int getScore();
	
	public abstract void levelIsComplete(boolean var);
	
	public abstract boolean isComplete();
	
	public abstract void setComponentsSpeed();
	
	public abstract void resetComponentsPosition();
	
	public abstract int getLives();
	
	public abstract void setLives(int lives);
	
	public abstract void resetScore();
}
