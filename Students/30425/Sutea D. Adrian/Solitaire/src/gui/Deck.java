package gui;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import java.util.Random;

public class Deck {
	private String[] types = { "clubs", "diamonds", "hearts", "spades" };
	private Card card;
	private static ArrayList<Card> deck;
	private String path = new String("res\\IMG\\");
	private Random randomNumber;

	public Deck() {
		deck = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j <= 13; j++) {
				card = new Card();
				card.setType(types[i]);
				card.setValue(j);
				card.setIcon(new ImageIcon(path + j + types[i] + ".png"));
				deck.add(card);
			}
		}
		// shuffle the deck
		for (int k = 0; k < 52; k++) {
			int l = randomNumber.nextInt(52);
			Collections.swap(deck, k, l);

		}
	}

	public Card getCard(int i) {
		return deck.get(i);
	}

}
