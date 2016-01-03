package uno.java.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uno.java.entities.Card;
import uno.java.entities.Player;
import uno.java.game.Game;

public class GameWindow extends JFrame implements Designer {

	public Color backgroundColor = new Color(14, 105, 32);
	public JPanel backgroundPanel = new JPanel();
	private Rectangle deckPanelPosition = new Rectangle(400, 250, 100, 152);
	public DeckPanel deckPanel;
	public GameCardsPanel releasedCards = new GameCardsPanel();
	private Rectangle realeasedCardsBounds = new Rectangle(700, 250, 100, 152);

	public PlayerDescriptionPanel playerDescPanel1;
	public PlayerDescriptionPanel playerDescPanel2;

	public ChangeColorPanel colorPanel = new ChangeColorPanel();
	public Color gameColor;
	public int reverse = 1;
	public int turn = 0;
	public JLabel playerTurn = new JLabel("Player 1");

	public GameWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1370, 700);
		setVisible(true);
		this.setLayout(new GridLayout(1, 1));

		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(1370, 700);
		backgroundPanel.setBackground(this.backgroundColor);
		this.arrangeItems();
		this.add(backgroundPanel);
	}

	@Override
	public void componentSetBounds(Component component, Rectangle bounds) {
		component.setBounds(bounds);
	}

	@Override
	public void arrangeItems() {
		this.setDeckPanel(Game.deckOfCards.deck);
		this.setGameCardsPanel();
		this.setChangeColorPanel();
		this.turnDisplay();
	}

	public void placePlayers(int nrOfPlayers, ArrayList<Player> players) {
		if (nrOfPlayers == 2) {
			PlayerDescriptionPanel playerDescPanel1 = new PlayerDescriptionPanel("images.png",
					players.get(0).getNickname(), new Dimension(120, 120));
			PlayerDescriptionPanel playerDescPanel2 = new PlayerDescriptionPanel("images.png",
					players.get(1).getNickname(), new Dimension(120, 120));

			players.get(0).playerCardsPanel = new PlayerCardsPanelHorizontal(new Dimension(600, 200));

			players.get(1).playerCardsPanel = new PlayerCardsPanelHorizontal(new Dimension(600, 150));

			players.get(0).playerCardsPanel.arrangeCards(players.get(0).getHand());
			players.get(1).playerCardsPanel.arrangeCards(players.get(1).getHand());

			this.componentSetBounds(playerDescPanel1, new Rectangle(285, 540, 120, 130));
			this.componentSetBounds(players.get(0).playerCardsPanel, new Rectangle(425, 500,
					players.get(0).playerCardsPanel.getWidth(), players.get(0).playerCardsPanel.getHeight()));
			this.backgroundPanel.add(playerDescPanel1);
			this.backgroundPanel.add(players.get(0).playerCardsPanel);

			this.componentSetBounds(playerDescPanel2, new Rectangle(905, 40, 120, 130));
			this.componentSetBounds(players.get(1).playerCardsPanel, new Rectangle(285, 0,
					players.get(1).playerCardsPanel.getWidth(), players.get(1).playerCardsPanel.getHeight()));
			this.backgroundPanel.add(players.get(1).playerCardsPanel);
			this.backgroundPanel.add(playerDescPanel2);

		} else if (nrOfPlayers == 3) {
			PlayerDescriptionPanel playerDescPanel1 = new PlayerDescriptionPanel("images.png",
					players.get(0).getNickname(), new Dimension(120, 120));
			PlayerDescriptionPanel playerDescPanel2 = new PlayerDescriptionPanel("images.png",
					players.get(1).getNickname(), new Dimension(120, 120));
			PlayerDescriptionPanel playerDescPanel3 = new PlayerDescriptionPanel("images.png",
					players.get(2).getNickname(), new Dimension(120, 120));

			players.get(0).playerCardsPanel = new PlayerCardsPanelHorizontal(new Dimension(600, 200)); // playerCardsPanel1.setPlayer(players.get(0));

			players.get(2).playerCardsPanel = new PlayerCardsPanelHorizontal(new Dimension(600, 150)); // playerCardsPanel3.setPlayer(players.get(2));

			players.get(1).playerCardsPanel = new PlayerCardsPanelVertical(new Dimension(150, 400)); // playerCardsPanel2.setPlayer(players.get(1));

			players.get(0).playerCardsPanel.arrangeCards(players.get(0).getHand());
			players.get(1).playerCardsPanel.arrangeCards(players.get(1).getHand());
			players.get(2).playerCardsPanel.arrangeCards(players.get(2).getHand());

			this.componentSetBounds(playerDescPanel1, new Rectangle(285, 540, 120, 130));
			this.componentSetBounds(players.get(0).playerCardsPanel, new Rectangle(425, 500,
					players.get(0).playerCardsPanel.getWidth(), players.get(0).playerCardsPanel.getHeight()));

			this.backgroundPanel.add(playerDescPanel1);
			this.backgroundPanel.add(players.get(0).playerCardsPanel);

			this.componentSetBounds(playerDescPanel3, new Rectangle(905, 40, 120, 130));
			this.componentSetBounds(players.get(2).playerCardsPanel, new Rectangle(285, 0,
					players.get(2).playerCardsPanel.getWidth(), players.get(2).playerCardsPanel.getHeight()));

			this.backgroundPanel.add(players.get(2).playerCardsPanel);
			this.backgroundPanel.add(playerDescPanel3);

			this.componentSetBounds(playerDescPanel2, new Rectangle(0, 65, 120, 130));
			this.componentSetBounds(players.get(1).playerCardsPanel, new Rectangle(0, 205,
					players.get(1).playerCardsPanel.getWidth(), players.get(1).playerCardsPanel.getHeight()));

			this.backgroundPanel.add(playerDescPanel2);
			this.backgroundPanel.add(players.get(1).playerCardsPanel);
		}

		else if (nrOfPlayers == 4) {
			PlayerDescriptionPanel playerDescPanel1 = new PlayerDescriptionPanel("images.png",
					players.get(0).getNickname(), new Dimension(120, 120));
			PlayerDescriptionPanel playerDescPanel2 = new PlayerDescriptionPanel("images.png",
					players.get(1).getNickname(), new Dimension(120, 120));
			PlayerDescriptionPanel playerDescPanel3 = new PlayerDescriptionPanel("images.png",
					players.get(2).getNickname(), new Dimension(120, 120));
			PlayerDescriptionPanel playerDescPanel4 = new PlayerDescriptionPanel("images.png",
					players.get(3).getNickname(), new Dimension(120, 120));

			players.get(0).playerCardsPanel = new PlayerCardsPanelHorizontal(new Dimension(600, 200)); // playerCardsPanel1.setPlayer(players.get(0));

			players.get(1).playerCardsPanel = new PlayerCardsPanelVertical(new Dimension(150, 400)); // playerCardsPanel2.setPlayer(players.get(1));

			players.get(2).playerCardsPanel = new PlayerCardsPanelHorizontal(new Dimension(600, 150)); // playerCardsPanel3.setPlayer(players.get(2));

			players.get(3).playerCardsPanel = new PlayerCardsPanelVertical(new Dimension(150, 400)); // playerCardsPanel4.setPlayer(players.get(3));

			players.get(0).playerCardsPanel.arrangeCards(players.get(0).getHand());
			players.get(1).playerCardsPanel.arrangeCards(players.get(1).getHand());
			players.get(2).playerCardsPanel.arrangeCards(players.get(2).getHand());
			players.get(3).playerCardsPanel.arrangeCards(players.get(3).getHand());

			this.componentSetBounds(playerDescPanel1, new Rectangle(285, 540, 120, 130));
			this.componentSetBounds(players.get(0).playerCardsPanel, new Rectangle(425, 500,
					players.get(0).playerCardsPanel.getWidth(), players.get(0).playerCardsPanel.getHeight()));

			this.backgroundPanel.add(playerDescPanel1);
			this.backgroundPanel.add(players.get(0).playerCardsPanel);

			this.componentSetBounds(playerDescPanel3, new Rectangle(905, 40, 120, 130));
			this.componentSetBounds(players.get(2).playerCardsPanel, new Rectangle(285, 0,
					players.get(2).playerCardsPanel.getWidth(), players.get(2).playerCardsPanel.getHeight()));

			this.backgroundPanel.add(players.get(2).playerCardsPanel);
			this.backgroundPanel.add(playerDescPanel3);

			this.componentSetBounds(playerDescPanel2, new Rectangle(0, 65, 120, 130));
			this.componentSetBounds(players.get(1).playerCardsPanel, new Rectangle(0, 205,
					players.get(1).playerCardsPanel.getWidth(), players.get(1).playerCardsPanel.getHeight()));

			this.backgroundPanel.add(playerDescPanel2);
			this.backgroundPanel.add(players.get(1).playerCardsPanel);

			this.componentSetBounds(playerDescPanel4, new Rectangle(1220, 485, 120, 130));
			this.componentSetBounds(players.get(3).playerCardsPanel, new Rectangle(1220, 65,
					players.get(3).playerCardsPanel.getWidth(), players.get(3).playerCardsPanel.getHeight()));

			this.backgroundPanel.add(playerDescPanel4);
			this.backgroundPanel.add(players.get(3).playerCardsPanel);
		}

	}

	public void setDeckPanel(ArrayList<Card> deck) {
		this.deckPanel = new DeckPanel(deck);
		this.componentSetBounds(this.deckPanel, this.deckPanelPosition);
		this.backgroundPanel.add(this.deckPanel);
	}

	public void setGameCardsPanel() {
		this.componentSetBounds(this.releasedCards, this.realeasedCardsBounds);
		this.backgroundPanel.add(this.releasedCards);
	}

	public void setChangeColorPanel() {
		this.componentSetBounds(this.colorPanel,
				new Rectangle(0, 600, this.colorPanel.getWidth(), this.colorPanel.getHeight()));
		this.backgroundPanel.add(this.colorPanel);
	}

	public void turnDisplay() {

		this.componentSetBounds(this.playerTurn, new Rectangle(540, 300, 152, 95));
		this.playerTurn.setFont(new Font("Times New Roman", Font.BOLD, 40));

		this.playerTurn.setBackground(this.backgroundColor);

		this.backgroundPanel.add(this.playerTurn);

	}

}
