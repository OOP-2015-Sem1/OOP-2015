package Object;

import java.awt.Color;

import javax.swing.JButton;

import Logic.RandomLogic;

public class Card extends JButton {
	
	private String suit;
	private String rank;
	private Color colorBackground;
	private int order;
	
	private RandomLogic random = new RandomLogic();
	
	public Card() {
		if (!SharedPreferences.isCardCliked()) {
			setBackground(Color.WHITE);
		} else {
			setBackground(Color.BLUE);
		}
		
	}
	
	public void setCardOreder(int i) {
		this.order = i;
	}
	
	public int getCardOrder() {
		return order;
	}
	public void setCardBackground(Color color){
		this.colorBackground = color;
		setBackground(color);
	}
	
	public Color getCardBackground(){
		return colorBackground;
	}
	
	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
}