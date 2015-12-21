package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import breakoutGame.Game;

public class Paddle extends Entity {
	
	
	//paddle details
	private int paddleX = 450, paddleY = 500;
	private int paddleWidth =90, paddleHeight =13;
	private int paddleSpeed =10 ;
	private int paddleXmove, paddleYmove;
	private int distanceToMargin =20;
	
	//Game
	private Game game;

	//constructor
	public Paddle(Game game) {
		this.game = game;
		entityCollider = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
	}

	@Override
	public void update() {
		if (game.keys.left)
			paddleX -= paddleSpeed;
		
		if (game.keys.right)
			paddleX += paddleSpeed;
		
		if(paddleX<=0)
			paddleX=paddleSpeed;
		
		if(paddleX +paddleWidth + distanceToMargin >= game.WINDOW_WIDTH)
			paddleX = game.WINDOW_WIDTH-paddleWidth -distanceToMargin;
		
		entityCollider.setLocation(paddleX, paddleY);
	}
}
