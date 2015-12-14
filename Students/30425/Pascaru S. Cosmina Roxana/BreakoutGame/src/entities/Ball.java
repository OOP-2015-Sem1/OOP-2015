package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import breakoutGame.Game;

public class Ball extends Entity {
	
	//ball details
	private int ballX = 200 , ballY = 200;
	private int ballDiameter = 16;
	private int ballSpeed = 5;
	private int ballXmove= ballSpeed, ballYmove = ballSpeed;
	
	//constructor
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
		
		if(entityCollider.intersects(game.paddle.entityCollider))
			ballYmove = -ballSpeed;
		
		for(int i=0;i<9;i++){
			for(int j=0;j<5;j++){
				if(entityCollider.intersects(game.brick[i][j].entityCollider) && !game.brick[i][j].isHit()){
					ballYmove = ballSpeed;
					game.brick[i][j].brickColor=Color.BLACK;
					game.brick[i][j].wasHit(true);
					game.score++;

					break;
				}
			}
		}
		
		if(game.score == Game.TOTAL_SCORE)
			game.youWon();
		
		if(ballY <= 0)
			ballYmove = ballSpeed;
		
		if(ballX <= 0)
			ballXmove = ballSpeed;
		
		if(ballX +  ballDiameter >= game.canvas.getWidth())
			ballXmove = -ballSpeed;
		
		if(ballY + ballDiameter >= game.canvas.getHeight())
			game.youLost();
		
		entityCollider.setLocation(ballX, ballY);
	}

}
