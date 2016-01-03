package models;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
}