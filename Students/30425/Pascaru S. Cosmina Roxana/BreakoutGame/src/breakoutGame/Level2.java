package breakoutGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Level2 extends Level {
	private int lives = 2;
	private int score;

	public Level2(Game game, Ball ball, Brick[][] brick, Paddle paddle) {
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
					brick[i][j] = new Brick(Color.WHITE, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 1:
					brick[i][j] = new Brick(Color.LIGHT_GRAY, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 2:
					brick[i][j] = new Brick(Color.GRAY, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 3:
					brick[i][j] = new Brick(Color.DARK_GRAY, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 4:
					brick[i][j] = new Brick(Color.WHITE, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;

				}
			}
		}
		brick[0][1].setIsDestroyed(true);
		brick[0][2].setIsDestroyed(true);
		brick[1][2].setIsDestroyed(true);
		brick[0][3].setIsDestroyed(true);
		brick[8][1].setIsDestroyed(true);
		brick[7][2].setIsDestroyed(true);
		brick[8][2].setIsDestroyed(true);
		brick[8][3].setIsDestroyed(true);

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
						
						if (brick[i][j].brickColor == Color.WHITE) {
							score++;
							brick[i][j].setIsDestroyed(true);
						}
						if (brick[i][j].brickColor == Color.LIGHT_GRAY) {
							brick[i][j].changeColor(Color.WHITE);
						}
						if (brick[i][j].brickColor == Color.GRAY) {
							brick[i][j].changeColor(Color.LIGHT_GRAY);
						}
						if (brick[i][j].brickColor == Color.DARK_GRAY) {
							brick[i][j].changeColor(Color.GRAY);
						}
					}
				}
			}
		}
		if (score == Game.TOTAL_SCORE_LEVEL2) {
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

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void levelIsComplete(boolean var) {
		isComplete = var;

	}

	@Override
	public boolean isComplete() {
		return isComplete;
	}

	@Override
	public void setComponentsSpeed() {
		ball.setBallSpeed(6);
	}

	@Override
	public void resetComponentsPosition() {
		ball.restartPosition();
		paddle.restartPosition();
	}
	
	@Override
	public int getLives() {
		return lives;
	}
	
	@Override
	public void setLives(int life) {
		lives = life;
	}
	
	@Override
	public void resetScore(){
		score = 0;
	}

}
