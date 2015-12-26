package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Calculation.Game;
import Main.Constants;

public class RandomPanel implements DrawTile {

	private Game game = new Game();

	public void redrawRandomPiece(Graphics g) {
		/*
		 * DRAW the grid where the new piece appears
		 */
		if (game.returnStatus() == true) {
			g.setColor(new Color(96, 96, 96));
			g.fillRect(Constants.NEWX, 0, Constants.TILE_SIZE * Constants.COUNT_COL / 2,
					Constants.TILE_SIZE * Constants.ROW_COUNT / 2);

			for (int x = 0; x < Constants.COUNT_COL / 2; x++) {
				for (int y = 0; y < Constants.ROW_COUNT / 2; y++) {
					int elem = game.getRandomTile(x, y);
					if (elem != 0) {
						drawPatratel(game.getNewColor(), Constants.NEWX + x * Constants.TILE_SIZE,
								y * Constants.TILE_SIZE, 0, g);
					}
				}
			}

			g.setColor(Color.white);
			for (int x = Constants.COUNT_COL + 1; x <= Constants.COUNT_COL + 1 + Constants.COUNT_COL / 2; x++) {
				for (int y = 0; y <= Constants.ROW_COUNT / 2; y++) {
					g.drawLine(Constants.NEWX, y * Constants.TILE_SIZE,
							(Constants.COUNT_COL + 1 + Constants.COUNT_COL / 2) * Constants.TILE_SIZE,
							y * Constants.TILE_SIZE);// LINII
					// ORIZONTALE
					g.drawLine(x * Constants.TILE_SIZE, 0, x * Constants.TILE_SIZE,
							Constants.ROW_COUNT * Constants.TILE_SIZE / 2);
				}
			}
		}
		else
		{
			g.setColor(new Color(96, 96, 96));
			g.drawRect(Constants.NEWX, 0, Constants.TILE_SIZE * Constants.COUNT_COL / 2,
					Constants.TILE_SIZE * Constants.ROW_COUNT / 2);
			g.setFont(Constants.LARGE_FONT);
			g.setColor(Color.red);
			g.drawString("NEW GAME", 447, 105);
			
		}
	}

	public void redrawScore(Graphics g) {

		g.setColor(new Color(96, 96, 96));

		g.fill3DRect(Constants.NEWX, Constants.NEWY, Constants.TILE_SIZE * 3 + 60, Constants.TILE_SIZE, true);

		g.setColor(new Color(102, 172, 255));
		g.setFont(Constants.LARGE_FONT);
		g.drawString("Score: " + game.getScore(), Constants.NEWX + 10, Constants.NEWY + 25);

		g.setColor(new Color(96, 96, 96));
		g.fill3DRect(Constants.NEWX, Constants.NEWY + Constants.TILE_SIZE, Constants.TILE_SIZE * 3 + 60,
				Constants.TILE_SIZE, true);

		g.setColor(new Color(102, 172, 255));
		g.setFont(Constants.LARGE_FONT);
		g.drawString("High Score : " + 1 + ", " + 1, Constants.NEWX + 10, Constants.NEWY + Constants.TILE_SIZE + 25);
	}

	public void drawPatratel(Color base, int x, int y, int cutDim, Graphics g) {

		/*
		 * Fill the entire tile with the base color.
		 */
		g.setColor(base);
		g.fill3DRect(x, y, Constants.TILE_SIZE - cutDim, Constants.TILE_SIZE - cutDim, true);

	}
}
