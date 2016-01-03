package game;

import java.util.Random;

public class Deck {
	private Card[] deck;
	private int nrCards;
	private Random randomNr;
	private int cardsUsed;

	public Deck() {
		deck = new Card[52];
		nrCards = 0;
		for (int suit = 0; suit < 4; suit++) {
			for (int value = 1; value < 14; value++) {
				deck[nrCards] = new Card(value, suit);
				nrCards++;
			}
		}
		cardsUsed = 0;
	}

	public void shuffle() {
		randomNr = new Random();
		for (int i = 0; i < nrCards; i++) {
			int randomPos = randomNr.nextInt(52);
			Card temp = deck[i];
			deck[i] = deck[randomPos];
			deck[randomPos] = temp;

		}
		cardsUsed = 0;

	}

	public Card dealCard() {
		cardsUsed++;
		return deck[cardsUsed - 1];
	}

}
