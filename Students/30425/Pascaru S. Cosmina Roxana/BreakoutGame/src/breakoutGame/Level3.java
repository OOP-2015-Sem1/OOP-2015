package breakoutGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Level3 extends Level {
	private int lives = 3;
	private int score;

	public Level3(Game game, Ball ball, Brick[][] brick, Paddle paddle) {
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
					brick[i][j] = new Brick(Color.BLUE, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 1:
					brick[i][j] = new Brick(Color.CYAN, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 2:
					brick[i][j] = new Brick(Color.GREEN, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 3:
					brick[i][j] = new Brick(Color.YELLOW, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 4:
					brick[i][j] = new Brick(Color.MAGENTA, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;

				}
			}
		}

		brick[0][0].brickColor = Color.GRAY;
		brick[2][0].brickColor = Color.GRAY;
		brick[4][0].brickColor = Color.GRAY;
		brick[6][0].brickColor = Color.GRAY;
		brick[8][0].brickColor = Color.GRAY;

		brick[1][1].brickColor = Color.GRAY;
		brick[3][1].brickColor = Color.GRAY;
		brick[5][1].brickColor = Color.GRAY;
		brick[7][1].brickColor = Color.GRAY;

		brick[0][2].brickColor = Color.GRAY;
		brick[2][2].brickColor = Color.GRAY;
		brick[4][2].brickColor = Color.GRAY;
		brick[6][2].brickColor = Color.GRAY;
		brick[8][2].brickColor = Color.GRAY;

		brick[1][3].brickColor = Color.GRAY;
		brick[3][3].brickColor = Color.GRAY;
		brick[5][3].brickColor = Color.GRAY;
		brick[7][3].brickColor = Color.GRAY;

		brick[0][4].brickColor = Color.GRAY;
		brick[2][4].brickColor = Color.GRAY;
		brick[4][4].brickColor = Color.GRAY;
		brick[6][4].brickColor = Color.GRAY;
		brick[8][4].brickColor = Color.GRAY;

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
						
						if (brick[i][j].brickColor == Color.MAGENTA) {
							score++;
							brick[i][j].setIsDestroyed(true);
						}
						if (brick[i][j].brickColor == Color.BLUE) {
							brick[i][j].changeColor(Color.MAGENTA);
						}
						if (brick[i][j].brickColor == Color.CYAN) {
							brick[i][j].changeColor(Color.BLUE);
						}
						if (brick[i][j].brickColor == Color.GREEN) {
							brick[i][j].changeColor(Color.CYAN);
						}
						if (brick[i][j].brickColor == Color.YELLOW) {
							brick[i][j].changeColor(Color.GREEN);
						}
						if (brick[i][j].brickColor == Color.GRAY && brick[i][j].nrOfHits < 3) {
							brick[i][j].nrOfHits++;
						}
						if (brick[i][j].brickColor == Color.GRAY && brick[i][j].nrOfHits == 3) {
							brick[i][j].changeColor(Color.YELLOW);
						}
					}
				}
			}
		}
		if (score == Game.TOTAL_SCORE_LEVEL3) {
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
		ball.setBallSpeed(7);
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
