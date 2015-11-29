package fluffy.the.cat.io;

import java.awt.*;
import java.io.FileNotFoundException;

import javax.swing.*;

public class MyPanel extends JPanel {

	private Image cat;
	private Image wall;
	private Image hat;
	
	protected int careImagine;

	public MyPanel() {

		try {
			cat = new ImageIcon("cat.png").getImage();
			wall = new ImageIcon("wall.png").getImage();
			hat = new ImageIcon("hat.png").getImage();
		} catch (Exception e) {
			System.out.println("Image not found");
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		switch (careImagine) {
		case 1: {
			g.drawImage(wall, 0, 0, this);
			break;
		}
		case 2: {
			g.drawImage(cat, 0, 0, this);
			break;
		}
		case 3: {
			g.drawImage(hat, 0, 0, this);
			break;
		}
		default: {
			g.drawImage(null, 0, 0, this);
			break;
		}
		}
	}
	
	public void setCareImagine(int care) {
		this.careImagine = care;
	}

}
