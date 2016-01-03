package models;

import controllers.MessageDialogs;

public class Deck {

	private Card[] deck;
	private int numberOfDecks = MessageDialogs.queryForNumberOfDecks();;
	private int cardsUsed;
	private int numberOfCards = 52 * numberOfDecks;

	public Deck() {
		deck = new Card[numberOfCards];
		int currentCard = 0;
		for (int suit = 0; suit <= 3; suit++) {
			for (int value = 1; value <= 13; value++) {
				for (int numDeck = 1; numDeck <= numberOfDecks; numDeck++) {
					deck[currentCard] = new Card(value, suit);
					currentCard++;
				}
			}
		}
		cardsUsed = 0;
	}

	public void shuffle() {
		for (int i = 0; i < numberOfCards; i++) {
			int random = i + (int) (Math.random() * (numberOfCards - i));
			Card t = deck[random];
			deck[random] = deck[i];
			deck[i] = t;
		}
		cardsUsed = 0;
	}

	public int cardsLeft() {
		return numberOfCards - cardsUsed;
	}

	public Card dealCard() {
		if (cardsUsed == numberOfCards)
			shuffle();
		cardsUsed++;
		return deck[cardsUsed - 1];
	}
}