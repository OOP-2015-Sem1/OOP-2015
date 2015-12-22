package breakoutGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import entities.Ball;
import entities.Brick;
import entities.Paddle;

public class Level3 extends Levels {

	private int scoreLevel3 = 0;
	
	public Level3 (Game game, Ball ball, Brick[][] brick, Paddle paddle){
		this.game = game;
		this.ball = ball;
		this.brick = brick;
		this.paddle= paddle;
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
					brick[i][j] = new Brick( Color.GREEN, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 3:
					brick[i][j] = new Brick( Color.YELLOW, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;
				case 4:
					brick[i][j] = new Brick(Color.MAGENTA, brickX + i * brickWidth + i * brickSpace,
							brickY + j * brickHeight + j * brickSpace);
					break;

				}
			}
		}
		
		brick[0][0].brickColor= Color.GRAY;
		brick[2][0].brickColor = Color.GRAY;
		brick[4][0].brickColor =Color.GRAY;
		brick[6][0].brickColor = Color.GRAY;
		brick[8][0].brickColor = Color.GRAY;
		
		brick[1][1].brickColor= Color.GRAY;
		brick[3][1].brickColor= Color.GRAY;
		brick[5][1].brickColor= Color.GRAY;
		brick[7][1].brickColor= Color.GRAY;
		
		brick[0][2].brickColor= Color.GRAY;
		brick[2][2].brickColor = Color.GRAY;
		brick[4][2].brickColor =Color.GRAY;
		brick[6][2].brickColor = Color.GRAY;
		brick[8][2].brickColor = Color.GRAY;
		
		brick[1][3].brickColor= Color.GRAY;
		brick[3][3].brickColor= Color.GRAY;
		brick[5][3].brickColor= Color.GRAY;
		brick[7][3].brickColor= Color.GRAY;
		
		brick[0][4].brickColor= Color.GRAY;
		brick[2][4].brickColor = Color.GRAY;
		brick[4][4].brickColor =Color.GRAY;
		brick[6][4].brickColor = Color.GRAY;
		brick[8][4].brickColor = Color.GRAY;
		
		
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

					Point pointRight = new Point(ballLeft + ballWidth , ballTop);
					Point pointLeft = new Point(ballLeft , ballTop);
					Point pointTop = new Point(ballLeft, ballTop );
					Point pointBottom = new Point(ballLeft, ballTop + ballHeight );

					if (!brick[i][j].isDestroyed()) {
						if (brick[i][j].entityCollider.contains(pointRight)) {
							ball.ballXmove = - ball.ballSpeed;
						} else if (brick[i][j].entityCollider.contains(pointLeft)) {
							ball.ballXmove = ball.ballSpeed;
						}

						if (brick[i][j].entityCollider.contains(pointTop)) {
							ball.ballYmove = ball.ballSpeed;
						} else if (brick[i][j].entityCollider.contains(pointBottom)) {
							ball.ballYmove = - ball.ballSpeed;
						}
						
						
						
						if(brick[i][j].brickColor == Color.MAGENTA){
							scoreLevel3++;
							brick[i][j].setIsDestroyed(true);
						}
						if(brick[i][j].brickColor == Color.BLUE){
							brick[i][j].changeColor(Color.MAGENTA);
						}
						if(brick[i][j].brickColor == Color.CYAN){
							brick[i][j].changeColor(Color.BLUE);
						}
						if(brick[i][j].brickColor == Color.GREEN){
							brick[i][j].changeColor(Color.CYAN);
						}
						if(brick[i][j].brickColor == Color.YELLOW){
							brick[i][j].changeColor(Color.GREEN);
						}
						if(brick[i][j].brickColor == Color.GRAY && brick[i][j].nrOfHits <3){
							brick[i][j].nrOfHits++;
						}
						if(brick[i][j].brickColor == Color.GRAY && brick[i][j].nrOfHits == 3){
							brick[i][j].changeColor(Color.YELLOW);
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
				if(!brick[i][j].isDestroyed())
				brick[i][j].paintComponent(g);
			}
		}	
	}

	@Override
	public int getScore() {
		return scoreLevel3;
	}

}
