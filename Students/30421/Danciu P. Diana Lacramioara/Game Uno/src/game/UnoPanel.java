package game;

import player.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

import options.Options;

@SuppressWarnings("serial")
public final class UnoPanel extends JPanel {

	private final UnoGame game;

	public static ArrayList<String> played = new ArrayList<>();

	private static final int CARD_H_SPACE = 50;
	private static final int CARD_V_SPACE = 25;
	private static final int CARD_WIDTH = 65;
	private static final int CARD_HEIGHT = 100;

	private final javax.swing.JFrame parentFrame;
	public static DefaultTableModel model;
	private javax.swing.JButton unoButton;
	private javax.swing.JButton colorButton;

	private final UNOMouseListener humanListener;

	public static Timer computerTimer;
	private final Timer unoTimer;

	private String statusMessage = "";
	private int statusMsgTime = 1;
	private final Rectangle[][] screen = new Rectangle[3][];

	private static final Color[] colorCode = { (new Color(204, 0, 0)), (new Color(0, 102, 0)), (new Color(0, 0, 204)),
			(new Color(255, 204, 0)), (new Color(0, 0, 0)) };

	private final Image[][] card = new Image[5][];
	private final Image[] directions = new Image[2];
	private final Image arrow = Toolkit.getDefaultToolkit()
			.createImage(getClass().getClassLoader().getResource("\\resources\\flecha.png"));

	public UnoPanel(JFrame parentFrame, UnoGame game, Options options) {
		this.parentFrame = parentFrame;

		initComponents();

		this.game = game;

		screen[0] = getCardPositions(game.getNumberPlayers() - 1, 0, 3);
		screen[1] = getCardPositions(2, 1, 3);
		screen[2] = getCardPositions(game.getDeck(0).getSize(), 2, 3);

		humanListener = new UNOMouseListener();

		this.addMouseListener(humanListener);

		computerTimer = new Timer(2000, new UNOAIListener(new AIEasyPlayer(game)));

		unoTimer = new Timer(4000, new UNOTimerEffect());
		unoTimer.setRepeats(false);

		newGame(options);

		UnoPanelWindow splash = new UnoPanelWindow();
		splash.setVisible(true);
		loadCards();
		splash.setVisible(false);

		repaint();
	}

	public void newGame(Options options) {
		game.initGame(options);

		computerTimer.stop();

		screen[0] = getCardPositions(game.getNumberPlayers() - 1, 0, 3);
		screen[1] = getCardPositions(2, 1, 3);
		screen[2] = getCardPositions(game.getDeck(0).getSize(), 2, 3);

		repaint();
	}

	private void initComponents() {

		unoButton = new JButton();
		colorButton = new JButton();
		setLayout(null);

		this.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		setBackground(new Color(0, 153, 51));
		setPreferredSize(new Dimension(610, 450));
		setMinimumSize(new Dimension(610, 450));

		unoButton.setText("UNO!");
		unoButton.setBounds(10, 160, 70, 30);
		add(unoButton);

		colorButton.setText("");
		colorButton.setEnabled(false);
		colorButton.setBounds(27, 200, 30, 30);
		colorButton.setBackground(Color.black);
		colorButton.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		add(colorButton);

		unoButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				unoButtonActionPerformed(evt);
			}
		});

		setBorder(new SoftBevelBorder(EtchedBorder.RAISED, Color.gray, Color.lightGray, Color.black, Color.darkGray));
		setBackground(new Color(0, 153, 51));
		setPreferredSize(new Dimension(650, 450));
		setMinimumSize(new Dimension(650, 450));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		setBackground(new java.awt.Color(0, 153, 51));

		screen[0] = getCardPositions(game.getNumberPlayers() - 1, 0, 3);
		screen[1] = getCardPositions(2, 1, 3);
		screen[2] = getCardPositions(game.getDeck(0).getSize(), 2, 3);

		colorButton.setBackground(colorCode[game.getCurrentColor()]);

		drawDirection(g, game.getDirection());

		// other players
		for (int i = 1; i != game.getNumberPlayers(); i += 1) {
			drawCard(g, null, i - 1, 0, "Player " + (i + 1) + " (" + game.getDeck(i).getSize() + ")",
					(game.getCurrentPlayer() == i));
		}

		int numCards = game.getDeck(UnoGame.DECK).getSize();

		if (numCards < 10)
			drawPile(g, null, 0, 1, 1);
		else if (numCards >= 10 && numCards < 20)
			drawPile(g, null, 0, 1, 2);
		else if (numCards >= 20 && numCards < 30)
			drawPile(g, null, 0, 1, 3);
		else
			drawPile(g, null, 0, 1, 4);

		numCards = game.getDeck(UnoGame.DISCARD).getSize();

		if (numCards < 10)
			drawCard(g, game.getLastCardPlayed(), 1, 1);
		else if (numCards < 20)
			drawPile(g, game.getLastCardPlayed(), 1, 1, 2);
		else if (numCards < 30)
			drawPile(g, game.getLastCardPlayed(), 1, 1, 3);
		else
			drawPile(g, game.getLastCardPlayed(), 1, 1, 4);

		UnoDeck deck = game.getDeck(0);

		// my cards
		if (deck.getSize() >= 1)
			drawCard(g, deck.showCard(0), 0, 2, "Player 1 (" + deck.getSize() + ")", (game.getCurrentPlayer() == 0));
		else
			drawCard(g, null, 0, 2, "Player 1 (0)", (game.getCurrentPlayer() == 0));

		if (deck.getSize() > 1) {
			for (int i = 1; i != deck.getSize(); i += 1) {
				drawCard(g, deck.showCard(i), i, 2);
			}
		}
		drawStatusMessage(g);
	}

	private void unoButtonActionPerformed(ActionEvent evt) {
		if (!unoTimer.isRunning())
			return;
		computerTimer.stop();

		new JOptionPane("Player 1 called Uno!", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION)
				.createDialog(this, "Someone has called Uno!").setVisible(true);
		unoTimer.stop();

		repaint();

		computerTimer.start();
	}

	private void drawCard(Graphics g, Card card, int cardPos, int cardLevel) {
		Image i = toImage(card);

		Point p = screen[cardLevel][cardPos].getLocation();

		g.drawImage(i, p.x, p.y, this);
	}

	private void drawCard(Graphics g, Card card, int cardPos, int cardLevel, String message, boolean isHighlighted) {
		Image i = toImage(card);

		Point p = screen[cardLevel][cardPos].getLocation();

		g.drawImage(i, p.x, p.y, this);

		Graphics2D g2 = (Graphics2D) g;

		Font font = new Font("Helvetica", Font.BOLD, 12);
		g2.setColor(isHighlighted ? Color.white : Color.white);
		g2.setFont(font);

		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(message, context);

		Point p2 = new Point(p.x + (int) (CARD_WIDTH - bounds.getWidth()) / 2, p.y - 8);

		g2.drawString(message, p2.x, p2.y);

		if (isHighlighted) {
			p2.x -= 30;
			if (p2.x < 0)
				p2.x = 0;

			g2.drawImage(arrow, p2.x, p2.y, this);
		}
	}

	private void drawPile(Graphics g, Card card, int cardPos, int cardLevel, int height) {
		Image i = toImage(card);

		Point p = screen[cardLevel][cardPos].getLocation();

		int x = p.x;
		int y = p.y;

		for (int j = 0; j != height; j += 1) {
			g.drawImage(i, x, y, this);
			x += 3;
			y -= 3;
		}
	}

	private void setStatusMessage(String message, int time) {
		statusMessage = message;
		statusMsgTime = time;
	}

	private void drawStatusMessage(Graphics g) {

		Font font = new Font("Helvetica", Font.BOLD, 12);
		g.setColor(Color.white);
		g.setFont(font);

		if (statusMsgTime != 0) {
			g.drawString(statusMessage, 12, this.getHeight() - 10);
			statusMsgTime--;
		}
	}

	private void drawDirection(Graphics g, int direction) {
		int x = (getWidth() - directions[0].getWidth(null)) / 2 + 7;
		int y = (getHeight() - directions[0].getHeight(null)) / 2 - 10;

		if (direction == Turn.CLOCKWISE) {
			g.drawImage(directions[0], x, y, this);
		} else {
			g.drawImage(directions[1], x, y, this);
		}
	}

	private Rectangle[] getCardPositions(int cardsWide, int cardLevel, int cardMaxLevels) {
		if (cardsWide == 0)
			cardsWide = 1;

		Rectangle[] rect = new Rectangle[cardsWide];

		int xSpace = fitCardSpace(CARD_WIDTH, CARD_H_SPACE, parentFrame.getWidth(), cardsWide, 10);
		int ySpace = fitCardSpace(CARD_HEIGHT, CARD_V_SPACE, parentFrame.getHeight(), cardMaxLevels, 30);

		int x = (parentFrame.getWidth() - (cardsWide - 1) * xSpace - cardsWide * CARD_WIDTH) / 2;
		int y = (parentFrame.getHeight() - cardMaxLevels * ySpace - cardMaxLevels * CARD_HEIGHT) / 2
				+ cardLevel * ySpace + cardLevel * CARD_HEIGHT - 10;

		for (int i = 0; i != rect.length; i += 1) {
			rect[i] = new Rectangle(x, y, CARD_WIDTH, CARD_HEIGHT);

			x += (xSpace + CARD_WIDTH);
		}

		return rect;
	}

	private int fitCardSpace(int cardWidth, int cardSpace, int maxWidth, int cardsWide, int step) {

		boolean outOfScreen = maxWidth - (cardsWide - 1) * cardSpace - cardsWide * cardWidth < 10;

		while (outOfScreen) {
			cardSpace -= step;
			outOfScreen = maxWidth - (cardsWide - 1) * cardSpace - cardsWide * cardWidth < 10;
		}

		return cardSpace;
	}

	private Image toImage(Card card) {
		if (card == null)
			return this.card[4][0];

		else if (!card.isWild())
			return this.card[card.getColor()][card.getValue()];

		else if (card.isWild() && !card.isWildFour())
			return this.card[4][1];

		else if (card.isWildFour())
			return this.card[4][2];

		else
			return this.card[card.getColor()][card.getValue()];
	}

	private int chooseColor() {
		ColorToChoose ucc = new ColorToChoose(parentFrame, true);
		return ucc.getColor();
	}

	private void announceWinner(int winner) {
		String s = "Player " + winner + " has won this match !";
		new JOptionPane(s, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION).createDialog(this, "Someone has won")
				.setVisible(true);
		setStatusMessage(s, 2);
		setStatusMessage("Game is finished !", 1);

		computerTimer.stop();

		repaint();
	}

	private void loadCards() {

		MediaTracker tracker = new MediaTracker(new Container());

		int id = 0;

		String[] color = { "red", "green", "blue", "yellow" };

		Toolkit tk = Toolkit.getDefaultToolkit();

		for (int i = 0; i != 5; i += 1) {
			card[i] = new Image[13];
		}
		card[4] = new Image[3];

		for (int i = 0; i != 4; i += 1) {
			for (int j = 0; j != 13; j += 1) {
				card[i][j] = tk.createImage(
						getClass().getClassLoader().getResource("\\resources\\" + color[i] + " " + j + ".png"));
				tracker.addImage(card[i][j], id++);
			}

		}

		card[4][0] = tk.createImage(getClass().getClassLoader().getResource("\\resources\\back.png"));
		card[4][1] = tk.createImage(getClass().getClassLoader().getResource("\\resources\\cardW.png"));
		card[4][2] = tk.createImage(getClass().getClassLoader().getResource("\\resources\\cardW+4.png"));
		directions[0] = tk.createImage(getClass().getClassLoader().getResource("\\resources\\cw.png"));
		directions[1] = tk.createImage(getClass().getClassLoader().getResource("\\resources\\ccw.png"));

		tracker.addImage(card[4][0], id++);
		tracker.addImage(card[4][1], id++);
		tracker.addImage(card[4][2], id++);
		tracker.addImage(directions[0], id++);
		tracker.addImage(directions[1], id++);

		tracker.addImage(arrow, id++);

		try {
			tracker.waitForAll();
		} catch (Exception e) {
			System.out.println("Ooops ! Loading card images ...");
			System.exit(1);
		}
	}

	private String toColor(int color) {
		switch (color) {
		case Card.RED:
			return "Red";
		case Card.YELLOW:
			return "Yellow";
		case Card.GREEN:
			return "Green";
		case Card.BLUE:
			return "Blue";
		}
		return "ERROR";
	}

	private class UNOMouseListener extends MouseAdapter {

		private int detectCardSelected(MouseEvent evt) {
			Point p = evt.getPoint();

			if (screen[1][0].contains(p))
				return game.getDeck(0).getSize();

			for (int i = screen[2].length - 1; i != -1; i -= 1) {
				if (screen[2][i].contains(p))
					return i;
			}

			return -1;
		}

		@Override
		public void mousePressed(MouseEvent evt) {

			int cardToPlay = detectCardSelected(evt);

			if (cardToPlay == GameMessages.CARD_NOT_VALID)
				return;
			int result = game.playCard(0, cardToPlay);

			if (!(played.isEmpty())) {
				int card = game.getLastCardPlayed().getValue();
				if (card == Card.REVERSE)
					setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a reverse", 1);
				if (card == Card.SKIP)
					setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a skip", 1);
				if (card == Card.DRAW_TWO)
					setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a draw two", 1);
				if (card == Card.WILD)
					setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a wild", 1);
				if (card == Card.WILD_FOUR)
					setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a wild draw 4", 1);
			}

			if (result >= 28) {
				result -= 35;
				announceWinner(game.getLastPlayer() + 1);
				return;
			} else if (result >= 13) {
				result -= 20;
				activateUnoTimer();
			}

			switch (result) {
			case GameMessages.CARD_NOT_VALID:
				setStatusMessage("That's not a valid card!", 1);
				break;
			case GameMessages.SELECT_COLOR_FIRST:
				throw new RuntimeException("This must not happen !");
			case GameMessages.DECK_EMPTY:
				setStatusMessage("You can't draw more cards!", 1);
				break;
			case GameMessages.DREW_AND_VALID:
				setStatusMessage("You drew a card you can play !", 1);
				break;
			case GameMessages.DREW_AND_NO_VALID:
				setStatusMessage("You drew a card, but you can't use it !", 1);
				break;
			case GameMessages.PLAYED_WILD:
				setStatusMessage("You've played a Wild card", 1);
			case GameMessages.PLAYED_WILD_4:
				game.setColor(chooseColor());
				setStatusMessage("The color chosen was: " + toColor(game.getCurrentColor()), 1);
				break;
			case GameMessages.PLAYED_ZERO:
				setStatusMessage("You've passed your hand to the next player !", 1);
				break;
			case GameMessages.PLAYED_WILD_4N:
				game.setColor(chooseColor());
			}

			repaint();

			if (game.getCurrentPlayer() != 0) {
				computerTimer.start();
			}
		}
	}

	private class UNOAIListener implements ActionListener {

		public UNOAIListener(AIPlayer player) {
			this.player = player;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {

			UnoDeck myDeck = game.getCurrentDeck();
			int cardToPlay = player.playMyTurn(myDeck);
			int result = game.playCard(game.getCurrentPlayer(), cardToPlay);
			int card = game.getLastCardPlayed().getValue();

			if (card == Card.REVERSE)
				setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a reverse", 1);
			if (card == Card.SKIP)
				setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a skip", 1);
			if (card == Card.DRAW_TWO)
				setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a draw two", 1);
			if (card == Card.WILD)
				setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a wild", 1);
			if (card == Card.WILD_FOUR)
				setStatusMessage("Player " + (game.getLastPlayer() + 1) + " played a wild draw 4", 1);

			if (result > 28) {
				result -= 35;
				announceWinner(game.getLastPlayer() + 1);
				return;
			} else if (result > 13) {
				result -= 20;
				new JOptionPane("Player " + (game.getLastPlayer() + 1) + " called Uno!", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.DEFAULT_OPTION).createDialog(parentFrame, "Someone has called Uno!")
								.setVisible(true);
				setStatusMessage("Player " + (game.getLastPlayer() + 1) + " called Uno!", 1);
			}

			switch (result) {
			case GameMessages.GAME_FINISHED:
				setStatusMessage("Game's finished.", 1);
			case GameMessages.CARD_NOT_VALID:
				setStatusMessage("That's not a valid card.", 1);
				break;
			case GameMessages.SELECT_COLOR_FIRST:
				throw new RuntimeException("This must not happen !");
			case GameMessages.DECK_EMPTY:
				setStatusMessage("Player " + (game.getCurrentPlayer() + 1) + " can't draw more cards !", 1);
				break;
			case GameMessages.DREW_AND_VALID:
				setStatusMessage("Player " + (game.getCurrentPlayer() + 1) + " drew a card he can play !", 1);
				break;
			case GameMessages.DREW_AND_NO_VALID:
				setStatusMessage("Player " + (game.getCurrentPlayer() + 1) + " drew a card, but he can't use it !", 1);
				break;
			case GameMessages.PLAYED_WILD:
				setStatusMessage("You've played a Wild card", 1);
			case GameMessages.PLAYED_WILD_4:
				game.setColor(player.chooseColor(game.getCurrentDeck()));
				setStatusMessage("The color chosen was: " + toColor(game.getCurrentColor()), 1);
				break;
			case GameMessages.PLAYED_ZERO:
				setStatusMessage(
						"Player " + (game.getCurrentPlayer() + 1) + " has passed your hand to the next player !", 1);
				break;
			case GameMessages.PLAYED_WILD_4N:
				game.setColor(player.chooseColor(game.getCurrentDeck()));
			}

			repaint();

			if (game.getCurrentPlayer() == 0)
				computerTimer.stop();
		}

		private final AIPlayer player;
	}

	private void activateUnoTimer() {
		computerTimer.stop();
		unoTimer.start();
	}

	private class UNOTimerEffect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			setStatusMessage("Someone called Uno for you. You draw 6 cards.", 2);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

			game.restartHand(0);
			repaint();

			computerTimer.start();
		}
	}
}