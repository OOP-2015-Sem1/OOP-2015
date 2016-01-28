package Components;

import java.awt.Color;
import java.awt.Graphics;

import bricks.game.Game;

public class Paddle {

	 int x, y, width = 120, height = 18;
	
	public Game game;

	public Paddle(Game game) {
		this.x = (game.width / 2) - 60;
		this.y = game.height - 40;
		this.game = game;
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
			if (x + width + speed < game.width) {
				x += speed;
			} else {
				x = game.width - width;
			}

		}
	}

}
