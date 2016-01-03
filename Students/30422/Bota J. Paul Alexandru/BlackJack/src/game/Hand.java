package game;

import java.util.Vector;

public class Hand {
	private Vector hand;

	public Hand() {
		hand = new Vector();

	}

	public void addCard(Card card) {
		if (card != null)
			hand.add(card);
	}

	public Card getCard(int position) {
		if (position >= 0 && position < hand.size())
			return (Card) hand.elementAt(position);
		else
			return null;
	}

	public int getCardsNumber() {
		return hand.size();
	}
}
