package game;

public class HandValue extends Hand {
	public int getHandValue() {
		int value = 0;
		int cards = getCardsNumber();
		Card card;
		for (int i = 0; i < cards; i++) {
			card = getCard(i);
			int cardVal = card.getValue();
			if (cardVal > 10)
				cardVal = 10;
			if (cardVal == 1) {
				value = value + cardVal;
				if (value + 10 <= 21)
					value = value + 10;
			} else
				value = value + cardVal;

		}
		return value;
	}
}