package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import core.GameManagerImpl;
import model.Bomberman;
import model.Constants;
import model.Wall;

public class GameDrawer extends JPanel {

	/**
	 * This class renders all game elements
	 */
	private static final long serialVersionUID = 1L;

	private Image background;
	private Wall[][] board;
	private Bomberman bomberman;
	private BombermanDrawer bombermanDrawer;
	private WallDrawer wallDrawer;
	private GameManagerImpl gmi;

	public GameDrawer(String mapName, GameManagerImpl gmi) {

		try {
			background = new ImageIcon(Constants.resourcesDir + mapName + "\\background.png").getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// this.gmi = gmi;
		this.board = gmi.getBoard();
		this.bomberman = gmi.getBomberman();
		bombermanDrawer = new BombermanDrawer();
		wallDrawer = new WallDrawer();

	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(background, 0, 0, null);
		// draw the wall elements
		try {
			for (int i = 0; i < Constants.BLOCK_COUNT_X; i++) {
				for (int j = 0; j < Constants.BLOCK_COUNT_Y; j++) {
					wallDrawer.paintWall(board[i][j], g);
				}
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
		;
		// draw the bomberman
		bombermanDrawer.paintBomberman(bomberman, g2D);
	}

}
