package breakoutGame;

import java.awt.Graphics;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public abstract class Levels {

	// Entities
	public Paddle paddle;
	public Ball ball;
	public Brick[][] brick = new Brick[9][5];
	public Game game;

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
}
