package breakoutGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Level1 extends Level {
	private int lives = 1;
	private int score;

	public Level1(Game game, Ball ball, Brick[][] brick, Paddle paddle) {
		this.game = game;
		this.ball = ball;
		this.brick = brick;
		this.paddle = paddle;
		
	}

	@Override
	public void init() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				switch (j) {
				case 0:
					brick[i][j] = new Brick(Color.RED, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 1:
					brick[i][j] = new Brick(Color.ORANGE, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 2:
					brick[i][j] = new Brick(Color.YELLOW, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 3:
					brick[i][j] = new Brick(Color.CYAN, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 4:
					brick[i][j] = new Brick(Color.GREEN, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;

				}
			}
		}

	}

	@Override
	public void update() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {

				if ((ball.surface.intersects(brick[i][j].surface))) {
					
					Rectangle intersection = ball.surface.intersection(brick[i][j].surface);
					
					if(!brick[i][j].isDestroyed()){
						//hit on left side
						if((ball.ballX + (ball.ballDiameter/2)) < (intersection.x +(intersection.width/2))){
							ball.ballXmove = -ball.ballSpeed;
						}
						//hit on right side
						if((ball.ballX + (ball.ballDiameter/2)) > (intersection.x + (intersection.width/2))){
							ball.ballXmove =ball.ballSpeed;
						}
						// hit top
						if((ball.ballY + (ball.ballDiameter/2) < (intersection.y + intersection.height/2))){
							ball.ballYmove = - ball.ballSpeed;
						}
						//hit on bottom
						if((ball.ballY + (ball.ballDiameter/2) > (intersection.y + intersection.height/2))){
							ball.ballYmove = ball.ballSpeed;
						}

						brick[i][j].setIsDestroyed(true);
						score++;

					}
				}
			}
		}

		if (score == Game.TOTAL_SCORE_LEVEL1) {
			levelIsComplete(true);
		}
	}

	@Override
	public void paintComponents(Graphics g) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				if (!brick[i][j].isDestroyed())
					brick[i][j].paintComponent(g);
			}
		}

	}

	public int getScore() {
		return score;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void levelIsComplete(boolean var) {
		isComplete = var;
	}

	@Override
	public void setComponentsSpeed() {
		ball.setBallSpeed(5);
	}

	@Override
	public void resetComponentsPosition() {
		ball.restartPosition();
	}

	@Override
	public int getLives() {
		
		return lives;
	}

	@Override
	public void setLives(int number) {
		lives = number;
		
	}

	@Override
	public void resetScore() {
		score = 0;
	}
	
	

}
