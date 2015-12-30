package Snake_Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SidePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 20);

	private static final Font MEDIUM_FONT = new Font("Tahoma", Font.BOLD, 16);

	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);

	private SnakeGame game;

	public SidePanel(SnakeGame game) {
		this.game = game;

		setPreferredSize(new Dimension(300, BoardPanel.ROW * BoardPanel.TILE_SIZE));
		setBackground(Color.DARK_GRAY);
	}

	private static final int STATISTICS_Y = 100;

	private static final int CONTROLS_Y = 250;

	private static final int MESSAGE_SPACE = 40;

	private static final int CAT_X = 50;

	private static final int DET_X = 50;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);

		g.setFont(LARGE_FONT);
		g.drawString("Snake INFO", getWidth() / 2 - g.getFontMetrics().stringWidth("Snake INFO") / 2, 50);

		g.setFont(MEDIUM_FONT);
		g.drawString("Statistics", CAT_X, STATISTICS_Y);
		g.drawString("Controls", CAT_X, CONTROLS_Y);

		g.setFont(SMALL_FONT);
		

		int drawY = STATISTICS_Y;
		g.drawString("Apples Eaten: " + game.getApplesEaten(), DET_X, drawY += MESSAGE_SPACE);
		
		drawY = CONTROLS_Y;
		g.drawString("Move Up:  Up", DET_X, drawY += MESSAGE_SPACE);
		g.drawString("Move Down:  Down", DET_X, drawY += MESSAGE_SPACE);
		g.drawString("Move Left:  Left", DET_X, drawY += MESSAGE_SPACE);
		g.drawString("Move Right:  Right", DET_X, drawY += MESSAGE_SPACE);
		g.drawString("Pause Game: P", DET_X, drawY += MESSAGE_SPACE);
	}

}
