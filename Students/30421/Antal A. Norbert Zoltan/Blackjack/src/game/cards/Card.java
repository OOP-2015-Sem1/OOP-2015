package game.cards;

import javax.swing.ImageIcon;

public class Card {
	private ImageIcon img;
	private int value;
	
	public Card(String path, int value){
		img = new ImageIcon(path);
		this.value = value;
	}
	
	public ImageIcon getImg(){
		return img;
	}
	
	public int getValue(){
		return value;
	}
}
