package project.memorygame.views;

import project.memorygame.models.*;

import project.memorygame.controllers.*;
import project.memorygame.models.Deck;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

@SuppressWarnings("serial")
public class Board extends JFrame {
	private Game game;

	public Board(int width, int height, Game game, int pairs) {
		this.game = game;
		this.setTitle("MemoryGame");
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.setLayout(new GridLayout(1,1));
		JPanel playArea = new JPanel();
		playArea.setVisible(true);

		// make the PlayArea
		playArea = addDeck(pairs);

		this.add(playArea);
	}

	private JPanel addDeck(int pairs) {
		JPanel panel = new JPanel(new GridLayout(pairs/2, pairs/2));
		Deck deck = new Deck(this.game,pairs);
		for (Card c : deck.cards) {
			panel.add(c);
		}
		return panel;
	}

}
