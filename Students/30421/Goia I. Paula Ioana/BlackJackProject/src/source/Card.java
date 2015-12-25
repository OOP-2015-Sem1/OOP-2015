package source;

import javax.swing.ImageIcon;

public class Card {
	
	private ImageIcon img;
	private int value;
	private char suit;
	
	public Card(int value, char suit){
		img = new ImageIcon("images/"+value+suit+".gif");
		this.value = value;
		this.suit = suit;
	}
	
	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public char getSuit() {
		return suit;
	}

	public void setSuit(char suit) {
		this.suit = suit;
	}
}
