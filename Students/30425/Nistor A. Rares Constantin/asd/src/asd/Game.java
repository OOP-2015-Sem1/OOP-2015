package asd;

import javax.swing.*;
import java.awt.event.*;

public class Game extends JFrame implements ActionListener {

	private Deck deck;
	private Player player = new Player();
	private Player dealer = new Player();

	Gui gui = new Gui();

	public Game() {
		gui.hit.addActionListener(this);
		gui.stay.addActionListener(this);
		gui.deal.addActionListener(this);
	}

	private void hitPlayer() {
		Card newCard = player.takeCard(deck.dealCard());
		ImageIcon b = new ImageIcon(getClass().getResource(newCard.toString()));
		gui.playerPanel.add(new JLabel(b));
		gui.playerPanel.updateUI();
	}

	
	//I know that this 2 functions do almost the same think 
	//I will try to combine them
	
	private void hitDealer() {
		Card newCard = dealer.takeCard(deck.dealCard());
		ImageIcon b = new ImageIcon(getClass().getResource(newCard.toString()));
		gui.dealerPanel.add(new JLabel(b));
		gui.dealerPanel.updateUI();
	}

	private void deal() {
		gui.playerPanel.removeAll();
		gui.dealerPanel.removeAll();
		gui.playerPanel.updateUI();
		gui.dealerPanel.updateUI();
		player.dropHand();
		dealer.dropHand();
		deck = new Deck();
		deck.shuffle();
		hitPlayer();
		hitDealer();
		hitPlayer();
		hitDealer();
	}

	private void endGame() {
		gui.hit.setEnabled(false);
		gui.stay.setEnabled(false);
		gui.deal.setEnabled(true);
		
		if (player.getHandValue() > 21) {
			gui.statusLabel.setText("Player Busts, Dealer Wins");
		} else if (dealer.getHandValue() > 21) {
			gui.statusLabel.setText("Dealer Busts, Player Wins");
		} else if (dealer.getHandValue() == player.getHandValue()) {
			gui.statusLabel.setText("Equal");
		} else if (dealer.getHandValue() < player.getHandValue()) {
			gui.statusLabel.setText("Player Wins");
		} else {
			gui.statusLabel.setText("Dealer Wins");
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gui.hit) {
			hitPlayer();
			if (player.getHandValue() > 21) {
				endGame();
			}
		}

		if (e.getSource() == gui.stay) {
			while (player.getHandValue() > dealer.getHandValue() || dealer.getHandValue() < 17) {
				hitDealer();
			}
			endGame();
		}

		if (e.getSource() == gui.deal) {
			deal();
			gui.statusLabel.setText("");
			gui.hit.setEnabled(true);
			gui.stay.setEnabled(true);
			gui.deal.setEnabled(false);
		}

	}

	public static void main(String[] args) {
		new Game();
	}
}
