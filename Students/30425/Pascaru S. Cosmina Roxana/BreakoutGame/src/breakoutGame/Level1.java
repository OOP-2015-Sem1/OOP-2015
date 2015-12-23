package breakoutGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Level1 extends Level {

	private int scoreLevel1 = 0;
	private boolean isComplete = false;

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

				if ((ball.entityCollider.intersects(brick[i][j].entityCollider))) {
					int ballLeft = (int) ball.entityCollider.getMinX();
					int ballHeight = (int) ball.entityCollider.getHeight();
					int ballWidth = (int) ball.entityCollider.getWidth();
					int ballTop = (int) ball.entityCollider.getMinY();

					Point pointRight = new Point(ballLeft + ballWidth + 2, ballTop);
					Point pointLeft = new Point(ballLeft, ballTop + 2);
					Point pointTop = new Point(ballLeft, ballTop);
					Point pointBottom = new Point(ballLeft + 2, ballTop + ballHeight);

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
						brick[i][j].setIsDestroyed(true);
						scoreLevel1++;

					}
				}
			}
		}

		if (scoreLevel1 == Game.TOTAL_SCORE_LEVEL1) {
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
		return scoreLevel1;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void levelIsComplete(boolean var) {
		isComplete = var;
	}

}
