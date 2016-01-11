package bricks.game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Mario {

	public Mario() {

	}

	public static Brick[][] brick = new Brick[800][850];

	public static void createMario(Graphics2D g) {

		for (int i = 220; i <= 460; i += 60) {
			int j = 15;
			brick[i][j] = new Brick(i, j);
			brick[i][j].render(g, Color.red, i, j);
			
			
		}

		for (int i = 160; i <= 640; i += 60) {
			int j = 38;
			brick[i][j] = new Brick(i, j);
			brick[i][j].render(g, Color.red, i, j);
		}

		for (int i = 160; i <= 520; i += 60) {
			int j = 61;
			brick[i][j] = new Brick(i, j);
			if (i <= 280 || (i >= 460 && i < 520)) {
				brick[i][j].render(g, Color.BLACK, i, j);
			} else {
				brick[i][j].render(g, new Color(255, 204, 153), i, j);
			}

		}
		for (int i = 100; i <= 640; i += 60) {
			int j = 84;
			brick[i][j] = new Brick(i, j);
			if (i < 160 || (i >= 220 && i < 280) || (i >= 460 && i < 520)) {
				brick[i][j].render(g, Color.BLACK, i, j);
			} else {
				brick[i][j].render(g, new Color(255, 204, 153), i, j);
			}

		}

		for (int i = 100; i <= 700; i += 60) {
			int j = 107;
			brick[i][j] = new Brick(i, j);
			if (i < 160 || (i >= 220 && i < 340) || (i >= 520 && i < 580)) {
				brick[i][j].render(g, Color.BLACK, i, j);
			} else {
				brick[i][j].render(g, new Color(255, 204, 153), i, j);
			}

		}

		for (int i = 100; i <= 640; i += 60) {
			int j = 130;
			brick[i][j] = new Brick(i, j);
			if (i <= 160 || i >= 460) {
				brick[i][j].render(g, Color.BLACK, i, j);
			} else {
				brick[i][j].render(g, new Color(255, 204, 153), i, j);
			}

		}
		for (int i = 220; i <= 580; i += 60) {
			int j = 153;
			brick[i][j] = new Brick(i, j);
			brick[i][j].render(g, new Color(255, 204, 153), i, j);
		}

		for (int i = 160; i <= 460; i += 60) {
			int j = 176;
			brick[i][j] = new Brick(i, j);
			if (i >= 280 && i < 340) {
				brick[i][j].render(g, Color.RED, i, j);
			} else {
				brick[i][j].render(g, new Color(63, 27, 182), i, j);
			}
		}
		for (int i = 100; i <= 640; i += 60) {
			int j = 199;
			brick[i][j] = new Brick(i, j);
			if ((i >= 280 && i < 340) || (i >= 460 && i < 520)) {
				brick[i][j].render(g, Color.RED, i, j);
			} else {
				brick[i][j].render(g, new Color(63, 27, 182), i, j);
			}
		}
		for (int i = 40; i <= 700; i += 60) {
			int j = 222;
			brick[i][j] = new Brick(i, j);
			if ((i >= 280 && i < 340) || (i >= 460 && i < 520)) {
				brick[i][j].render(g, Color.RED, i, j);
			} else {
				brick[i][j].render(g, new Color(63, 27, 182), i, j);
			}
		}
		for (int i = 40; i <= 700; i += 60) {
			int j = 245;
			brick[i][j] = new Brick(i, j);
			if (i >= 280 && i < 520) {
				brick[i][j].render(g, Color.RED, i, j);
			} else if ((i >= 40 && i < 160) || (i >= 640 && i < 760)) {
				brick[i][j].render(g, new Color(255, 204, 153), i, j);
			} else {
				brick[i][j].render(g, new Color(63, 27, 182), i, j);
			}
		}
		for (int i = 40; i <= 700; i += 60) {
			int j = 268;
			brick[i][j] = new Brick(i, j);
			if ((i >= 40 && i < 220) || (i >= 580 && i < 760)) {
				brick[i][j].render(g, new Color(255, 204, 153), i, j);
			} else if ((i >= 280 && i < 340) || (i >= 460 && i < 520)) {
				brick[i][j].render(g, new Color(255, 255, 0), i, j);
			} else {
				brick[i][j].render(g, Color.RED, i, j);
			}
		}
		for (int i = 40; i <= 700; i += 60) {
			int j = 291;
			brick[i][j] = new Brick(i, j);
			if ((i >= 40 && i < 160) || (i >= 640 && i < 760)) {
				brick[i][j].render(g, new Color(255, 204, 153), i, j);
			} else {
				brick[i][j].render(g, Color.RED, i, j);
			}
		}
		for (int i = 160; i <= 580; i += 60) {
			int j = 314;
			if ((i >= 160 && i < 340) || (i >= 520 && i <= 580)) {
				brick[i][j] = new Brick(i, j);
				brick[i][j].render(g, Color.red, i, j);
			}
		}
		for (int i = 100; i <= 640; i += 60) {
			int j = 337;
			if ((i >= 100 && i < 280) || (i >= 520 && i <= 640)) {
				brick[i][j] = new Brick(i, j);
				brick[i][j].render(g, new Color(102, 51, 0), i, j);
			}
		}
		for (int i = 40; i <= 700; i += 60) {
			int j = 360;
			if ((i >= 40 && i < 280) || (i >= 520 && i <= 700)) {
				brick[i][j] = new Brick(i, j);
				brick[i][j].render(g, new Color(102, 51, 0), i, j);
				
				
			}
		}

	}

}
