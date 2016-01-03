package models;

import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> hand = new ArrayList<>();

	public Hand() {
		hand = new ArrayList<>();
	}

	public void clear() {
		hand.clear();
	}

	public void addCard(Card c) {
		if (c != null)
			hand.add(c);
	}

	public void removeCard(Card c) {
		hand.remove(c);
	}

	public void removeCard(int position) {
		if (position >= 0 && position < hand.size())
			hand.remove(position);
	}

	public int getNumberOfCards() {
		return hand.size();
	}

	public Card getCard(int position) {
		if (position >= 0 && position < hand.size())
			return (Card) hand.get(position);
		else
			return null;
	}
}
