package models;

public class BlackJackHand extends Hand {

	public int getBlackjackValue() {

		int value;
		boolean isAce;
		int nrOfCards;

		value = 0;
		isAce = false;
		nrOfCards = getNumberOfCards();

		for (int i = 0; i < nrOfCards; i++) {
			Card card;
			int cardValue;
			card = getCard(i);
			cardValue = card.getValue();
			if (cardValue > 10) {
				cardValue = 10;
			}
			if (cardValue == 1) {
				isAce = true;
			}
			value = value + cardValue;
		}

		if (isAce == true && value + 10 <= 21)
			value = value + 10;

		return value;

	}

}
