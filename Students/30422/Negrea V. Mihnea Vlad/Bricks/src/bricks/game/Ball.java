package bricks.game;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	int x, y;
	int size = 14;
	int speed = 8;

	int vx, vy;
	
	public static Brick[][] brick = new Brick[800][850];

	public Ball(int x, int y) {
		this.x = x;
		this.y = y;

		vx = speed;
		vy = speed;
	}

	public void move(Game game, Paddle paddle) {
		if (x <= 0) {
			vx = speed;
		} else if (x + size >= game.width) {
			vx = -speed;
		}

		if (y <= 0) {
			vy = speed;
		} else if (y + size >= game.height) {
			vy = -speed;
		}

		if (CheckCollision(paddle) == 1) {
			if (vx <= 0 && x >= paddle.x + 60 && x <= paddle.x + 75) {
				vy = -speed + 3;
				vx = speed - 3;
			}
			if (vx <= 0 && x >= paddle.x + 60 && x <= paddle.x + 90) {
				vy = -speed + 1;
				vx = speed - 1;
			}
			if (vx <= 0 && x >= paddle.x + 60 && x <= paddle.x + 105) {
				vy = -speed - 1;
				vx = speed + 1;
			}
			if (vx <= 0 && x >= paddle.x + 60 && x <= paddle.x + 120) {
				vy = -speed - 3;
				vx = speed + 3;
			} else if (vx >= 0 && x >= paddle.x && x <= paddle.x + 15) {
				vy = speed + 3;
				vx = -speed - 3;

			} else if (vx >= 0 && x >= paddle.x && x <= paddle.x + 30) {
				vy = speed + 1;
				vx = -speed - 1;

			} else if (vx >= 0 && x >= paddle.x && x <= paddle.x + 45) {
				vy = speed - 1;
				vx = -speed + 1;

			} else if (vx >= 0 && x >= paddle.x && x <= paddle.x + 60) {
				vy = speed - 3;
				vx = -speed + 3;

			} else {
				vy = -speed;
			}
		}
		x += vx;
		y += vy;
		
		
		
		

	}

	public int CheckCollision(Paddle paddle) {

		if ((y + size >= paddle.y) && (x + size >= paddle.x) && (x <= paddle.x + paddle.width)) {

			return 1;
			// sound.PlaySound(wallHit);

		}

		return 0;
	}

	public void render(Graphics g) {
		g.setColor(new Color(51, 25, 0));
		g.fillOval(x, y, size, size);

	}

}
