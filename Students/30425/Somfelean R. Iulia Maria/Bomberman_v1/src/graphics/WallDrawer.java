package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.BonusType;
import model.Constants;
import model.Wall;

public class WallDrawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Wall images
	private static Image breakableSquare = new ImageIcon(Constants.resourcesDir + "WALL11\\WALL111.png").getImage();
	private static Image unbreakableSquare = new ImageIcon(Constants.resourcesDir + "WALL12\\WALL121.png").getImage();

	public void paintWall(Wall wall, Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		if (wall.getBonusType() == BonusType.EMPTY) {
			// g2D.drawImage(null,wall.getxPosition(), wall.getyPosition(),
			// null);
		} else if (wall.isBreakable()) {
			g2D.drawImage(breakableSquare, wall.getxPosition(), wall.getyPosition(), null);
		} else {
			g2D.drawImage(unbreakableSquare, wall.getxPosition(), wall.getyPosition(), null);
		}

	}

}
