package go;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import go.Grid;

/**
 * Provides I/O.
 * 
 *
 */
public class GameBoard extends JPanel {

	public int scoreBlack = 0;
	public int scoreWhite = 0;

	private static final long serialVersionUID = -494530433694385328L;

	/**
	 * Number of rows/columns.
	 */
	// public static int SIZE = 9;
	/**
	 * Number of tiles in row/column. (Size - 1)
	 */
	// public static final int N_OF_TILES = (size - 1);
	public static final int TILE_SIZE = 40;
	public static final int BORDER_SIZE = TILE_SIZE;

	/**
	 * Black/white player/stone
	 * 
	 *
	 */
	public enum State {
		BLACK, WHITE
	}

	private State current_player;
	private Grid grid;
	private Point lastMove;

	public final int size;

	public GameBoard(int size) {
		this.size = size;
		this.setBackground(Color.ORANGE);
		grid = new Grid(size);
		// Black always starts
		current_player = State.BLACK;

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Converts to float for float division and then rounds to
				// provide nearest intersection.
				int row = Math.round((float) (e.getY() - BORDER_SIZE) / TILE_SIZE);
				int col = Math.round((float) (e.getX() - BORDER_SIZE) / TILE_SIZE);

				// System.out.println(String.format("y: %d, x: %d", row, col));

				// Check wherever it's valid
				if (row >= size || col >= size || row < 0 || col < 0) {
					return;
				}

				if (grid.isOccupied(row, col)) {
					return;
				}

				grid.addStone(row, col, current_player);
				int captured=grid.getCapture();
				//System.out.println("CAPTURED:"+captured);
				grid.resetCapture();
				if (current_player == State.BLACK) {
					scoreBlack++;
					
					scoreWhite-=captured;

				} else {
					scoreWhite++;
					scoreBlack-=captured;
				}
				//print the score (needs improvement)
				System.out.println("B: "+scoreBlack);
				System.out.println("W: "+scoreWhite+"\n");
				lastMove = new Point(col, row);

				// Switch current player
				if (current_player == State.BLACK) {
					current_player = State.WHITE;
				} else {
					current_player = State.BLACK;
				}
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.BLACK);
		// Draw rows.
		for (int i = 0; i < size; i++) {
			g2.drawLine(BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE, TILE_SIZE * (size - 1) + BORDER_SIZE,
					i * TILE_SIZE + BORDER_SIZE);
		}
		// Draw columns.
		for (int i = 0; i < size; i++) {
			g2.drawLine(i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE,
					TILE_SIZE * (size - 1) + BORDER_SIZE);
		}
		// Iterate over intersections
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				State state = grid.getState(row, col);
				if (state != null) {
					if (state == State.BLACK) {
						g2.setColor(Color.BLACK);
					} else {
						g2.setColor(Color.WHITE);
					}
					g2.fillOval(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
							row * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
				}
			}
		}
		// Highlight last move
		if (lastMove != null) {
			g2.setColor(Color.RED);
			g2.drawOval(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
					lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((size - 1) * TILE_SIZE + BORDER_SIZE * 2, (size - 1) * TILE_SIZE + BORDER_SIZE * 2);
	}

	private boolean passedPreviously;

	// pass is not workink ok (yet)
	public void pass() {
		if (passedPreviously) {
			JOptionPane.showMessageDialog(null, "Game over.");
			if (current_player == State.BLACK) {
				current_player = State.WHITE;
			} else {
				current_player = State.BLACK;
			}
		}

		lastMove = null;
		passedPreviously = true;

	}

}