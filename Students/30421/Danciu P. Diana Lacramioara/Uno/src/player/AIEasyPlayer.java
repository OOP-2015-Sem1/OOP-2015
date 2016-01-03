package player;

import java.util.Random;

import game.UnoDeck;
import game.UnoGame;

public class AIEasyPlayer extends AIPlayer {

	private final boolean withWildWin;

	public AIEasyPlayer(UnoGame thisGame) {
		super(thisGame);
		this.thisGame = thisGame;
		this.withWildWin = thisGame.withWildWin();
	}

	@Override
	public int playMyTurn(UnoDeck myDeck) {

		int cardNumber = -1;

		for (int i = 0; i != myDeck.getSize(); i += 1) {
			if (thisGame.isValidCard(i)) {
				cardNumber = i;
				break;
			}
		}

		if (withWildWin) {
			if (cardNumber != -1 && myDeck.getSize() == 2 && myDeck.showCard(cardNumber).isWild()) {
				int otherCard = (cardNumber == 1) ? 0 : 1;
				if (thisGame.isValidCard(otherCard) && !myDeck.showCard(otherCard).isWild()) {
					cardNumber = otherCard;
				} else {
					cardNumber = 2;
				}
			}
		}

		if (cardNumber == -1) {
			cardNumber = myDeck.getSize();
		}

		return cardNumber;
	}

	@Override
	public int chooseColor(UnoDeck myDeck) {
		Random rand = new Random(System.currentTimeMillis());

		return rand.nextInt(4);
	}
}
