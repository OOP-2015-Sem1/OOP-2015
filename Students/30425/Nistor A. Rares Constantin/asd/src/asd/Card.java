package asd;

public class Card {

	private int value;
	private String front;

	Card(int b, String c) {
		value = b;
		front = c;

		if (value == 0) {
			value = 11;
		} else if (value >= 9) {// the cards from 10 above have the value 10
			value = 10;
		} else
			value++;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return front;
	}
}
