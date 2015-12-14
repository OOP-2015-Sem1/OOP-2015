package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import account.Account;
import board.Board;
import cards.Card;
import cards.Deck;

public class Controller implements ActionListener {
	private Board board;
	private Deck deck;

	private Account account = null;

	private List<Card> dealerHand = new ArrayList<Card>();
	private List<ArrayList<Card>> playerHands = new ArrayList<ArrayList<Card>>();

	private boolean dealerDone;
	private List<Boolean> playersDone = new ArrayList<Boolean>();

	private int nrPlayers = 1;
	private int mPlayerNr;

	public Controller() {
		board = new Board(this);
	}

	public void actionPerformed(ActionEvent ae) {
		String button = ae.getActionCommand();
		if (button == "New Game") {
			startNewGame();
		} else if (button == "Hit") {
			addNewCard();
		} else if (button == "Stand") {
			getResult();
		} else if (button == "Log-in or create account") {
			String userName = JOptionPane.showInputDialog(board, "Please enter in your username:",
					"Account Login and Register", JOptionPane.PLAIN_MESSAGE);
			if (userName != null) {
				account = new Account(userName);
				board.showStart(nrPlayers);
				board.displayScore.setEnabled(true);
			}
		} else if (button == "Play as guest") {
			account = null;
			board.showStart(nrPlayers);
			board.displayScore.setEnabled(false);
		} else if (button == "Adjust number of players") {
			nrPlayers = Integer.parseInt((String) JOptionPane.showInputDialog(board,
					"Please enter in the number of players(1-3):", "Number of players", JOptionPane.PLAIN_MESSAGE));
			adjustNrPlayers();
		} else if (button == "Display score") {
			displayScore();
		}
	}

	private void startNewGame() {
		deck = new Deck();
		board.clear();
		dealerHand.clear();
		playerHands.clear();
		playersDone.clear();
		dealerDone = false;
		for (int i = 0; i < nrPlayers; i++) {
			playerHands.add(new ArrayList<Card>());
			playersDone.add(false);
		}
		board.setButtons(true);
		for (int i = 0; i < 2; i++) {
			addNewCard();
		}
		if (checkScore(dealerHand) == 21) {
			dealerDone = true;
			for (int i = 0; i < nrPlayers; i++) {
				playersDone.set(i, true);
			}
			getResult();
		}
	}

	private void addNewCard() {
		for (int i = 0; i < nrPlayers; i++) {
			if (!playersDone.get(i)) {
				int pScore = addCard(playerHands.get(i));
				if (i == mPlayerNr) {
					if (pScore >= 21) {
						playersDone.set(i, true);
						getResult();
					} else {
						board.setInfo("Your score: " + pScore, i);
					}
				} else {
					if (pScore > 21) {
						playersDone.set(i, true);
					} else if (pScore >= 17 && !isAce(playerHands.get(i)) || pScore >= 20) {
						playersDone.set(i, true);
						board.setInfo("Player" + (i + 1) + " Score: " + pScore, i);
					}
				}
				board.drawPlayer(playerHands.get(i).get(playerHands.get(i).size() - 1), i);
			}
		}
		if (!dealerDone) {
			int dScore = addCard(dealerHand);
			if (dScore > 21) {
				for (int i = 0; i < nrPlayers; i++) {
					playersDone.set(i, true);
				}
				dealerDone = true;
				getResult();
			} else if (dScore >= 17) {
				dealerDone = true;
				board.drawDealer(dealerHand.get(dealerHand.size() - 1));
			} else {
				board.drawDealer(dealerHand.get(dealerHand.size() - 1));
			}
		}
	}

	private int addCard(List<Card> hand) {
		Card card = deck.getRandCard();
		hand.add(card);
		return checkScore(hand);
	}

	private void getResult() {
		playersDone.set(mPlayerNr, true);
		board.setButtons(false);
		while (inGame()) {
			addNewCard();
		}
		board.showDealer();
		int dScore = checkScore(dealerHand);
		if (dScore > 21) {
			dScore = 0;
		}
		for (int i = 0; i < nrPlayers; i++) {
			int pScore = checkScore(playerHands.get(i));
			if (pScore > 21) {
				if (i == mPlayerNr) {
					board.setInfo("You busted.", i);
				} else {
					board.setInfo("Player" + (i + 1) + " busted.", i);
				}
			} else {
				if (pScore > dScore) {
					if (i == mPlayerNr) {
						board.setInfo("You won!", i);
						updateAccount();
					} else {
						board.setInfo("Player" + (i + 1) + " won.", i);
					}
				} else if (pScore == dScore) {
					if (i == mPlayerNr) {
						board.setInfo("You tied.", i);
					} else {
						board.setInfo("Player" + (i + 1) + " tied.", i);
					}
				} else {
					if (i == mPlayerNr) {
						board.setInfo("You lost.", i);
					} else {
						board.setInfo("Player" + (i + 1) + " lost.", i);
					}
				}
			}
		}
	}

	private int checkScore(List<Card> hand) {
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

	private boolean isAce(List<Card> hand) {
		for (Card card : hand) {
			if (card.getValue() == 11) {
				ArrayList<Card> tempHand = new ArrayList<Card>(hand);
				tempHand.remove(card);
				if (checkScore(tempHand) <= 10) {
					return true;
				}
			}
		}
		return false;
	}

	private void adjustNrPlayers() {
		if (nrPlayers == 3) {
			mPlayerNr = 1;
		} else {
			mPlayerNr = 0;
		}
		board.adjustNrPlayers(nrPlayers);
	}

	private boolean inGame() {
		if (dealerDone == false)
			return true;
		for (int i = 0; i < nrPlayers; i++) {
			if (playersDone.get(i) == false)
				return true;
		}
		return false;
	}

	private void updateAccount() {
		if (account != null) {
			account.addScore(checkScore(playerHands.get(mPlayerNr)));
			account.saveScores();
		}
	}

	private void displayScore() {
		board.setInfo("Your total score is: " + account.returnScore(), mPlayerNr);
	}
}
