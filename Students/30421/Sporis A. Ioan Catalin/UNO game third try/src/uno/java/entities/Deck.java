package uno.java.entities;

import java.awt.Color;
import java.util.ArrayList;

import uno.java.constants.Constants;

public class Deck implements CardDealer {
	public ArrayList<Card> deck = new ArrayList<Card>();
	public int nr_Of_Cards_In_Deck = Card.NO_OF_CARDS;

	public Deck() {
		deck.addAll(createNormalCards(Color.BLUE));
		deck.addAll(createNormalCards(Color.GREEN));
		deck.addAll(createNormalCards(Color.RED));
		deck.addAll(createNormalCards(Color.YELLOW));

		deck.addAll(createSpecialCards("Draw two", Card.DRAW_TWO, false, false, false, Color.BLUE));
		deck.addAll(createSpecialCards("Draw two", Card.DRAW_TWO, false, false, false, Color.GREEN));
		deck.addAll(createSpecialCards("Draw two", Card.DRAW_TWO, false, false, false, Color.RED));
		deck.addAll(createSpecialCards("Draw two", Card.DRAW_TWO, false, false, false, Color.YELLOW));

		deck.addAll(createSpecialCards("Reverse", 0, false, false, true, Color.BLUE));
		deck.addAll(createSpecialCards("Reverse", 0, false, false, true, Color.GREEN));
		deck.addAll(createSpecialCards("Reverse", 0, false, false, true, Color.RED));
		deck.addAll(createSpecialCards("Reverse", 0, false, false, true, Color.YELLOW));

		deck.addAll(createSpecialCards("Skip", 0, false, true, false, Color.BLUE));
		deck.addAll(createSpecialCards("Skip", 0, false, true, false, Color.GREEN));
		deck.addAll(createSpecialCards("Skip", 0, false, true, false, Color.RED));
		deck.addAll(createSpecialCards("Skip", 0, false, true, false, Color.YELLOW));

		for (int i = 0; i < Card.NO_EACH_SPECIAL_CARD; i++) {
			deck.addAll(createSpecialCards("Wild", 0, true, false, false, Color.BLACK));
			deck.addAll(createSpecialCards("Wild draw 4", Card.DRAW_FOUR, true, false, false, Color.BLACK));
		}
	}

	@Override
	public ArrayList<Card> createNormalCards(Color color) {
		int i;
		NormalCard[] cards = new NormalCard[Card.NO_NORMAL_CARDS];
		ArrayList<Card> cardList = new ArrayList<Card>();
		int k = 0;
		String colorLabel = " ";
		for (i = Card.LOWEST_CARD; i < Card.NO_NORMAL_CARDS; i++) {
			cards[i] = new NormalCard();
			cards[i].setCardName(Integer.toString(k));
			cards[i].setColor(color);
			cards[i].setValue(k);
			cards[i].setSpecial(false);
			if (color.equals(Color.BLUE)) {
				colorLabel = Constants.BLUE;
			} else if (color.equals(Color.GREEN)) {
				colorLabel = Constants.GREEN;
			} else if (color.equals(Color.RED)) {
				colorLabel = Constants.RED;
			} else if (color.equals(Color.YELLOW)) {
				colorLabel = Constants.YELLOW;
			}
			cards[i].loadCardImage(
					"D:\\Java assignments\\UNO game third try\\Resorces\\" + Integer.toString(k) + colorLabel + ".png");

			cardList.add(cards[i]);
			k++;
			if (k == 10) {
				k = 1;
			}

		}

		return cardList;
	}

	@Override
	public ArrayList<Card> createSpecialCards(String cardName, int draw, boolean wild, boolean skip, boolean reverse,
			Color color) {
		int i;
		SpecialCard[] sCard = new SpecialCard[Card.NO_EACH_SPECIAL_CARD];
		ArrayList<Card> specialCardList = new ArrayList<Card>();

		for (i = Card.LOWEST_CARD; i < Card.NO_EACH_SPECIAL_CARD; i++) {
			sCard[i] = new SpecialCard(cardName);
			sCard[i].setDraw(draw);
			sCard[i].setSkip(skip);
			sCard[i].setReverse(reverse);
			sCard[i].setWild(wild);
			sCard[i].setColor(color);
			String colorLabel = " ";

			if (color.equals(Color.BLUE)) {
				colorLabel = Constants.BLUE;
			} else if (color.equals(Color.GREEN)) {
				colorLabel = Constants.GREEN;
			} else if (color.equals(Color.RED)) {
				colorLabel = Constants.RED;
			} else if (color.equals(Color.YELLOW)) {
				colorLabel = Constants.YELLOW;
			}
			if (wild == true && draw == 0) {

				sCard[i].loadCardImage(
						"D:\\Java assignments\\UNO game third try\\Resorces\\" + Constants.WILD + ".png");
			}

			else if (skip == true) {

				sCard[i].loadCardImage(
						"D:\\Java assignments\\UNO game third try\\Resorces\\" + Constants.SKIP + colorLabel + ".png");
			} else if (reverse == true) {

				sCard[i].loadCardImage("D:\\Java assignments\\UNO game third try\\Resorces\\" + Constants.REVERSE
						+ colorLabel + ".png");
			} else if (draw == 2) {

				sCard[i].loadCardImage(
						"D:\\Java assignments\\UNO game third try\\Resorces\\" + Constants.DRAW2 + colorLabel + ".png");
			} else if (draw == 4) {

				sCard[i].loadCardImage(
						"D:\\Java assignments\\UNO game third try\\Resorces\\" + Constants.WILDRAW + ".png");
			}
			specialCardList.add(sCard[i]);

		}

		return specialCardList;
	}

	public void shuffleDeck() {
		Card[] cards = new Card[Card.NO_OF_CARDS];
		int i = 0;
		for (Card c : this.deck) {
			if (i < Card.NO_OF_CARDS) {
				cards[i] = this.deck.get(i);
				i++;
			}
		}
		for (i = Card.NO_OF_CARDS - 1; i >= 0; i--) {
			int j = this.random(0, i);
			Card card = cards[i];
			cards[i] = cards[j];
			cards[j] = card;

		}
		this.deck.removeAll(deck);

		for (i = 0; i < Card.NO_OF_CARDS; i++) {
			this.deck.add(cards[i]);
		}
	}

	private int random(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range + min);
	}

	public Card popFromDeck() {
		int index = this.nr_Of_Cards_In_Deck - 1;
		Card card = this.deck.get(index);
		this.deck.remove(index);
		this.nr_Of_Cards_In_Deck--;
		return card;
	}
}
