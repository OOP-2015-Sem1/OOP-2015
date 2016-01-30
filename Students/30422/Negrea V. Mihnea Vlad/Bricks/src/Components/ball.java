package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import bricks.game.Game;
import bricks.game.Sound;

public class Ball {

	 int x;
	 int y;
	int size = 14;
	int speed = 8;
	 int lives = 3;

	int vx;

	int vy;

	File paddleHit = new File("paddle.wav");
	File life = new File("life.wav");
	File gameover = new File("gameover.wav");

	public Ball(int x, int y) {
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
			if (vx <= 0 && x + size / 2 >= paddle.x + 60 && x + size / 2 <= paddle.x + 75) {
				vy = -speed;
				vx = speed - 3;
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
	
	 public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}


}
