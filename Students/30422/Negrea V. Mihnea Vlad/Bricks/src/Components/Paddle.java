package Components;

import java.awt.Color;
import java.awt.Graphics;

import bricks.game.Game;

public class Paddle {

	public int x, y, width = 120, height = 18;

	public Paddle(Game game) {
		this.x = (game.width / 2) - 60;
		this.y = game.height - 40;

	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRoundRect(x, y, width, height, 12, 12);

	}

	public void move(boolean key) {
		int speed = 10;
		if (key) {
			if (x - speed > 0) {
				x -= speed;
			} else {
				x = 0;
			}
		} else {
			if (x + width + speed < Game.game.width) {
				x += speed;
			} else {
				x = Game.game.width - width;
			}

		}
	}

}
