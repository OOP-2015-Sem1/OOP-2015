package game;

public class Card {
	public final static int SPADES = 0, HEARTS = 1, DIAMONDS = 2, CLUBS = 3;

	public final static int Ace = 1, Jack = 11, Queen = 12, King = 13;
	private final int suit;
	private final int value;

	public Card(int theValue, int theSuit) {
		value = theValue;
		suit = theSuit;
	}

	public int getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}

	public String getSuitAsString() {

		switch (suit) {
		case SPADES:
			return "spades";
		case HEARTS:
			return "hearts";
		case DIAMONDS:
			return "diamonds";
		case CLUBS:
			return "clubs";
		default:
			return "??";
		}
	}

	public String getValueAsString() {

		switch (value) {
		case 1:
			return "ace";
		case 2:
			return "2";
		case 3:
			return "3";
		case 4:
			return "4";
		case 5:
			return "5";
		case 6:
			return "6";
		case 7:
			return "7";
		case 8:
			return "8";
		case 9:
			return "9";
		case 10:
			return "10";
		case 11:
			return "jack";
		case 12:
			return "queen";
		case 13:
			return "king";
		default:
			return "??";
		}
	}
}
