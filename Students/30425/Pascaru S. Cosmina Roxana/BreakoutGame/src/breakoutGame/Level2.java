package breakoutGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Level2 extends Levels {
	private int scoreLevel2 = 0;

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

				if ((ball.entityCollider.intersects(brick[i][j].entityCollider))) {

					int ballLeft = (int) ball.entityCollider.getMinX();
					int ballHeight = (int) ball.entityCollider.getHeight();
					int ballWidth = (int) ball.entityCollider.getWidth();
					int ballTop = (int) ball.entityCollider.getMinY();

					Point pointRight = new Point(ballLeft + ballWidth, ballTop);
					Point pointLeft = new Point(ballLeft, ballTop);
					Point pointTop = new Point(ballLeft, ballTop);
					Point pointBottom = new Point(ballLeft, ballTop + ballHeight);

					if (!brick[i][j].isDestroyed()) {
						if (brick[i][j].entityCollider.contains(pointRight)) {
							ball.ballXmove = -ball.ballSpeed;
						} else if (brick[i][j].entityCollider.contains(pointLeft)) {
							ball.ballXmove = ball.ballSpeed;
						}

						if (brick[i][j].entityCollider.contains(pointTop)) {
							ball.ballYmove = ball.ballSpeed;
						} else if (brick[i][j].entityCollider.contains(pointBottom)) {
							ball.ballYmove = -ball.ballSpeed;
						}

						if (brick[i][j].brickColor == Color.WHITE) {
							scoreLevel2++;
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
		return scoreLevel2;
	}

}
