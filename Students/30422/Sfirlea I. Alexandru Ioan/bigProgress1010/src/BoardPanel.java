import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {

	public static int back_X = 0;
	public static int back_Y = 0;

	public static int mouse_X = 999;
	public static int mouse_Y = 999;

	public static int random_X = 0;
	public static int random_Y = 0;

	private static int m_lastMovedX;
	private static int m_lastMovedY;

	Game game = new Game();

	public BoardPanel() {
		setPreferredSize(new Dimension(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT));
		setBackground(Color.BLACK);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// This helps simplify the positioning of things.
		g.translate(Constants.BORDER_WIDTH, Constants.BORDER_WIDTH);

		game.startGame();
		redrawMap(g);
		redrawShadow(g);
		redrawRandomPiece(g);
		redrawScore(g);

	}

	private void redrawScore(Graphics g) {
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
		g.drawString("High Score : "+ game.getHighScore(), Constants.NEWX + 10,
				Constants.NEWY + Constants.TILE_SIZE + 25);
	}

	private void redrawShadow(Graphics g) {
		Color cool = new Color(198, 182, 182);
		if (m_lastMovedX < 407) {
			for (int r = 0; r < game.randMat.getDimI(); r++) {
				for (int c = 0; c < game.randMat.getDimJ(); c++) {
					int[][] form = game.randMat.getForm();
					if (form[r][c] != 0) {
						drawShadow(cool, c * Constants.TILE_SIZE + m_lastMovedX - 20,
								r * Constants.TILE_SIZE + m_lastMovedY - 20, g);
					}
				}
			}
		}
	}

	private void redrawMap(Graphics g) {
		g.setColor(new Color(96, 96, 96));
		g.fillRect(0, 0, Constants.TILE_SIZE * Constants.COUNT_COL, Constants.TILE_SIZE * Constants.ROW_COUNT);

		/*
		 * draw the pieces ia fiecare patratel din matricea din spate verifica
		 * daca ii 0 daca nu ii 0 il pune pe harta
		 */

		for (int x = 0; x < Constants.COUNT_COL; x++) {
			for (int y = 0; y < Constants.ROW_COUNT; y++) {
				int elem = game.getTile(x, y);
				if (elem != 0) {
					drawPatratel(game.getColor(), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, g);
				}
			}
		}

		/*
		 * Draw the background grid above the pieces (serves as a useful visual
		 * for players, and makes the pieces look nicer by breaking them up.
		 */
		g.setColor(Color.white);
		for (int x = 0; x <= Constants.COUNT_COL; x++) {
			for (int y = 0; y <= Constants.ROW_COUNT; y++) {
				g.drawLine(0, y * Constants.TILE_SIZE, Constants.COUNT_COL * Constants.TILE_SIZE,
						y * Constants.TILE_SIZE);// linii
				// ORIZONTALE
				g.drawLine(x * Constants.TILE_SIZE, 0, x * Constants.TILE_SIZE,
						Constants.ROW_COUNT * Constants.TILE_SIZE);
			}
		}

	}

	private void redrawRandomPiece(Graphics g) {
		/*
		 * DRAW the grid where the new piece appears
		 */
		g.setColor(new Color(96, 96, 96));
		g.fillRect(Constants.NEWX, 0, Constants.TILE_SIZE * Constants.COUNT_COL / 2,
				Constants.TILE_SIZE * Constants.ROW_COUNT / 2);

		for (int x = 0; x < Constants.COUNT_COL / 2; x++) {
			for (int y = 0; y < Constants.ROW_COUNT / 2; y++) {
				int elem = game.getRandomTile(x, y);
				if (elem != 0) {
					drawPatratel(game.getColor(), Constants.NEWX + x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, g);
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

	private void drawPatratel(Color base, int x, int y, Graphics g) {

		/*
		 * Fill the entire tile with the base color.
		 */
		g.setColor(base);
		g.fill3DRect(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE, true);

	}

	private void drawShadow(Color base, int x, int y, Graphics g) {

		/*
		 * Fill the entire tile with the base color.
		 */
		g.setColor(base);
		g.fillRect(x, y, Constants.TILE_SIZE - 5, Constants.TILE_SIZE - 5);
		g.setColor(new Color(255, 255, 255));
		g.drawRect(x, y, Constants.TILE_SIZE - 5, Constants.TILE_SIZE - 5);

	}

	public void mouseClicked(MouseEvent e) {
		mouse_X = e.getX();
		mouse_Y = e.getY();

		if (mouse_X < 407) {
			back_X = mouse_Y / Constants.TILE_SIZE;
			back_Y = mouse_X / Constants.TILE_SIZE;
		}

		this.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		m_lastMovedX = e.getX();
		m_lastMovedY = e.getY();
		this.repaint();
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
