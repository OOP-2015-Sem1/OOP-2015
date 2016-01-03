package player;

import game.UnoDeck;
import game.UnoGame;

public abstract class AIPlayer {

	protected UnoGame thisGame;

	public AIPlayer(UnoGame game) {
		thisGame = game;
	}

	public abstract int playMyTurn(UnoDeck myDeck);

	public abstract int chooseColor(UnoDeck myDeck);
}