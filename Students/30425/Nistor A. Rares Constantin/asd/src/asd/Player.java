package asd;

public class Player {

	public Card[] cards = new Card[20];
	private int numberCardsInHand = 0;

	public int inHand() {
		return numberCardsInHand;
	}

	public Card takeCard(Card c) {
		cards[numberCardsInHand++] = c;
		return c;
	}

	public void dropHand() {
		numberCardsInHand = 0;
	}

	public int getHandValue() {
		int handVal = 0;
		int nrAce = 0;
		for (int i = 0; i < numberCardsInHand; i++) {
			handVal = handVal + cards[i].getValue();
			if (cards[i].getValue() == 11)
				nrAce++;
		}
		while (handVal > 21 && nrAce > 0) { // Since an Ace can have 11 or 1 potints
											// if the handValue is over 21 and i have one or more Aces
			handVal -= 10;                  // then the Aces will have 1 point
			nrAce--;                        // else they will habe 11
		}                                   
		return handVal;
	}
}
