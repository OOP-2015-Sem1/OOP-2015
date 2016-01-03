package gui;

import javax.swing.*;
import java.util.Stack;
import java.awt.*;
import java.awt.event.*;

public class DeckPile extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Card card;
	private Stack<Card> deckStack;
	private Deck deck;
	private int noOfCards = 24;
	private JButton button;

	public DeckPile() {
		setLayout(new CardLayout());
		for (int i = 0; i < 24; i++) {
			card = deck.getCard(i);
			button = card.getButton();
			add(button);
			button.addActionListener(this);
			deckStack.push(card);
		}
	}
	public void actionPerformed(ActionEvent e){
		// addToWastePile();
		removeFromDeckPile(button);
	}

	public void removeFromDeckPile(JButton b) {
		remove(b);
		revalidate();
		repaint();
		noOfCards--;
	}

	public Card getFromDeckPile() {
		card = deckStack.pop();
		removeFromDeckPile(card.getButton());
		return card;
	}

	public boolean isEmptyDeck() {
		if (noOfCards == 0)
			return true;
		else
			return false;
	}

}
