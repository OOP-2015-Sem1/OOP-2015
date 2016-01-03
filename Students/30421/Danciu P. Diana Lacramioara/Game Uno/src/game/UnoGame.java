package game;

import java.util.*;
import options.Options;

public final class UnoGame implements GameMessages {

	public static final int DISCARD = 70;
	public static final int DECK = 72;
	private UnoDeck deck;
	public Vector<UnoDeck> players;
	private UnoDeck discard;
	private int startingCards;
	private int numberOfPlayers;
	public ArrayList<Object> scoreTable;
	private int playerRetired;
	public Turn turn;
	private int lastTurn;
	private int currentColor;
	private int currentCard;
	private int currentCardsToDraw;
	private boolean colorSet;
	private boolean finished;
	private boolean withWildWin;
	private boolean withZeroRule;

	// CONSTRUCTOR
	public UnoGame(Options options) {
		initGame(options);
	}

	public void initGame(Options options) {

		startingCards = options.getNumberCards();
		numberOfPlayers = options.getNumberPlayers();
		playerRetired = -1;

		deck = new UnoDeck(UnoDeck.FULL);
		deck.shuffleDeck();
		players = new Vector<UnoDeck>();

		for (int i = 0; i != options.getNumberPlayers(); i++) {
			players.add(new UnoDeck(UnoDeck.EMPTY));

			for (int j = 0; j != options.getNumberCards(); j++) {
				((UnoDeck) players.get(i)).insertCard(deck.getCard(0));
			}
			((UnoDeck) players.get(i)).sort();
		}

		discard = new UnoDeck(UnoDeck.EMPTY);

		do {
			discard.insertCard(deck.getCard(0));
			currentColor = getLastCardPlayed().getColor();
			currentCard = getLastCardPlayed().getValue();
		} while (!discard.showCard(0).isNumber());

		currentCardsToDraw = 0;
		turn = new Turn((new Random().nextInt(options.getNumberPlayers())), options.getNumberPlayers(), 1);
		lastTurn = turn.showCurrentTurn();
		colorSet = true;
		finished = false;

		withWildWin = options.withWildWin();
		withZeroRule = options.withZeroRule();
	}

	private boolean existsValidPlay() {
		for (int i = 0; i != ((UnoDeck) players.get(turn.showCurrentTurn())).getSize(); i += 1) {
			if (isValidCard(i))
				return true;
		}

		return false;
	}

	private void playCard(int numCard) {
		if (!isValidCard(numCard))
			throw new IllegalArgumentException("This is not a valid card.");
		discard.insertCard(((UnoDeck) players.get(turn.showCurrentTurn())).getCard(numCard));
		currentColor = getLastCardPlayed().getColor();
		currentCard = getLastCardPlayed().getValue();

	}

	private int drawCard(int times) {

		if (times == 1 && currentCardsToDraw != 0)
			return -3;

		switch (times) {
		case 2:
			currentCardsToDraw += times;
			if (((UnoDeck) players.get(turn.showNextTurn())).exists(new Card(Card.NULL, Card.DRAW_TWO))
					|| ((UnoDeck) players.get(turn.showNextTurn())).exists(new Card(Card.NULL, Card.WILD_FOUR))) {
				return -1;
			}
			break;
		case 4:
			currentCardsToDraw += times;
			if (((UnoDeck) players.get(turn.showNextTurn())).exists(new Card(Card.NULL, Card.WILD_FOUR))) {
				return -2;
			}
			break;
		default:
			currentCardsToDraw += times;
			break;
		}

		if (times != 1)
			turn.nextTurn();

		int cardsDrawn = currentCardsToDraw;

		if (getDeck(DECK).isEmpty()) {
			currentCardsToDraw = 0;
			return -4;
		}

		for (int i = 0; i != currentCardsToDraw; i += 1) {

			((UnoDeck) players.get(turn.showCurrentTurn())).insertCard(deck.getCard(0));

			if (deck.getSize() == 0) {
				if (discard.isEmpty()) {
					cardsDrawn = i + 1;
					break;
				}

				while (!discard.isEmpty()) {
					deck.insertCardAtLast(discard.getCard(0));
				}

				discard.insertCard(deck.getCard(deck.getSize() - 1));

				deck.shuffleDeck();
			}
		}

		currentCardsToDraw = 0;
		((UnoDeck) players.get(turn.showCurrentTurn())).sort();

		return cardsDrawn;
	}

	public void restartHand(int forPlayer) {
		if (((UnoDeck) players.get(forPlayer)).getSize() > 1)
			return;

		for (int i = 0; i != 6; i += 1) {
			((UnoDeck) players.get(forPlayer)).insertCard(deck.getCard(0));

			if (deck.getSize() == 0) {
				if (discard.isEmpty()) {
					return;
				}

				while (!discard.isEmpty()) {
					deck.insertCardAtLast(discard.getCard(0));
				}

				discard.insertCard(deck.getCard(deck.getSize() - 1));

				deck.shuffleDeck();
			}
		}
	}

	public UnoDeck getDeck(int numDeck) {

		try {
			return ((UnoDeck) players.get(numDeck));
		} catch (Exception e) {
			// do nothing
		}

		switch (numDeck) {
		case DECK:
			return deck;

		case DISCARD:
			return discard;

		default:
			throw new RuntimeException("invalid deck");
		}
	}

	public UnoDeck getCurrentDeck() {
		return ((UnoDeck) players.get(turn.showCurrentTurn()));
	}

	public Card getLastCardPlayed() {
		return discard.showCard(0);
	}

	public int getDirection() {
		return turn.getDirection();
	}

	public int getCurrentColor() {
		return currentColor;
	}

	public int getNumberPlayers() {
		return players.size();
	}

	public boolean withWildWin() {
		return withWildWin;
	}

	public boolean withZeroRule() {
		return withZeroRule;
	}

	public boolean retirePlayer(int playerNumber) {
		if (players.size() == 1)
			return false;

		playerRetired = playerNumber;

		while (((UnoDeck) players.get(playerNumber)).getSize() != 0)
			deck.insertCardAtLast(((UnoDeck) players.get(playerNumber)).getCard(0));

		deck.shuffleDeck();

		players.remove(playerNumber);

		turn.retirePlayer();
		return true;
	}

	public int addNewPlayer() {
		if (players.size() == numberOfPlayers)
			return -1;

		players.add(new UnoDeck(UnoDeck.EMPTY));

		if (deck.getSize() < startingCards) {
			if (discard.getSize() + deck.getSize() - 1 < startingCards) {
				return -2;
			}

			while (!discard.isEmpty()) {
				deck.insertCardAtLast(discard.getCard(0));
			}

			discard.insertCard(deck.getCard(deck.getSize() - 1));

			deck.shuffleDeck();
		}

		for (int i = 0; i != startingCards; i++)
			((UnoDeck) players.get(players.size() - 1)).insertCard(deck.getCard(0));

		((UnoDeck) players.get(players.size() - 1)).sort();

		turn.addNewPlayer();

		return players.size() - 1;
	}

	public boolean isValidCard(int cardNumber) {
		boolean sameColor;
		boolean sameNumber;
		boolean isWild;
		boolean isWildFour;
		boolean isDrawTwo;

		sameColor = getCurrentDeck().showCard(cardNumber).getColor() == currentColor;
		sameNumber = getCurrentDeck().showCard(cardNumber).getValue() == currentCard;

		isWild = getCurrentDeck().showCard(cardNumber).isWild();
		isWildFour = getCurrentDeck().showCard(cardNumber).isWildFour();
		isDrawTwo = getCurrentDeck().showCard(cardNumber).isDrawTwo();

		if (currentCardsToDraw != 0) {
			if (getLastCardPlayed().isWildFour()) {
				return (isWildFour);
			} else {
				return (isDrawTwo || isWildFour);
			}
		}

		if (!withWildWin) {
			return ((sameColor || sameNumber || isWild) && !(isWild && getCurrentDeck().getSize() == 1));
		}

		return (sameColor || sameNumber || isWild);

	}

	private int switchHands() {
		if (!withZeroRule)
			return 0;

		Turn i = new Turn(turn.showCurrentTurn(), getNumberPlayers(), turn.getDirection() * -1);

		UnoDeck first = ((UnoDeck) players.get(i.showCurrentTurn()));

		for (int j = 0; j != getNumberPlayers() - 1; j += 1) {
			players.set(i.showCurrentTurn(), ((UnoDeck) players.get(i.showNextTurn())));
			i.nextTurn();
		}
		players.set(i.showCurrentTurn(), first);

		return 6;
	}

	public int getCurrentPlayer() {
		return turn.showCurrentTurn();
	}

	public int getNextPlayer() {
		return turn.showNextTurn();
	}

	public int getPrevPlayer() {
		return turn.showPrevTurn();
	}

	public int getLastPlayer() {
		return lastTurn;
	}

	public void setColor(int color) {
		if (colorSet)
			throw new RuntimeException("You can only set a color with a previous Wild !");

		if (color < 0 || color > 3)
			throw new RuntimeException("Invalid color chosen !");

		currentColor = color;

		colorSet = true;
	}

	public int playCard(int player, int cardNumber) {

		if (finished)
			return GAME_FINISHED;
		if (!colorSet)
			return SELECT_COLOR_FIRST;

		if (playerRetired != -1) {
			int temp = playerRetired;
			playerRetired = -1;
			return PLAYER_RETIRED + temp;
		}

		int result = PLAYED_NORMAL;

		if (player != turn.showCurrentTurn())
			return NOT_YOUR_TURN;

		lastTurn = turn.showCurrentTurn();

		if (cardNumber == getCurrentDeck().getSize()) {

			int drawn = drawCard(1);

			((UnoDeck) players.get(turn.showCurrentTurn())).sort();

			if (drawn > 0)
				if (existsValidPlay())
					return DREW_AND_VALID;
				else if (drawn == DREW_AND_VALID) {
					if (existsValidPlay())
						return CARD_NOT_VALID;
				} else if (drawn == DECK_EMPTY) {
					return DECK_EMPTY ;
				}

			if (!existsValidPlay()) {
				turn.nextTurn();
				return DREW_AND_NO_VALID;
			}
		}

		result = playTurn(cardNumber);

		return result;
	}

	private int playTurn(int numCard) {
		int c = 0;

		try {
			playCard(numCard);
		} catch (IllegalArgumentException e) {
			return -1;
		}

		if (((UnoDeck) players.get(turn.showCurrentTurn())).getSize() == 0) {
			c += 35;
			finished = true;
		}

		if (((UnoDeck) players.get(turn.showCurrentTurn())).getSize() == 1)
			c += 20;

		int cardsDrawn = 0;

		switch (getLastCardPlayed().getValue()) {

		default:
			c += 0;
			break;
		case Card.RED:
			c += switchHands();
			break;
		case Card.REVERSE:
			c += 1;
			turn.changeDirection();
			if (getNumberPlayers() == 2)
				turn.nextTurn();
			break;

		case Card.SKIP:
			c += 2;
			turn.nextTurn();
			break;

		case Card.DRAW_TWO:
			c += 3;
			cardsDrawn = drawCard(2);
			if (cardsDrawn == -1 || cardsDrawn == -2) {
				c += 4;
			}

			break;

		case Card.WILD:
			c += 4;
			colorSet = false;
			break;

		case Card.WILD_FOUR:
			c += 5;
			colorSet = false;
			cardsDrawn = drawCard(4);
			if (cardsDrawn == -1 || cardsDrawn == -2)
				c += 3;
			break;
		}

		turn.nextTurn();

		return c;
	}
}