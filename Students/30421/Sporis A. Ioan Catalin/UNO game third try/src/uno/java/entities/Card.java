package uno.java.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class Card extends JPanel {
	public static int HIGHEST_CARD = 9;
	public static int LOWEST_CARD = 0;
	public static int DRAW_TWO = 2;
	public static int DRAW_FOUR = 4;
	public static int NO_SPECIAL_CARDS = 8;
	public static int NO_EACH_SPECIAL_CARD = 2;
	public static int NO_NORMAL_CARDS = 19;
	public static int NO_OF_CARDS = 104;

	private String cardName;

	private int value;
	private Color color;
	public boolean isSpecial;
	public Image cardImage;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(this.cardImage, 2, 2, this);

	}

	public void loadCardImage(String imagePath) {
		this.cardImage = new ImageIcon(imagePath).getImage();
	}

}
