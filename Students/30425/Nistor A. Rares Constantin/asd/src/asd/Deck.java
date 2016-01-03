package asd;

import java.util.Random;

public class Deck {

	private Card[] cards;
	private int nrOfCards = 0;

	public Deck() {
		cards = new Card[52];
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 13; j++) {
					cards[nrOfCards] = new Card(j, "/" + i + "" + j + ".png");
					nrOfCards++;
				}
	
	}

	public Card dealCard() {
		return cards[--nrOfCards];
	}

	public void shuffle() {
		Random r = new Random();
		Card swap;
		int j;
		for (int i = 0; i < nrOfCards; i++) {
			j = r.nextInt(nrOfCards);
			swap = cards[i];
			cards[i] = cards[j];
			cards[j] = swap;
		}
	}
}
