package cards;

import java.util.*;

public class Deck {

	List<Card> cards = new ArrayList<Card>();

	public Deck() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 4; j++) {
				int cardidx = 4 * i + j;
				cards.add(new Card("CardImages/c" + cardidx + ".png", i + 2));
			}
		}
		for (int i = 36; i < 48; i++) {
			cards.add(new Card("CardImages/c" + i + ".png", 10));
		}
		for (int i = 48; i < 52; i++) {
			cards.add(new Card("CardImages/c" + i + ".png", 11));
		}
	}

	public Card getRandCard() {
		return cards.remove(new Random().nextInt(cards.size()));
	}
}
