package models;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Card {

	private final int suit;

	private final int value;

	public Card(int theValue, int theSuit) {
		this.value = theValue;
		this.suit = theSuit;
	}

	public int getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}

	public BufferedImage getImage() {
		String fileName = value + "_" + "of" + "_" + suit + ".png";
		try {
			return ImageIO.read(Card.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args){
		Card c= new Card(12, 3);
		JFrame f= new JFrame();
		f.add(new JLabel(new ImageIcon(c.getImage())));
		f.setSize(200,200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}