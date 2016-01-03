package game;

import java.util.Collections;
import java.util.Vector;

import game.Card;

public class UnoDeck {

	public static final int FULL = 0;
	public static final int EMPTY = 1;

	private Vector<Card> cards;

	public UnoDeck(int mode) {

		cards = new Vector<Card>(108, 0); // 112 cards

		if (mode == FULL) {
			initCards(cards);
		}
	}

	private void initCards(Vector<Card> cards) {

		for (int i = Card.RED; i != 4; i += 1) {

			for (int k = 0; k != 2; k++) {
				for (int j = 0; j != 10; j++) {
					cards.add(new Card(i, j));
				}
			}

			for (int j = 0; j != 2; j++) {
				cards.add(new Card(i, Card.DRAW_TWO));
				cards.add(new Card(i, Card.REVERSE));
				cards.add(new Card(i, Card.SKIP));
			}
		}

		for (int i = 0; i != 4; i += 1) {
			cards.add(Card.NULL, new Card(Card.NULL, Card.WILD));
		}

		for (int i = 0; i != 4; i += 1) {
			cards.add(i, new Card(Card.NULL, Card.WILD_FOUR));
		}

		shuffleDeck();
	}

	public int getSize() {
		return cards.size();
	}

	public Card getCard(int index) {

		Card card = (Card) cards.elementAt(index);
		cards.remove(index);
		return card;
	}

	public Card showCard(int index) {
		return (Card) cards.elementAt(index);
	}

	public void insertCard(Card card) {
		cards.add(0, card);
	}

	public void insertCardAtLast(Card card) {
		cards.add(card);
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	public boolean exists(Card card) {

		boolean sameColor = false;
		boolean sameValue = false;
		boolean isOnlyValue = card.getColor() == Card.NULL;

		for (int i = 0; i != cards.size(); i += 1) {
			sameColor = isOnlyValue ? true : card.getColor() == showCard(i).getColor();
			sameValue = card.getValue() == showCard(i).getValue();

			if (sameColor && sameValue)
				return true;
		}
		return false;
	}

	public void shuffleDeck() {
		Collections.shuffle(cards);
	}

	@SuppressWarnings("unchecked")
	public void sort() {
		Collections.sort(cards);
	}
}