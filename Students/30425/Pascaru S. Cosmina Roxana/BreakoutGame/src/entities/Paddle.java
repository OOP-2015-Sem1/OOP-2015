package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import breakoutGame.Game;

public class Paddle extends Entity {

	// paddle details
	private int paddleX = 450, paddleY = 500;
	private int paddleWidth = 90, paddleHeight = 13;
	public int paddleSpeed = 18;
	private int distanceToMargin = 20;

	// Game
	private Game game;

	// constructor
	public Paddle(Game game) {
		this.game = game;
		surface = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
	}

	@Override
	public void update() {
		if (game.keys.direction == "left")
			paddleX -= paddleSpeed;

		if (game.keys.direction == "right")
			paddleX += paddleSpeed;

		if (paddleX <= 0)
			paddleX = paddleSpeed;

		if (paddleX + paddleWidth + distanceToMargin >= Game.WINDOW_WIDTH)
			paddleX = Game.WINDOW_WIDTH - paddleWidth - distanceToMargin;

		surface.setLocation(paddleX, paddleY);
	}

	public void setPaddleSpeed(int speed) {
		paddleSpeed = speed;
	}
	
	public void restartPosition(){
		paddleX = 450;
		paddleY = 500;
	}
}
