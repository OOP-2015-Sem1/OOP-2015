package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Bomberman;
import model.DirectionType;

public class BombermanDrawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Bomberman images */
	private static String BOMBERMAN_IMAGES_PATH = "D:\\ANUL2\\DynaBlasterv1\\Resources\\Animations";

	private Image[] bombermanMoveDown = new Image[4];
	private Image[] bombermanMoveUp = new Image[4];
	private Image[] bombermanMoveLeft = new Image[4];
	private Image[] bombermanMoveRight = new Image[4];
	public BombermanDrawer() {
		for (int i = 0; i < 4; i++) {
			try {
				bombermanMoveDown[i] = new ImageIcon(BOMBERMAN_IMAGES_PATH + "\\WLK2DN\\WLK2DN" + (i + 1) + ".png")
						.getImage();
			} catch (Exception ex) {
				ex.getMessage();
			}
			
			try {
				bombermanMoveUp[i] = new ImageIcon(BOMBERMAN_IMAGES_PATH + "\\WLK2UP\\WLK2UP" + (i + 1) + ".png")
						.getImage();

			} catch (Exception ex) {
				ex.getMessage();
			}
			
			try {
				bombermanMoveLeft[i] = new ImageIcon(BOMBERMAN_IMAGES_PATH + "\\WLK2LEFT\\WLK2LEFT" + (i + 1) + ".png")
						.getImage();
			} catch (Exception ex) {
				ex.getMessage();
			}
			
			try {
				bombermanMoveRight[i] = new ImageIcon(BOMBERMAN_IMAGES_PATH + "\\WLK2RIGH\\WLK2RIGH" + (i + 1) + ".png")
						.getImage();
			} catch (Exception ex) {
				ex.getMessage();
			}
			
		}

	}

	public void paintBomberman(Bomberman bomberman, Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		if (bomberman.getDirection() == DirectionType.DOWN) {
			g2D.drawImage(bombermanMoveDown[bomberman.getyPosition() % 4], bomberman.getxPosition(),
					bomberman.getyPosition(), null);
		}
		if (bomberman.getDirection() == DirectionType.UP) {
			g2D.drawImage(bombermanMoveUp[bomberman.getyPosition() % 4], bomberman.getxPosition(),
					bomberman.getyPosition(), null);
		}
		if (bomberman.getDirection() == DirectionType.LEFT) {
			g2D.drawImage(bombermanMoveLeft[bomberman.getxPosition() % 4], bomberman.getxPosition(),
					bomberman.getyPosition(), null);
		}
		if (bomberman.getDirection() == DirectionType.RIGHT) {
			g2D.drawImage(bombermanMoveRight[bomberman.getxPosition() % 4], bomberman.getxPosition(),
					bomberman.getyPosition(), null);
		}
	}

}
