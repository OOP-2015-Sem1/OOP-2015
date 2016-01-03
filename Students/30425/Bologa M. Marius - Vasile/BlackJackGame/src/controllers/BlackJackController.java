package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
	public static int playerWins = 0;
	public static int dealerWins = 0;
	private static int money;
	private int bet;
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
		BufferedImage b = null;
		try {
			b = ImageIO.read(BlackJackController.class.getClassLoader().getResourceAsStream("back.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backCard = new JLabel(new ImageIcon(b));
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
			setDealerWins();
		} else if (playerHand.getNumberOfCards() == 5) {
			frame.showmsg.setText("You have drawn 5 card without going over 21! You won!");
			gameInProgress = false;
			showHand();
			setPlayerWins();
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
			setPlayerWins();
		} else if (dealerHand.getNumberOfCards() == 5) {
			frame.showmsg.setText("Dealer has drawn 5 cards without going over 21. You lost!");
			showHand();
			setDealerWins();
		} else if ((dealerHand.getBlackjackValue() > playerHand.getBlackjackValue())) {
			frame.showmsg.setText(
					"You lost, " + dealerHand.getBlackjackValue() + " to " + playerHand.getBlackjackValue() + "!");
			showHand();
			setDealerWins();
		} else if (dealerHand.getBlackjackValue() == playerHand.getBlackjackValue()) {
			frame.showmsg.setText("You lost!  Dealer wins on a tie!");
			showHand();
			setDealerWins();
		} else {
			frame.showmsg.setText(
					"You win, " + playerHand.getBlackjackValue() + " to " + dealerHand.getBlackjackValue() + "!");
			showHand();
			setPlayerWins();
		}
		frame.repaint();
	}

	public void newGamePressed() {
		if ((gameInProgress)) {
			frame.showmsg.setText("You still have to finish this game!");
			System.exit(0);
			frame.repaint();
			return;
		} else if (money <= 0) {
			MessageDialogs.optionPane(playerWins, dealerWins);
		}
		frame.playerScore.setText(Integer.toString(playerWins));
		frame.dealerScore.setText(Integer.toString(dealerWins));
		frame.dealerPanel.removeAll();
		frame.playerPanel.removeAll();
		deck = new Deck();
		dealerHand = new BlackJackHand();
		playerHand = new BlackJackHand();
		deck.shuffle();
		getFirstTwoCards(deck, playerHand, dealerHand);
		frame.showmoney.setText("Your money:" + Integer.toString(money));
		setBet(MessageDialogs.queryForBet());
		if (dealerHand.getBlackjackValue() == 21) {
			frame.showmsg.setText("You lost! Dealer has Blackjack!");
			gameInProgress = false;
			showHand();
			setDealerWins();
		} else if (playerHand.getBlackjackValue() == 21) {
			frame.showmsg.setText("You win! You have Blackjack!");
			gameInProgress = false;
			showHand();
			setPlayerWins();
		} else {
			frame.showmsg.setText("You have " + playerHand.getBlackjackValue() + ".  Hit or stand?");
			gameInProgress = true;
		}
		frame.repaint();
	}

	public void setPlayerWins() {
		playerWins++;
		money = money + getBet();
	}

	public void setDealerWins() {
		dealerWins++;
		money = money - getBet();
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public static final int getMoney() {
		return money;
	}

	public static final void setMoney(int money) {
		BlackJackController.money = money;
	}

}