package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import breakoutGame.Game;

public class Ball extends Entity {

	// ball details
	public int ballX = 200;
	public int ballY = 200;
	public int ballDiameter = 16;
	public int ballSpeed = 5;
	public int ballXmove = ballSpeed;
	public int ballYmove = ballSpeed;
	
	//Game
	private Game game;

	// constructor
	public Ball(Game game) {
		this.game = game;
		entityCollider = new Rectangle(ballX, ballY, ballDiameter, ballDiameter);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
	}

	@Override
	public void update() {
		ballX += ballXmove;
		ballY += ballYmove;
		entityCollider.setLocation(ballX, ballY);
	}

}
