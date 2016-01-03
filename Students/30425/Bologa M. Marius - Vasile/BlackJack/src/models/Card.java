package models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {

	public final static int SPADES = 0;
	public final static int HEARTS = 1;
	public final static int DIAMONDS = 2;
	public final static int CLUBS = 3;

	public final static int ACE = 1;
	public final static int JACK = 11;
	public final static int QUEEN = 12;
	public final static int KING = 13;
	private static final String IMAGE_DIR = "C:/Users/Bolo/JavaSummer/BlackJack/src/images";

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
		File file = new File(IMAGE_DIR, fileName);
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}