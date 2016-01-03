package gui;

import java.awt.Color;
import java.awt.Graphics;

import entities.Constants;

public class PaintSnakeBoard {

	public void drawPanel(Graphics g) {
		g.clearRect(0, 0, Constants.DIMENSION, Constants.DIMENSION);
		g.setColor(new Color(10, 80, 0));
		g.fillRect(0, 0, Constants.DIMENSION, Constants.DIMENSION);
		g.setColor(Color.BLACK);
	}

	public void paint(Graphics g) {
		drawPanel(g);
		for (int i = 0; i < Constants.DIMENSION / Constants.BOARD_SIZE; i++) {
			g.drawLine(i * Constants.DIMENSION / Constants.BOARD_SIZE, 0,
					i * Constants.DIMENSION / Constants.BOARD_SIZE, Constants.DIMENSION);
		}
		for (int i = 0; i < Constants.DIMENSION / Constants.BOARD_SIZE; i++) {
			g.drawLine(0, i * Constants.DIMENSION / Constants.BOARD_SIZE, Constants.DIMENSION,
					i * Constants.DIMENSION / Constants.BOARD_SIZE);
		}
		
		
	}
}
