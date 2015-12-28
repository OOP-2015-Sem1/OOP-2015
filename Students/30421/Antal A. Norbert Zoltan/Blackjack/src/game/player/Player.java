package game.player;

import java.util.*;

import game.board.Board;
import game.cards.*;

public class Player {
	protected List<Card> hand = new ArrayList<Card>();
	private boolean done = false;

	private int aces = 0;
	private int score = 0;

	private Deck deck;
	protected Board board;

	public Player(Deck deck, Board board) {
		this.deck = deck;
		this.board = board;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean getDone() {
		return done;
	}

	public void addRandCard() {
		hand.add(deck.getRandCard());
		calcScore();
	}

	public int checkScore() {
		return score;
	}

	private void calcScore() {
		int value = hand.get(hand.size() - 1).getValue();
		if (value == 11) {
			aces++;
		}
		score += value;
		if (aces > 0 && score > 21) {
			score -= 10;
			aces--;
		}
	}

	public boolean isAce() {
		ArrayList<Card> tempHand = new ArrayList<Card>(hand);
		boolean retVal = isAceH();
		hand = tempHand;
		return retVal;
	}

	private boolean isAceH() {
		for (int i = 0; i < hand.size(); i++) {
			Card card = hand.get(i);
			if (card.getValue() == 11) {
				hand.remove(i);
				if (!isAceH() && checkScore() <= 10) {
					return true;
				}
			}
		}
		return false;
	}

	public void drawPlayer(int playerNr) {
		board.drawPlayer(hand.get(hand.size() - 1), playerNr);
	}
}
