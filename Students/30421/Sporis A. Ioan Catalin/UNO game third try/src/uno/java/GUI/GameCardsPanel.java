package uno.java.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import uno.java.entities.Card;

public class GameCardsPanel extends JPanel {
	public ArrayList<Card> cards = new ArrayList<Card>();
	public Card releasedCard;
	public static int NoOfCards = 0;

	public GameCardsPanel() {
		this.setBackground(Color.WHITE);
		this.setSize(95, 152);
		this.setLayout(null);
	}

	public GameCardsPanel(Card card) {
		this();
		this.releasedCard = card;
	}

	public void paintComponent(Graphics g) {
		if (this.cards.isEmpty() == false) {
			super.paintComponent(g);
			g.setColor(Color.white);
			g.clearRect(0, 0, getWidth(), getHeight());
			;
			g.drawImage(releasedCard.cardImage, 0, 0, this);
		} else {
			super.paintComponent(g);
			g.setColor(Color.white);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawRect(0, 0, 95, 152);
		}
	}
}
