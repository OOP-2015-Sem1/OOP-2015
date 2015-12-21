package game.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import account.Account;
import game.board.Board;
import game.cards.Card;
import game.cards.Deck;
import game.player.Dealer;
import game.player.Player;

public class Controller implements ActionListener {
	private Board board;
	private Deck deck;

	private List<Card> cards = new ArrayList<Card>();

	private Account account = null;

	public Dealer dealer;
	private List<Player> players = new ArrayList<Player>();

	private int nrPlayers = 1;
	private int mPlayerNr;

	private boolean isPlayed = false;

	public Controller() {
		board = new Board(this);
		loadCards();
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
			if (!isPlayed) {
				nrPlayers = Integer.parseInt((String) JOptionPane.showInputDialog(board,
						"Please enter in the number of players(1-3):", "Number of players", JOptionPane.PLAIN_MESSAGE));
				adjustNrPlayers();
			} else {
				JOptionPane.showMessageDialog(board,
						"You can't change the number of players while a game is in progress.", "Error",
						JOptionPane.PLAIN_MESSAGE);
			}
		} else if (button == "Display score") {
			displayScore();
		}
	}

	private void startNewGame() {
		setPlayed(true);
		deck = new Deck(cards);
		board.clear();
		dealer = new Dealer(deck, board);
		players.clear();
		for (int i = 0; i < nrPlayers; i++) {
			players.add(new Player(deck, board));
		}
		for (int i = 0; i < 2; i++) {
			addNewCard();
		}
		if (dealer.checkScore() == 21) {
			dealer.setDone(true);
			for (int i = 0; i < nrPlayers; i++) {
				players.get(i).setDone(true);
			}
			getResult();
		}
	}

	private void addNewCard() {
		for (int i = 0; i < nrPlayers; i++) {
			Player player = players.get(i);
			if (!player.getDone()) {
				player.addRandCard();
				int pScore = player.checkScore();
				if (i == mPlayerNr) {
					if (pScore >= 21) {
						players.get(i).setDone(true);
						getResult();
					} else {
						board.setInfo("Your score: " + pScore, i);
					}
				} else {
					if (pScore >= 21) {
						players.get(i).setDone(true);
						if (pScore == 21) {
							board.setInfo("Player" + (i + 1) + "won.", i);
						}
					} else {
						if (pScore >= 17 && !player.isAce() || pScore >= 20) {
							players.get(i).setDone(true);
						}
						board.setInfo("Player" + (i + 1) + " Score: " + pScore, i);
					}
				}
				player.drawPlayer(i);
			}
		}
		if (!dealer.getDone()) {
			dealer.addRandCard();
			int dScore = dealer.checkScore();
			if (dScore > 21) {
				for (int i = 0; i < nrPlayers; i++) {
					players.get(i).setDone(true);
				}
				dealer.setDone(true);
				getResult();
			} else {
				if (dScore >= 17) {
					dealer.setDone(true);
				}
				dealer.drawPlayer();
			}
		}
	}

	private void getResult() {
		setPlayed(false);
		players.get(mPlayerNr).setDone(true);
		while (inGame()) {
			addNewCard();
		}
		board.showDealer();
		int dScore = dealer.checkScore();
		if (dScore > 21) {
			dScore = 0;
		}
		for (int i = 0; i < nrPlayers; i++) {
			int pScore = players.get(i).checkScore();
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

	private void adjustNrPlayers() {
		if (nrPlayers == 3) {
			mPlayerNr = 1;
		} else {
			mPlayerNr = 0;
		}
		board.adjustNrPlayers(nrPlayers);
	}

	private boolean inGame() {
		if (dealer.getDone() == false)
			return true;
		for (int i = 0; i < nrPlayers; i++) {
			if (players.get(i).getDone() == false)
				return true;
		}
		return false;
	}

	private void updateAccount() {
		if (account != null) {
			account.addScore(players.get(mPlayerNr).checkScore());
			account.saveScores();
		}
	}

	private void displayScore() {
		board.setInfo("Your total score is: " + account.returnScore(), mPlayerNr);
	}

	private void loadCards() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 4; j++) {
				cards.add(new Card("CardImages/c" + (4 * i + j) + ".png", i + 2));
			}
		}
		for (int i = 36; i < 48; i++) {
			cards.add(new Card("CardImages/c" + i + ".png", 10));
		}
		for (int i = 48; i < 52; i++) {
			cards.add(new Card("CardImages/c" + i + ".png", 11));
		}
	}

	private void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
		board.setButtons(isPlayed);
	}
}
