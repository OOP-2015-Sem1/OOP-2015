package uno.java.GUI;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import uno.java.entities.Card;

public class PlayerCardsPanelVertical extends PlayerCardsPanel {
	public PlayerCardsPanelVertical(Dimension dimension) {
		super(dimension);
		this.setLayout(null);
	}

	public void arrangeCards(ArrayList<Card> cards) {
		for (Card card : cards) {
			card.addMouseListener(new GameHandler());
			card.addMouseListener(new Handler());
			card.setBounds(new Rectangle(this.x, this.y, 95, 152));
			this.add(card);
			this.y = this.y + 25;
		}
		this.x = 0;
		this.y = 0;
	}
}
