package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

	public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
	public Rectangle exitButton = new Rectangle(Game.WIDTH / 2 + 120, 300, 100, 50);

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("SPACE GAME", Game.WIDTH / 2, 100);

		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.drawString("Play", playButton.x + 19, playButton.y + 31);
		g.drawString("Exit", exitButton.x + 19, exitButton.y + 31);

		g2d.draw(playButton);
		g2d.draw(exitButton);

	}

}
