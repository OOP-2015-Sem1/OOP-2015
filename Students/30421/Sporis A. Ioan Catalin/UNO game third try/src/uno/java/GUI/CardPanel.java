package uno.java.GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CardPanel extends JPanel {
	private Image cardImage;

	public CardPanel(String imagePath) {
		this.loadCardImage(imagePath);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(this.cardImage, 2, 2, this);
	}

	public void loadCardImage(String imagePath) {
		this.cardImage = new ImageIcon(imagePath).getImage();
	}
}
