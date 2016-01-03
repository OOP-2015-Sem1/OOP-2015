package uno.java.entities;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import uno.java.GUI.PlayerCardsPanel;
import uno.java.constants.Constants;

public class Player {

	private String Nickname;
	private int nrOfCards;
	private boolean isTurn = false;
	private ArrayList<Card> hand = new ArrayList<Card>();

	private ImageIcon avatar = new ImageIcon();
	public PlayerCardsPanel playerCardsPanel;

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public int getNrOfCards() {
		return nrOfCards;
	}

	public void setNrOfCards(int nrOfCards) {
		this.nrOfCards = nrOfCards;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}

	public void setFirstHand(ArrayList<Card> hand) {
		this.hand.addAll(hand);
		this.nrOfCards = Constants.FIRST_HAND;
	}

	public ArrayList<Card> getHand() {
		return this.hand;
	}

	public void drawCard(Card card) {
		this.hand.add(card);
	}

	public Card removeCard(Card card) {
		this.hand.remove(card);
		return card;
	}
}
