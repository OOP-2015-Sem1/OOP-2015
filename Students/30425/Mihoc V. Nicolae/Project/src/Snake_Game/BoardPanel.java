package Snake_Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int COL = 27;

	public static final int ROW = 27;

	public static final int TILE_SIZE = 25;

	private SnakeGame game;

	private TileType[] tiles;
	
	private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);

	public BoardPanel(SnakeGame game) {
		this.game = game;
		this.tiles = new TileType[ROW * COL];

		setPreferredSize(new Dimension(COL * TILE_SIZE, ROW * TILE_SIZE));
		setBackground(Color.BLACK);
	}

	public void clearBoard() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = null;
		}
	}

	public void setTile(Point point, TileType type) {
		setTile(point.x, point.y, type);
	}

	public void setTile(int x, int y, TileType type) {
		tiles[y * ROW + x] = type;
	}

	public TileType getTile(int x, int y) {
		return tiles[y * ROW + x];
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int x = 0; x < COL; x++) {
			for (int y = 0; y < ROW; y++) {
				TileType type = getTile(x, y);
				if (type != null) {
					drawTile(x * TILE_SIZE, y * TILE_SIZE, type, g);
				}
			}
		}

		g.setColor(Color.GRAY);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		for (int x = 0; x < COL; x++) {
			for (int y = 0; y < ROW; y++) {
				g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, getHeight());
				g.drawLine(0, y * TILE_SIZE, getWidth(), y * TILE_SIZE);
			}
		}

		if (game.isGameOver() || game.isNewGame() || game.isPaused()) {
			g.setColor(Color.WHITE);

			int centerX = getWidth() / 2;
			int centerY = getHeight() / 2;

			String largeMessage = null;
			String smallMessage = null;
			if (game.isNewGame()) {
				largeMessage = "Snake Game!";
				smallMessage = "Have Fun!";
			} else if (game.isGameOver()) {
				largeMessage = "Game Over!";
				smallMessage = "Press Enter to Restart";
			} else if (game.isPaused()) {
				largeMessage = "Paused";
				smallMessage = "Press P to Resume";
			}
			g.setFont(FONT);
			g.drawString(largeMessage, centerX - g.getFontMetrics().stringWidth(largeMessage) / 2, centerY - 50);
			g.drawString(smallMessage, centerX - g.getFontMetrics().stringWidth(smallMessage) / 2, centerY + 50);
		}
	}

	private void drawTile(int x, int y, TileType type, Graphics g) {
		switch (type) {

		case Apple:
			g.setColor(Color.GREEN);
			g.fillOval(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4);
			break;

		case SnakeBody:
			g.setColor(Color.WHITE);
			g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
			break;

		case SnakeHead:
			g.setColor(Color.RED);
			g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		}
	}
}
