package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import Calculation.Game;
import Main.Constants;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {

	
	private static final long serialVersionUID = 1L;
	public static int back_X = 0;
	public static int back_Y = 0;

	public static int mouse_X = 999;
	public static int mouse_Y = 999;

	public static int random_X = 0;
	public static int random_Y = 0;

	private static int m_lastMovedX;
	private static int m_lastMovedY;
	
	RandomPanel panel = new RandomPanel();
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
		if(game.returnStatus())
			redrawShadow(g);
		panel.redrawRandomPiece(g);
		panel.redrawScore(g , game.getScore(), game.getHighScore());
		this.repaint();

	}

	private void redrawShadow(Graphics g) {
		Color cool = new Color(198, 182, 182);
		if (m_lastMovedX < 407 ) {
			for (int r = 0; r < game.getRandPiece().getDimI(); r++) {
				for (int c = 0; c < game.getRandPiece().getDimJ(); c++) {
					int[][] form = game.getRandPiece().getForm();
					if (form[r][c] != 0) {
						drawPatratel(game.getOldColor(), c * Constants.TILE_SIZE + m_lastMovedX - 20,
								r * Constants.TILE_SIZE + m_lastMovedY - 20,5, g);
					}
				}
			}
		}
	}

	private void redrawMap(Graphics g) {

		if(Constants.choseColor==true)
		g.setColor(Constants.BOARD_COLOR);
		else g.setColor(Color.BLACK);
		g.fillRect(0, 0, Constants.TILE_SIZE * Constants.COUNT_COL, Constants.TILE_SIZE * Constants.ROW_COUNT);

		if (game.returnStatus() == true) {
			/*
			 * draw the pieces ia fiecare patratel din matricea din spate
			 * verifica daca ii 0 daca nu ii 0 il pune pe harta
			 */

			for (int x = 0; x < Constants.COUNT_COL; x++) {
				for (int y = 0; y < Constants.ROW_COUNT; y++) {
					int elem = game.getTile(x, y);
					if(elem!=0)
					drawPatratel(game.returnColor(y, x), y * Constants.TILE_SIZE, x * Constants.TILE_SIZE, 0, g);

				}
			}

			/*
			 * Draw the background grid above the pieces (serves as a useful
			 * visual for players, and makes the pieces look nicer by breaking
			 * them up.
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
		} else {
			g.setFont(Constants.BIG_FONT);
			g.setColor(Color.red);
			g.drawString("GAME OVER", 20, 200);
		}

	}
	



	private void drawPatratel(Color base, int x, int y,int cutDim,  Graphics g) {

		/*
		 * Fill the entire tile with the base color.
		 */
		g.setColor(base);
		g.fill3DRect(x, y, Constants.TILE_SIZE-cutDim,Constants.TILE_SIZE-cutDim, true);

	}


	public void mouseClicked(MouseEvent e) {
		mouse_X = e.getX();
		mouse_Y = e.getY();

		if (mouse_X < 407) {
			back_X = mouse_Y / Constants.TILE_SIZE;
			back_Y = mouse_X / Constants.TILE_SIZE;
		}

		
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