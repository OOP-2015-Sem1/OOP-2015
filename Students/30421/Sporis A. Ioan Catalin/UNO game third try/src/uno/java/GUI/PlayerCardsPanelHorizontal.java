package uno.java.GUI;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import uno.java.constants.Constants;
import uno.java.entities.Card;
import uno.java.game.Game;

public class PlayerCardsPanelHorizontal extends PlayerCardsPanel {
	public PlayerCardsPanelHorizontal(Dimension dimension) {
		super(dimension);
		this.setLayout(null);
	}

	public void arrangeCards(ArrayList<Card> cards) {
		for (Card card : cards) {
			card.addMouseListener(Constants.gameHandler);
			card.addMouseListener(this.handler);
			card.setBounds(new Rectangle(this.x, this.y, 95, 152));
			this.add(card);
			card.repaint();
			this.x = this.x + 25;
		}
		this.x = 0;
		this.y = 0;
	}
}
