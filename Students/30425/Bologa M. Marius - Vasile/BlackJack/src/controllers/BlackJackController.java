package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import models.BlackJackHand;
import models.Deck;
import view.BlackJackFrame;

public class BlackJackController {
	Deck deck;
	BlackJackHand dealerHand;
	BlackJackHand playerHand;
	JLabel playercard1 = new JLabel();
	JLabel playercard2 = new JLabel();
	JLabel playerCardHit = new JLabel();
	JLabel dealerCardHit = new JLabel();
	JLabel dealercard1 = new JLabel();
	JLabel dealercard2 = new JLabel();
	JLabel backCard = new JLabel();

	boolean gameInProgress;
	protected BlackJackFrame frame;

	public BlackJackController(final BlackJackFrame frame) {
		super();
		this.frame = frame;
		frame.setHitButtonActionListener(new HitButtonActionListener());
		frame.setStandButtonActionListener(new StandButtonActionListener());
		frame.setNewGameButtonActionListener(new NewGameButtonActionListener());
	}

	public class HitButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			hitPressed();
		}
	}

	public class StandButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			standPressed();

		}
	}

	public class NewGameButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			newGamePressed();

		}
	}

	public void showHand() {
		backCard.setVisible(false);
		dealercard1.setVisible(true);
	}

	public static int queryForNumberOfDecks() {
		String decks = JOptionPane.showInputDialog("Number of decks(1-8):");
		Integer decksNumber;
		try {
			decksNumber = Integer.parseInt(decks);
			if (decksNumber < 1 || decksNumber > 8)
				decksNumber = 1;
		} catch (NumberFormatException e) {
			decksNumber = 1;
		}
		System.out.println(decksNumber * 52);
		return decksNumber;
	}

	public void getFirstTwoCards(Deck d, BlackJackHand p, BlackJackHand dl) {
		dl.addCard(d.dealCard());
		dl.addCard(d.dealCard());
		p.addCard(d.dealCard());
		p.addCard(d.dealCard());
		BufferedImage imgd1 = dl.getCard(0).getImage();
		BufferedImage imgd2 = dl.getCard(1).getImage();
		BufferedImage imgp1 = p.getCard(0).getImage();
		BufferedImage imgp2 = p.getCard(1).getImage();
		dealercard1 = new JLabel(new ImageIcon(imgd1));
		dealercard2 = new JLabel(new ImageIcon(imgd2));
		playercard1 = new JLabel(new ImageIcon(imgp1));
		playercard2 = new JLabel(new ImageIcon(imgp2));
		backCard = new JLabel(new ImageIcon("C:/Users/Bolo/JavaSummer/BlackJack/src/images/back.png"));
		frame.dealerPanel.add(backCard);
		frame.dealerPanel.add(dealercard1);
		dealercard1.setVisible(false);
		frame.dealerPanel.add(dealercard2);
		frame.playerPanel.add(playercard1);
		frame.playerPanel.add(playercard2);
		frame.cardPanel.add(frame.dealerPanel);
		frame.cardPanel.add(frame.playerPanel);
	}

	public void hitPressed() {
		if (gameInProgress == false) {
			frame.showmsg.setText("Click the button -New Game- to play BlackJack!");
			frame.repaint();
			return;
		}
		playerHand.addCard(deck.dealCard());
		int nrOfCards = playerHand.getNumberOfCards();
		BufferedImage imgHit = playerHand.getCard(nrOfCards - 1).getImage();
		playerCardHit = new JLabel(new ImageIcon(imgHit));
		frame.playerPanel.add(playerCardHit);
		if (playerHand.getBlackjackValue() > 21) {
			frame.showmsg.setText("You lost! You were busted with " + playerHand.getBlackjackValue() + "!");
			gameInProgress = false;
			showHand();
		} else if (playerHand.getNumberOfCards() == 5) {
			frame.showmsg.setText("You have drawn 5 card without going over 21! You won!");
			gameInProgress = false;
			showHand();
		} else {
			frame.showmsg.setText("You have " + playerHand.getBlackjackValue() + " points.  Hit or Stand?");
		}
		frame.repaint();
	}

	public void standPressed() {
		if (gameInProgress == false) {
			frame.showmsg.setText("Click the button -New Game- to play BlackJack!");
			frame.repaint();
			return;
		}
		gameInProgress = false;
		while ((dealerHand.getBlackjackValue() <= 16) && (dealerHand.getNumberOfCards() < 5)) {
			dealerHand.addCard(deck.dealCard());
			int nrOfCardsD = dealerHand.getNumberOfCards();
			BufferedImage imgHit = dealerHand.getCard(nrOfCardsD - 1).getImage();
			dealerCardHit = new JLabel(new ImageIcon(imgHit));
			frame.dealerPanel.add(dealerCardHit);
		}
		if (dealerHand.getBlackjackValue() > 21) {
			frame.showmsg.setText("You win!  Dealer was busted with " + dealerHand.getBlackjackValue());
			showHand();
		} else if (dealerHand.getNumberOfCards() == 5) {
			frame.showmsg.setText("Dealer has drawn 5 cards without going over 21. You lost!");
			showHand();
		} else if ((dealerHand.getBlackjackValue() > playerHand.getBlackjackValue())) {
			frame.showmsg.setText(
					"You lost, " + dealerHand.getBlackjackValue() + " to " + playerHand.getBlackjackValue() + "!");
			showHand();
		} else if (dealerHand.getBlackjackValue() == playerHand.getBlackjackValue()) {
			frame.showmsg.setText("You lost!  Dealer wins on a tie!");
			showHand();
		} else {
			frame.showmsg.setText(
					"You win, " + playerHand.getBlackjackValue() + " to " + dealerHand.getBlackjackValue() + "!");
			showHand();
		}
		frame.repaint();
	}

	public void newGamePressed() {
		if (gameInProgress) {
			frame.showmsg.setText("You still have to finish this game!");
			System.exit(0);
			frame.repaint();
			return;
		}
		frame.dealerPanel.removeAll();
		frame.playerPanel.removeAll();
		deck = new Deck();
		dealerHand = new BlackJackHand();
		playerHand = new BlackJackHand();
		deck.shuffle();
		getFirstTwoCards(deck, playerHand, dealerHand);
		if (dealerHand.getBlackjackValue() == 21) {
			frame.showmsg.setText("You lost! Dealer has Blackjack!");
			gameInProgress = false;
			showHand();
		} else if (playerHand.getBlackjackValue() == 21) {
			frame.showmsg.setText("You win! You have Blackjack!");
			gameInProgress = false;
			showHand();
		} else {
			frame.showmsg.setText("You have " + playerHand.getBlackjackValue() + ".  Hit or stand?");
			gameInProgress = true;
		}
		frame.repaint();
	}
}
