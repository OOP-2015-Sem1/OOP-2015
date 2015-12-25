package source;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private ArrayList<Card> deck;

	public Deck() {
		this.deck = new ArrayList<>();
	}

	public Deck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	public void removeFirstCard() {
		this.deck.remove(0);
	}

	public void shuffledeck() {
		Collections.shuffle(this.deck);
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void createFullDeck( ArrayList<Card> fullDeck) {
		Card card;
		for (int i = 2; i <= 14; i++) {
			for (int j = 1; j <= 4; j++) {
				switch (j) {
				case 1:
					card = new Card(i, 's');
					fullDeck.add(card);
					break;
				case 2:
					card = new Card(i, 'c');
					fullDeck.add(card);
					break;
				case 3:
					card = new Card(i, 'h');
					fullDeck.add(card);
					break;
				case 4:
					card = new Card(i, 'd');
					fullDeck.add(card);
					break;
				}
			}
		}
		
	}
	public int getSumValue(){
		int Sum=0;
	for (Card c :this.deck){
		if ( c.getValue() > 11)
			Sum+= 10;
		else 
			Sum += c.getValue();
	}
		return Sum;
	}

}
