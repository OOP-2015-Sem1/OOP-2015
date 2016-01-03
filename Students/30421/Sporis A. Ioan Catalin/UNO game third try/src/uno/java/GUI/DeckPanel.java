package uno.java.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import uno.java.entities.Card;

public class DeckPanel extends JPanel {
	private ArrayList<Card> deck = new ArrayList<Card>();

	public Image deckImage = new ImageIcon("D:\\Java assignments\\UNO game third try\\Resorces\\back.png").getImage();

	public DeckPanel(ArrayList<Card> deck) {
		this.setDeck(deck);
		this.addMouseListener(new DeckHandler());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.deckImage, 2, 2, this);
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;

	}

}
