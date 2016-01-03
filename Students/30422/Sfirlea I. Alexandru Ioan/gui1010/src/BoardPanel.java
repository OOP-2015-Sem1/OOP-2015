import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private static final int BORDER_WIDTH = 5;// cat de groasa ii linia

	public static final int COUNT_COL = 10;// numar coloane desenate
	public static final int ROW_COUNT = 10;// numar total randuri

	public static final int TILE_SIZE = 40; // number of pixels for a tile

	private static final int CENTER_X = COUNT_COL * TILE_SIZE / 2;
	private static final int CENTER_Y = ROW_COUNT * TILE_SIZE / 2;

	// total pannel width
	public static final int PANEL_WIDTH = COUNT_COL * TILE_SIZE + BORDER_WIDTH * 2;

	// total hight of panel
	public static final int PANEL_HEIGHT = ROW_COUNT * TILE_SIZE + BORDER_WIDTH * 2;

	// font display
	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 16);
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);

	private static final int NEWX = COUNT_COL * TILE_SIZE + TILE_SIZE;
	private static final int NEWY = (ROW_COUNT / 2 + 1) * TILE_SIZE;

	public static final int[][] matrix = new int[][] { { 0, 1 }, { 1, 1 } };

	public static int[][] bmatrix = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
												  { 0, 0, 0, 0, 0 ,0 ,0 ,0, 0, 0 },
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
												  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
												  };
												  
	public static int[][] randomMatrix = new int[][] { { 0, 0, 0, 0, 0 },
													   { 0, 0, 0, 0, 0 }, 
													   { 0, 0, 0, 0, 0 },
													   { 0, 0, 0, 0, 0 }, 
													   { 0, 0, 0, 0, 0 } };

	public BoardPanel() {
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
	}

	public void addPiece(int x, int y, int[][] matrix) {
		/*
		 * Loop through every tile within the piece and add it to the board only
		 * if the boolean that represents that tile is set to true.
		 */
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix.length; row++) {
				if (matrix[col][row] != 0) {
					setBackMatrix(col + x, row + y, matrix[col][row]);
				}
			}
		}
	}

	public void addRandomPiece(int x, int y, int[][] matrix) {
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix.length; row++) {
				if (matrix[col][row] != 0) {
					setRandomMatrix(col + x, row + y, matrix[col][row]);
				}
			}
		}
	}

	private void setRandomMatrix(int x, int y, int elem) {
		randomMatrix[y][x] = elem;
	}

	private void setBackMatrix(int x, int y, int elem) {
		bmatrix[y][x] = elem;
	}

	/**
	 * Gets a tile by it's column and row from backmatrix.
	 */
	private int getTile(int x, int y) {
		return bmatrix[y][x];
	}

	private int getRandomTile(int x, int y) {
		return randomMatrix[y][x];
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// This helps simplify the positioning of things.
		g.translate(BORDER_WIDTH, BORDER_WIDTH);

		addPiece(0, 0, matrix);
		addRandomPiece(0, 0, matrix);

		redrawMap(g);
		redrawRandomPiece(g);
		redrawScore(g);
		

	}
	private void redrawScore(Graphics g){
		g.setColor(new Color(96, 96, 96));
		g.fill3DRect(NEWX, NEWY, TILE_SIZE * 3 + 60, TILE_SIZE, true);

		g.setColor(new Color(102, 172, 255));
		g.setFont(LARGE_FONT);
		g.drawString("Score: 112321 ", NEWX + 10, NEWY + 25);

		g.setColor(new Color(96, 96, 96));
		g.fill3DRect(NEWX, NEWY + TILE_SIZE, TILE_SIZE * 3 + 60, TILE_SIZE, true);

		g.setColor(new Color(102, 172, 255));
		g.setFont(LARGE_FONT);
		g.drawString("HIGH Score: 112321 ", NEWX + 10, NEWY + TILE_SIZE + 25);
	}

	private void redrawMap(Graphics g) {
		g.setColor(new Color(96, 96, 96));
		g.fillRect(0, 0, TILE_SIZE * COUNT_COL, TILE_SIZE * ROW_COUNT);

		/*
		 * draw the pieces ia fiecare patratel din matricea din spate verifica
		 * daca ii 0 daca nu ii 0 il pune pe harta
		 */

		for (int x = 0; x < COUNT_COL ; x++) {
			for (int y = 0; y < ROW_COUNT; y++) {
				int elem = getTile(x, y);
				if (elem != 0) {
					drawPatratel(new Color(180, 180, 180), x * TILE_SIZE, y * TILE_SIZE, g);
				}
			}
		}

		/*
		 * Draw the background grid above the pieces (serves as a useful visual
		 * for players, and makes the pieces look nicer by breaking them up.
		 */
		g.setColor(Color.white);
		for (int x = 0; x <= COUNT_COL; x++) {
			for (int y = 0; y <= ROW_COUNT; y++) {
				g.drawLine(0, y * TILE_SIZE, COUNT_COL * TILE_SIZE, y * TILE_SIZE);// linii
																					// ORIZONTALE
				g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, ROW_COUNT * TILE_SIZE);
			}
		}

	}

	private void redrawRandomPiece(Graphics g) {
		/*
		 * DRAW the grid where the new piece appears
		 */
		g.setColor(new Color(96, 96, 96));
		g.fillRect(NEWX, 0, TILE_SIZE * COUNT_COL / 2, TILE_SIZE * ROW_COUNT / 2);

		for (int x = 0; x < COUNT_COL / 2; x++) {
			for (int y = 0; y < ROW_COUNT / 2; y++) {
				int elem = getRandomTile(x, y);
				if (elem != 0) {
					drawPatratel(new Color(180, 180, 180), NEWX+ x * TILE_SIZE, y * TILE_SIZE, g);
				}
			}
		}

		g.setColor(Color.white);
		for (int x = COUNT_COL + 1; x <= COUNT_COL + 1 + COUNT_COL / 2; x++) {
			for (int y = 0; y <= ROW_COUNT / 2; y++) {
				g.drawLine(NEWX, y * TILE_SIZE, (COUNT_COL + 1 + COUNT_COL / 2) * TILE_SIZE, y * TILE_SIZE);// LINII
																											// ORIZONTALE
				g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, ROW_COUNT * TILE_SIZE / 2);
			}
		}
	}

	private void drawPatratel(Color base, int x, int y, Graphics g) {

		/*
		 * Fill the entire tile with the base color.
		 */
		g.setColor(base);
		g.fill3DRect(x, y, TILE_SIZE, TILE_SIZE, true);

	}
}
