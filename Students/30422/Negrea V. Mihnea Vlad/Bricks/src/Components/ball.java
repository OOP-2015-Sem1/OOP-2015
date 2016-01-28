package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import bricks.game.Game;
import bricks.game.Sound;

public class ball {

	public int x;
	public int y;
	int size = 14;
	int speed = 8;
	public int lives = 3;

	public int vx;
	public int vy;

	public static File paddleHit = new File("paddle.wav");
	public static File life = new File("life.wav");
	public static File gameover = new File("gameover.wav");

	public ball(int x, int y) {
		this.x = x;
		this.y = y;

		vx = 0;
		vy = speed;

	}

	public void move(Game game, Paddle paddle) {

		if (x <= 0 && y <= 40) {
			vx = speed + 4;
		} else if (x <= 0) {
			vx = speed;
		} else if (x + size >= game.width && y <= 40) {
			vx = -speed - 4;
		} else if (x + size >= game.width) {
			vx = -speed;
		}

		if (y <= 0) {
			vy = speed;
		} else if (y + size >= game.height) {
			vy = -speed;
		}

		if (CheckCollision(paddle) == 1) {
			Sound.PlaySound(paddleHit);
			if (vx <= 0 && x + size / 2 >= paddle.x + 60 && x + size / 2 <= paddle.x + 75) { // this
																								// instructions
																								// set
																								// the
																								// angle
																								// of
																								// the
																								// ball
																								// on
																								// different
																								// hitted
																								// paddle
																								// areas
				vy = -speed; // and when the ball is coming i.e. the right side
								// and you hit it with the right side of the
								// paddle
				vx = speed - 3; // the ball goes to the right side agains
			} else if (vx <= 0 && x + size / 2 > paddle.x + 75 && x + size / 2 <= paddle.x + 90) {
				vy = -speed;
				vx = speed - 1;
			} else if (vx <= 0 && x + size / 2 > paddle.x + 90 && x + size / 2 <= paddle.x + 105) {
				vy = -speed;
				vx = speed + 1;
			} else if (vx <= 0 && x + size / 2 > paddle.x + 105 && x + size / 2 <= paddle.x + 120) {
				vy = -speed;
				vx = speed + 3;
			} else if (vx >= 0 && x + size / 2 >= paddle.x && x + size / 2 <= paddle.x + 15) {
				vy = -speed;
				vx = -speed - 3;

			} else if (vx >= 0 && x + size / 2 > paddle.x + 15 && x + size / 2 <= paddle.x + 30) {
				vy = -speed;
				vx = -speed - 1;

			} else if (vx >= 0 && x + size / 2 > paddle.x + 30 && x + size / 2 <= paddle.x + 45) {
				vy = -speed;
				vx = -speed + 1;

			} else if (vx >= 0 && x + size / 2 > paddle.x + 45 && x + size / 2 < paddle.x + 60) {
				vy = -speed;
				;
				vx = -speed + 3;

			} else if (vx >= 0 && x + size / 2 >= paddle.x + 60 && x + size / 2 <= paddle.x + 75) {
				vy = -speed;
				vx = speed - 3;
			} else if (vx >= 0 && x + size / 2 > paddle.x + 75 && x + size / 2 <= paddle.x + 90) {
				vy = -speed;
				vx = speed - 1;
			} else if (vx >= 0 && x + size / 2 > paddle.x + 90 && x + size / 2 <= paddle.x + 105) {
				vy = -speed;
				vx = speed + 1;
			} else if (vx >= 0 && x + size / 2 > paddle.x + 105 && x + size / 2 <= paddle.x + 120) {
				vy = -speed;
				vx = speed + 3;
			} else if (vx <= 0 && x + size / 2 >= paddle.x && x + size / 2 <= paddle.x + 15) {
				vy = -speed;
				vx = -speed - 3;

			} else if (vx <= 0 && x + size / 2 > paddle.x + 15 && x + size / 2 <= paddle.x + 30) {
				vy = -speed;
				vx = -speed - 1;

			} else if (vx <= 0 && x + size / 2 > paddle.x + 30 && x + size / 2 <= paddle.x + 45) {
				vy = -speed;
				vx = -speed + 1;

			} else if (vx <= 0 && x + size / 2 > paddle.x + 45 && x + size / 2 < paddle.x + 60) {
				vy = -speed;
				vx = -speed + 3;

			}

		}
		x += vx;
		y += vy;

		if (CheckCollision(paddle) == 2) {
			lives--;

			this.x = 400;
			this.y = 500;
			this.vx = 0;
			this.vy = speed;

			paddle.x = (game.width / 2) - 60;
			paddle.y = game.height - 40;

		}

	}

	public int CheckCollision(Paddle paddle) {

		if ((y + size >= paddle.y) && (x + size >= paddle.x) && (x <= paddle.x + paddle.width)) {

			return 1;
		} else if (paddle.y + 20 < y) {
			if (lives > 1) {
				Sound.PlaySound(life);
			} else {
				Sound.PlaySound(gameover);
			}
			return 2;

		}
		return 0;

	}

	public void render(Graphics g) {
		g.setColor(new Color(51, 25, 0));
		g.fillOval(x, y, size, size);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
