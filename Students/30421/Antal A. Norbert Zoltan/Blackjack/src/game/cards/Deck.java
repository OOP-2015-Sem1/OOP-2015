package game.cards;

import java.util.*;

import game.cards.Card;

public class Deck {

	List<Card> cards;

	public Deck(List<Card> cards) {
		this.cards = new ArrayList<Card>(cards);
	}

	public Card getRandCard() {
		return cards.remove(new Random().nextInt(cards.size()));
	}
}
