package game;

import java.util.*;

import game.board.Board;
import game.cards.*;

public class Player {
	private List<Card> hand = new ArrayList<Card>();
	private boolean done = false;

	private Deck deck;
	private Board board;

	public Player(Deck deck, Board board) {
		this.deck = deck;
		this.board = board;
	}

	public void clear() {
		hand.clear();
		done = false;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean getDone() {
		return done;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void addRandCard() {
		hand.add(deck.getRandCard());
	}

	public int checkScore() {
		int score = 0;
		boolean isAce = false;
		for (int i = 0; i < hand.size(); i++) {
			int value = hand.get(i).getValue();
			if (!isAce && value == 11) {
				isAce = true;
			}
			score += value;
			if (isAce && score > 21) {
				score -= 10;
				isAce = false;
			}

		}
		return score;
	}

	public boolean isAce() {
		ArrayList<Card> tempHand = new ArrayList<Card>(hand);
		boolean retVal = isAceH();
		hand = tempHand;
		return retVal;
	}

	private boolean isAceH() {
		Iterator<Card> itr = hand.iterator();
		while (itr.hasNext()) {
			Card card = itr.next();
			if (card.getValue() == 11) {
				hand.remove(card);
				if (!isAce() && checkScore() <= 10) {
					return true;
				}
			}
		}
		return false;
	}

	public void drawPlayer(int playerNr) {
		if (playerNr == 15) {
			board.drawDealer(hand.get(hand.size() - 1));
		} else {
			board.drawPlayer(hand.get(hand.size() - 1), playerNr);
		}
	}
}
