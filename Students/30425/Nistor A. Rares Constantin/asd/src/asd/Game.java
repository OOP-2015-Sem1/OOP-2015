package asd;

import javax.swing.*;

import java.awt.event.*;

public class Game extends JFrame implements ActionListener {
	Gui gui = new Gui();
	Gamble g = new Gamble();
	private Deck deck;
	private Player player = new Player();
	private Player dealer = new Player();
	private Player player1 = new Player();
	private Player player2 = new Player();

	public Game() {
		gui.hit.addActionListener(this);
		gui.stay.addActionListener(this);
		gui.deal.addActionListener(this);
		gui.bet1.addActionListener(this);
		gui.bet2.addActionListener(this);
		gui.bet3.addActionListener(this);
	}

	private void hitDealerDown() {
		Card newCard = dealer.takeCard(deck.dealCard());
		ImageIcon b = new ImageIcon(getClass().getResource("/b2fv.png"));
		gui.face = new ImageIcon(getClass().getResource(newCard.toString()));
		gui.cardDown.setIcon(b);
		gui.dealerPanel.add(gui.cardDown);
		gui.dealerPanel.updateUI();
	}

	private void hit(Player player, JPanel panel) {
		Card newCard = player.takeCard(deck.dealCard());
		ImageIcon b = new ImageIcon(getClass().getResource(newCard.toString()));
		panel.add(new JLabel(b));
		panel.updateUI();
	}

	private void deal(Player player, JPanel panel) {
		panel.removeAll();
		panel.updateUI();
		player.dropHand();
		hit(player, panel);
		hit(player, panel);
	}
    private void dealDealer(){
    	gui.dealerPanel.removeAll();
    	gui.dealerPanel.updateUI();
		dealer.dropHand();
		hitDealerDown();
		hit(dealer, gui.dealerPanel);
    }
	
	private void endGame() {
		gui.hit.setEnabled(false);
		gui.stay.setEnabled(false);
		gui.deal.setEnabled(true);
		gui.bet1.setEnabled(false);
		gui.bet2.setEnabled(false);
		gui.bet3.setEnabled(false);
		gui.cardDown.setIcon(gui.face);
		
		if (player.getHandValue() > 21) {
			gui.statusLabel.setText("YOU LOST");
			g.setBet(0);
			gui.updateMoney(g.getMoney(), 0);
		} else if (dealer.getHandValue() > 21) {
			gui.statusLabel.setText("YOU WON");
			int x = g.getMoney();
			int z = g.getBet();
			x = x + z * 2;
			g.setMoney(x);
			g.setBet(0);
			gui.updateMoney(x, 0);
		} else if (dealer.getHandValue() == player.getHandValue()) {
			gui.statusLabel.setText("YOU MADE EQUAL");
			int x = g.getMoney();
			int z = g.getBet();
			x += z;
			g.setBet(0);
			g.setMoney(x);
			gui.updateMoney(x, 0);
		} else if (dealer.getHandValue() < player.getHandValue()) {
			gui.statusLabel.setText("YOU WON");
			int x = g.getMoney();
			int z = g.getBet();
			x = x + z * 2;
			g.setMoney(x);
			g.setBet(0);
			gui.updateMoney(x, 0);
		} else {
			gui.statusLabel.setText("YOU LOST");
			g.setBet(0);
			gui.updateMoney(g.getMoney(), 0);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gui.bet1) {
			if (g.getMoney() >= 50) {
				int x = g.getMoney();
				int z = g.getBet();
				x -= 50;
				z += 50;
				gui.updateMoney(x, z);
				g.setMoney(x);
				g.setBet(z);
				gui.stay.setEnabled(true);
				gui.hit.setEnabled(true);
			}
		}
		if (e.getSource() == gui.bet2) {
			if (g.getMoney() >= 100) {
				int x = g.getMoney();
				int z = g.getBet();
				x -= 100;
				z += 100;
				gui.updateMoney(x, z);
				g.setMoney(x);
				g.setBet(z);
				gui.stay.setEnabled(true);
				gui.hit.setEnabled(true);
			}
		}
		if (e.getSource() == gui.bet3) {
			if (g.getMoney() >= 200) {
				int x = g.getMoney();
				int z = g.getBet();
				x -= 200;
				z += 200;
				gui.updateMoney(x, z);
				g.setMoney(x);
				g.setBet(z);
				gui.stay.setEnabled(true);
				gui.hit.setEnabled(true);
			}
		}
		if (e.getSource() == gui.hit) {
			hit(player, gui.playerPanel);
			gui.bet1.setEnabled(false);
			gui.bet2.setEnabled(false);
			gui.bet3.setEnabled(false);
			if (player.getHandValue() > 21) {
				if (gui.nrplayers == 1 || gui.nrplayers == 2) {
					while (player1.getHandValue() < 17)
						hit(player1, gui.player1Panel);
					if (gui.nrplayers == 2)
						while (player2.getHandValue() < 17)
							hit(player2, gui.player2Panel);
				}
				endGame();
			}
		}

		if (e.getSource() == gui.stay) {
			while (player.getHandValue() > dealer.getHandValue() || dealer.getHandValue() < 17)
				hit(dealer, gui.dealerPanel);
			if (gui.nrplayers == 1 || gui.nrplayers == 2){
				while (player1.getHandValue() < 17)
					hit(player1, gui.player1Panel);
				if(gui.nrplayers == 2)
					while (player2.getHandValue() < 17)
						hit(player2, gui.player2Panel);
			}
			endGame();
		}

		if (e.getSource() == gui.deal) {
			deck = new Deck();
			deck.shuffle();
			deal(player, gui.playerPanel);
			dealDealer();
			if (gui.nrplayers == 1 || gui.nrplayers == 2) {
				deal(player1, gui.player1Panel);
				if (gui.nrplayers == 2)
					deal(player2, gui.player2Panel);
			}
			gui.statusLabel.setText("");
			gui.hit.setEnabled(false);
			gui.stay.setEnabled(false);
			gui.deal.setEnabled(false);
			gui.bet1.setEnabled(true);
			gui.bet2.setEnabled(true);
			gui.bet3.setEnabled(true);
		}

	}

	public static void main(String[] args) {
		new Game();
	}
}
