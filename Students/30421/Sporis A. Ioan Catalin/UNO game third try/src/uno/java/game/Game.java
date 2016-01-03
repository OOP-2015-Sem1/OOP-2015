package uno.java.game;

import java.util.ArrayList;

import uno.java.GUI.EnterPlayerNameFrame;
import uno.java.GUI.GameWindow;
import uno.java.GUI.NrOfPlayersFrame;
import uno.java.GUI.StartWindow;
import uno.java.constants.Constants;
import uno.java.entities.Card;
import uno.java.entities.Deck;
import uno.java.entities.Player;

public class Game {
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static GameWindow gameWindow;
	public static int nrOfPlayers = 1;
	public static NrOfPlayersFrame nrOfPlayersFrame;
	public static EnterPlayerNameFrame enterName;
	public static StartWindow startWindow;

	public static Deck deckOfCards = new Deck();
	private boolean run = true;

	public static void addPlayers(Player player) {
		players.add(player);
	}

	public static void dealCards() {
		Game.deckOfCards.shuffleDeck();
		for (Player player : Game.players) {
			ArrayList<Card> firstHand = new ArrayList<Card>();
			for (int j = 0; j < Constants.FIRST_HAND; j++) {
				firstHand.add(Game.deckOfCards.popFromDeck());
			}
			player.setFirstHand(firstHand);
		}

	}

	public static void showCards() {
		for (Player player : Game.players) {
			System.out.println('\n' + player.getNickname());
			for (Card card : player.getHand()) {
				System.out.println(card.getCardName());
			}
		}
	}

	public static void startGame(boolean run) {
		Game.startWindow = new StartWindow();

	}

}