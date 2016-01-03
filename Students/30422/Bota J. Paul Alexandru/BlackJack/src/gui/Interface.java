package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import game.Bet;
import game.Card;
import game.Deck;
import game.HandValue;

class Interface extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3795079669270762653L;
	private Deck deck;
	private HandValue dealerHand;
	private HandValue yourHand;
	private HandlerClass handler;
	private boolean gameStatus;
	private boolean win;
	private boolean lose;
	private boolean canSurrender;
	private int yourMoney;
	private int bet;
	private String text;

	Interface() {
		String twoLines;
		setLayout(null);
		JButton hit = new JButton("HIT");
		JButton stand = new JButton("STAND");
		twoLines = "DOUBLE\nDOWN";
		JButton dd = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
		twoLines = "PLAY\nAGAIN";
		JButton pa = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");

		JButton leave = new JButton("LEAVE");
		JButton surrender = new JButton("Surrender");

		add(hit);
		add(stand);
		add(pa);
		add(dd);
		add(leave);
		add(surrender);

		hit.setBounds(700, 400, 100, 60);
		stand.setBounds(840, 400, 100, 60);
		dd.setBounds(700, 490, 100, 60);
		surrender.setBounds(840, 490, 100, 60);
		pa.setBounds(700, 580, 100, 60);
		leave.setBounds(840, 580, 100, 60);

		yourMoney = 1000;

		canSurrender = true;
		gameStatus = true;
		win = true;
		lose = true;

		handler = new HandlerClass();
		hit.addActionListener(handler);
		stand.addActionListener(handler);
		dd.addActionListener(handler);
		pa.addActionListener(handler);
		leave.addActionListener(handler);
		surrender.addActionListener(handler);

		hit.setActionCommand("Hit");
		stand.setActionCommand("Stand");
		dd.setActionCommand("Double Down");
		pa.setActionCommand("Play Again");
		leave.setActionCommand("Leave");
		surrender.setActionCommand("Surrender");

		bet=Bet.newBet(yourMoney);
		showCards();
		isBlackJack();

	}

	public void paintComponent(Graphics g) {
		int refreshMoney;
		super.paintComponent(g);
		this.setBackground(new Color(0, 120, 0));
		g.setFont(new Font("SansSerif", Font.PLAIN, 20));
		g.drawString(text, 10, getSize().height - 10);
		g.drawString("Your bet: " + bet, 700, 200);
		refreshMoney = yourMoney - bet;
		g.drawString("Your money: " + refreshMoney, 700, 300);

		if (gameStatus)
			drawCard(g, null, 70, 340);
		else
			drawCard(g, dealerHand.getCard(0), 70, 340);
		for (int i = 1; i < dealerHand.getCardsNumber(); i++)
			drawCard(g, dealerHand.getCard(i), 70 + i * 110, 340);

		for (int i = 0; i < yourHand.getCardsNumber(); i++)
			drawCard(g, yourHand.getCard(i), 70 + i * 110, 530);

	}

	void drawCard(Graphics g, Card card, int x, int y) {
		BufferedImage image = null;

		if (card == null) {
			try {
				image = ImageIO.read(new File("resources/back.jpg"));
			} catch (IOException e) {
			}

			g.drawImage(image, x, y, 100, 120, null);
		} else {

			try {
				image = ImageIO.read(new File("resources/"+card.getValueAsString() + "_of_" + card.getSuitAsString() + ".png"));
			} catch (IOException e) {
			}
			if (image != null) {
				g.drawImage(image, x, y, 100, 120, null);

			}
		}
	}

	private class HandlerClass implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String action = event.getActionCommand();
			if (action == "Hit") {
				pressHit();
			}
			if (action == "Stand") {
				pressStand();
			}
			if (action == "Play Again") {
				pressPlayAgain();
			}
			if (action == "Double Down") {
				pressDoubleDown();
			}
			if (action == "Leave") {
				System.exit(0);
			}
			if (action == "Surrender") {
				pressSurrender();
			}
		}
	}

	

	private void showCards() {

		deck = new Deck();
		dealerHand = new HandValue();
		yourHand = new HandValue();
		deck.shuffle();
		dealerHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());
		yourHand.addCard(deck.dealCard());
		yourHand.addCard(deck.dealCard());
		gameStatus = true;
		repaint();
	}

	private void isBlackJack() {

		if (gameStatus == false) {
			repaint();
			return;
		}

		if (dealerHand.getHandValue() == 21) {
			gameStatus = false;
			win = false;
			lose = true;
			JOptionPane.showMessageDialog(null, "You lose! Dealer has BlackJack.");
		} else if (yourHand.getHandValue() == 21) {
			gameStatus = false;
			lose = false;
			win = true;
			JOptionPane.showMessageDialog(null, "You win! You have BlackJack.");
		} else {
			text = "You have " + yourHand.getHandValue() + ". " + "You can Hit, Stand, Double down or Surrender.";

		}
		yourMoney=Bet.currentMoney(win, lose,yourMoney,bet);
		text=Bet.message(win, lose,text);
		bet=Bet.refreshBet(win, lose);
		repaint();
	}

	private void pressHit() {
		if (gameStatus == false) {
			text = "Click \"Play Again\" to play again.";
			repaint();
			return;
		}

		canSurrender = false;
		yourHand.addCard(deck.dealCard());
		if (yourHand.getHandValue() > 21) {
			repaint();
			JOptionPane.showMessageDialog(null, "You lose! You have busted.");
			win = false;
			lose = true;
			gameStatus = false;
			yourMoney=Bet.currentMoney(win, lose,yourMoney,bet);
			text=Bet.message(win, lose,text);
			bet=Bet.refreshBet(win, lose);
		} else
			text = "You have " + yourHand.getHandValue() + ".  Hit, Double down or stand?";
		repaint();
	}

	private void pressStand() {
		if (gameStatus == false) {
			text = "Click \"Play Again\" to play again.";
			repaint();
			return;
		}
		while (dealerHand.getHandValue() <= 16)

			dealerHand.addCard(deck.dealCard());

		gameStatus = false;
		repaint();
		if (dealerHand.getHandValue() > 21) {
			JOptionPane.showMessageDialog(null, "You win! Dealer has busted.");
			win = true;
			lose = false;
		} else if (dealerHand.getHandValue() > yourHand.getHandValue()) {
			JOptionPane.showMessageDialog(null, "You lose! Dealer has " + dealerHand.getHandValue() + ".");
			win = false;
			lose = true;
		} else if (dealerHand.getHandValue() == yourHand.getHandValue()) {
			JOptionPane.showMessageDialog(null, "You lose! It's a tie. Dealer has " + dealerHand.getHandValue() + ".");
			win = false;
			lose = true;
		} else {
			JOptionPane.showMessageDialog(null, "You win! Dealer has " + dealerHand.getHandValue() + ".");
			lose = false;
			win = true;
		}
		yourMoney=Bet.currentMoney(win, lose,yourMoney,bet);
		text=Bet.message(win, lose,text);
		bet=Bet.refreshBet(win, lose);
		repaint();
	}

	

	private void pressSurrender() {
		if (gameStatus == false) {
			text = "Click \"Play Again\" to play again.";
			repaint();
			return;
		}
		if (canSurrender == false)
			text = "You can't surrender. Only in first move of the turn you can use this option.";
		if (canSurrender == true) {
			gameStatus = false;
			yourMoney = yourMoney - (bet / 2);
			bet = 0;

		}
		repaint();
	}

	private void pressPlayAgain() {
		if (gameStatus == false)
			text = "You have to finish this turn!";
		if (gameStatus == false) {
			gameStatus = true;
			win = true;
			lose = true;
			canSurrender = true;
			bet=Bet.newBet(yourMoney);
			showCards();
			isBlackJack();
		}
	}

	private void pressDoubleDown() {
		if (yourMoney >= bet * 2) {
			bet = bet * 2;
			pressHit();
			pressStand();
		} else
			JOptionPane.showMessageDialog(null, "You don't have enough money for double down");

	}

}
