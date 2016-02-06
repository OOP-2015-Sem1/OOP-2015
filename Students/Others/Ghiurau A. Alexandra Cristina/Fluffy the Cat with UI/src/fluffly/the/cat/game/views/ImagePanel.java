package fluffly.the.cat.game.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private BufferedImage catImage;

	
	public ImagePanel() {
		try {
			catImage = ImageIO.read(new File("res/nyancat.png"));
		} catch (IOException ex) {
			System.err.println("Image not found" + ex);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(catImage, 0, 0, null); // see javadoc for more info on the
										// parameters
	}

	


}