package gui;

import java.awt.Color;
import java.awt.Graphics;

import entities.Constants;

public class DrawGameGraphics {

	private void clearDrawing(Graphics g) {
		g.clearRect(0, 0, Constants.DIMENSION, Constants.DIMENSION);
	}

	private void fillDrawing(Graphics g) {
		g.fillRect(0, 0, Constants.DIMENSION, Constants.DIMENSION);
	}

	public void drawAtWin(Graphics g) {
		clearDrawing(g);
		g.setColor(new Color(10, 50, 0));
		fillDrawing(g);
		g.setColor(Color.YELLOW);
		g.drawString("YOU WON!", 350, 400);
	}

	public void drawAtLoss(Graphics g) {
		clearDrawing(g);
		g.setColor(Color.BLACK);
		fillDrawing(g);
		g.setColor(Color.WHITE);
		g.drawString("GAME  OVER", 350, 400);
	}

	
}
