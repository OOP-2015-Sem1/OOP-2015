package bricks.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;

import Components.Brick;
import Components.Ball;

public class Mario {

	public Mario() {

	}

	private int score = 0;
	private int[][] flag = new int[800][800];
	private Brick[][] brick = new Brick[800][850];
	File brickHit = new File("brick.wav");
	File winner = new File("winner.wav");

	public void createMario(Graphics2D g, Ball ball) {

		int speed = 8;

		for (int i = 40; i <= 700; i += 60) {
			for (int j = 15; j <= 360; j += 23) {
				if (flag[i][j] == 0) {
					brick[i][j] = new Brick(i, j);
				}

				if (i >= 220 && i <= 460 && j == 15) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}

					if (brick[i][j].getVisibility()) {
						brick[i][j].render(g, Color.red, i, j);
					}

				}

				if (i >= 160 && i <= 640 && j == 38) {

					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						brick[i][j].render(g, Color.red, i, j);
					}

				}
				if (i >= 160 && i <= 520 && j == 61) {

					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if (i <= 280 || (i >= 460 && i < 520)) {
							brick[i][j].render(g, Color.BLACK, i, j);
						} else {
							brick[i][j].render(g, new Color(255, 204, 153), i, j);
						}
					}

				}
				if (i >= 100 && i <= 640 && j == 84) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if (i < 160 || (i >= 220 && i < 280) || (i >= 460 && i < 520)) {
							brick[i][j].render(g, Color.BLACK, i, j);
						} else {
							brick[i][j].render(g, new Color(255, 204, 153), i, j);
						}
					}

				}
				if (i >= 100 && i <= 700 && j == 107) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if (i < 160 || (i >= 220 && i < 340) || (i >= 520 && i < 580)) {
							brick[i][j].render(g, Color.BLACK, i, j);
						} else {
							brick[i][j].render(g, new Color(255, 204, 153), i, j);
						}
					}
				}
				if (i >= 100 && i <= 640 && j == 130) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if (i <= 160 || i >= 460) {
							brick[i][j].render(g, Color.BLACK, i, j);
						} else {
							brick[i][j].render(g, new Color(255, 204, 153), i, j);
						}
					}
				}
				if (i >= 220 && i <= 580 && j == 153) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						brick[i][j].render(g, new Color(255, 204, 153), i, j);
					}

				}
				if (i >= 160 && i <= 460 && j == 176) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if (i >= 280 && i < 340) {
							brick[i][j].render(g, Color.RED, i, j);
						} else {
							brick[i][j].render(g, new Color(63, 27, 182), i, j);
						}
					}
				}
				if (i >= 100 && i <= 640 && j == 199) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if ((i >= 280 && i < 340) || (i >= 460 && i < 520)) {
							brick[i][j].render(g, Color.RED, i, j);
						} else {
							brick[i][j].render(g, new Color(63, 27, 182), i, j);
						}
					}
				}
				if (i >= 40 && i <= 700 && j == 222) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if ((i >= 280 && i < 340) || (i >= 460 && i < 520)) {
							brick[i][j].render(g, Color.RED, i, j);
						} else {
							brick[i][j].render(g, new Color(63, 27, 182), i, j);
						}
					}
				}
				if (i >= 40 && i <= 700 && j == 245) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if (i >= 280 && i < 520) {
							brick[i][j].render(g, Color.RED, i, j);
						} else if ((i >= 40 && i < 160) || (i >= 640 && i < 760)) {
							brick[i][j].render(g, new Color(255, 204, 153), i, j);
						} else {
							brick[i][j].render(g, new Color(63, 27, 182), i, j);
						}
					}
				}
				if (i >= 40 && i <= 700 && j == 268) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if ((i >= 40 && i < 220) || (i >= 580 && i < 760)) {
							brick[i][j].render(g, new Color(255, 204, 153), i, j);
						} else if ((i >= 280 && i < 340) || (i >= 460 && i < 520)) {
							brick[i][j].render(g, new Color(255, 255, 0), i, j);
						} else {
							brick[i][j].render(g, Color.RED, i, j);
						}
					}
				}
				if (i >= 40 && i <= 700 && j == 291) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						if ((i >= 40 && i < 160) || (i >= 640 && i < 760)) {
							brick[i][j].render(g, new Color(255, 204, 153), i, j);
						} else {
							brick[i][j].render(g, Color.RED, i, j);
						}
					}
				}
				if ((i >= 160 && i < 340 && j == 314) || (i >= 520 && i <= 580 && j == 314)) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						brick[i][j].render(g, Color.red, i, j);

					}
				}
				if ((i >= 100 && i < 280 && j == 337) || (i >= 520 && i <= 640 && j == 337)) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						brick[i][j].render(g, new Color(102, 51, 0), i, j);
					}
				}
				if ((i >= 40 && i < 280 && j == 360) || (i >= 520 && i <= 700 && j == 360)) {
					if (flag[i][j] == 0) {
						brick[i][j].setVisiblity(true);
						flag[i][j] = 1;
					}
					if (brick[i][j].getVisibility()) {
						brick[i][j].render(g, new Color(102, 51, 0), i, j);

					}
				}

				if (brick[i][j].getVisibility() && ball.getX() + ball.getSize() >= brick[i][j].getX()
						&& ball.getX() <= brick[i][j].getX() + brick[i][j].getWidth()
						&& ball.getY() <= brick[i][j].getY() + brick[i][j].getHeight()
						&& ball.getY() + ball.getSize() >= brick[i][j].getY()) {

					if (ball.getVx() <= 0 && ball.getVy() <= 0
							&& ball.getX() == brick[i][j].getX() + brick[i][j].getWidth()) {
						ball.setVy(-speed);
						ball.setVx(speed);
					} else if (ball.getVx() <= 0 && ball.getVy() <= 0) {
						ball.setVy(speed);
						ball.setVx(-speed);
					} else if (ball.getVx() >= 0 && ball.getVy() <= 0
							&& ball.getX() + ball.getSize() == brick[i][j].getX()) {
						ball.setVy(-speed);
						ball.setVx(-speed);
					} else if (ball.getVx() >= 0 && ball.getVy() <= 0) {
						ball.setVy(speed);
						ball.setVx(speed);
					} else if (ball.getVx() <= 0 && ball.getVy() >= 0
							&& ball.getX() == brick[i][j].getX() + brick[i][j].getWidth()) {
						ball.setVy(speed);
						ball.setVx(speed);
					} else if (ball.getVx() <= 0 && ball.getVy() >= 0) {
						ball.setVy(-speed);
						ball.setVx(-speed);
					} else if (ball.getVx() >= 0 && ball.getVy() >= 0
							&& ball.getX() + ball.getSize() == brick[i][j].getX()) {
						ball.setVy(speed);
						ball.setVx(-speed);
					} else if (ball.getVx() >= 0 && ball.getVy() >= 0) {
						ball.setVy(-speed);
						ball.setVx(speed);
					}

					brick[i][j].setVisiblity(false);

					setScore(getScore() + 150);
					Sound.PlaySound(brickHit);
					if (getScore() == 21300) {
						Sound.PlaySound(winner);
					}
					ball.setX(ball.getX() + ball.getVx());
					ball.setY(ball.getY() + ball.getVy());

				}

			}
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
