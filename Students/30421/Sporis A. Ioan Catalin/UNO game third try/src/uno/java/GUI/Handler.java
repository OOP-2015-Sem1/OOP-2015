package uno.java.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import uno.java.entities.Card;
import uno.java.entities.Player;
import uno.java.entities.SpecialCard;
import uno.java.game.Game;

public class Handler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (Game.players.get(Game.gameWindow.turn).getHand().isEmpty()) {
			WinnerFrame winnerFrame = new WinnerFrame(Game.players.get(Game.gameWindow.turn).getNickname());

			Game.gameWindow.setVisible(false);
		}
		this.game(((PlayerCardsPanel) ((Card) e.getSource()).getParent()), ((Card) e.getSource()));
		Game.gameWindow.playerTurn.setText(Game.players.get(Game.gameWindow.turn).getNickname());
		Game.gameWindow.playerTurn.setBackground(Game.gameWindow.gameColor);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void refreshPanel(PlayerCardsPanel panel, Card card, Player player) {
		player.getHand().remove(card);
		panel.removeAll();
		panel.updateUI();
		// System.out.println(card.getCardName() + " " + card.getColor());
		panel.arrangeCards(player.getHand());

	}

	public void releaseCard(Card card) {
		Game.gameWindow.releasedCards.cards.add(card);
		Game.gameWindow.releasedCards.releasedCard = (card);
		Game.gameWindow.releasedCards.repaint();
	}

	public void draw2Cards(PlayerCardsPanel panel, Player player) {
		player.getHand().add(Game.gameWindow.deckPanel.getDeck().get(Game.gameWindow.deckPanel.getDeck().size() - 1));
		Game.gameWindow.deckPanel.getDeck().remove(Game.gameWindow.deckPanel.getDeck().size() - 1);
		player.getHand().add(Game.gameWindow.deckPanel.getDeck().get(Game.gameWindow.deckPanel.getDeck().size() - 1));
		Game.gameWindow.deckPanel.getDeck().remove(Game.gameWindow.deckPanel.getDeck().size() - 1);
		panel.removeAll();
		panel.updateUI();
		panel.arrangeCards(player.getHand());

	}

	public void gameFor2Security(int turn) {
		if (turn == -1) {
			turn = 1;
		} else if (turn == 2) {
			turn = 0;
		}
		Game.gameWindow.turn = turn;
	}

	public void gameSecurity(int turn) {
		if (turn == -1) {
			turn = Game.players.size() - 1;
		} else if (turn == Game.players.size()) {
			turn = 0;
		}
		Game.gameWindow.turn = turn;

	}

	public void game(PlayerCardsPanel panel, Card card) {
		if (panel.equals(Game.players.get(Game.gameWindow.turn).playerCardsPanel)) {
			if (Game.gameWindow.releasedCards.cards.isEmpty()) {
				for (Player player : Game.players) {
					if (player.isTurn()) {
						this.releaseCard(card);
						this.refreshPanel(panel, card, Game.players.get(Game.gameWindow.turn));
						if (card.isSpecial) {
							if (((SpecialCard) card).isSkip()) {
								Game.players.get(Game.gameWindow.turn).setTurn(false);
								this.gameSecurity(Game.gameWindow.turn + 2 * Game.gameWindow.reverse);
								Game.players.get(Game.gameWindow.turn).setTurn(true);
								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);

							}
							if (((SpecialCard) card).isReverse()) {
								Game.gameWindow.reverse = -Game.gameWindow.reverse;
								Game.players.get(Game.gameWindow.turn).setTurn(false);
								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
								Game.players.get(Game.gameWindow.turn).setTurn(true);

							}
							if (((SpecialCard) card).isWild() && (((SpecialCard) card).getDraw() == 0)) {
								Game.gameWindow.colorPanel.addListener(new ColorHandler());
								Game.players.get(Game.gameWindow.turn).setTurn(false);
								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
								Game.players.get(Game.gameWindow.turn).setTurn(true);

							}
							if (((SpecialCard) card).isWild() && (((SpecialCard) card).getDraw() == Card.DRAW_FOUR)) {
								Game.gameWindow.colorPanel.addListener(new ColorHandler());

								Game.players.get(Game.gameWindow.turn).setTurn(false);

								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
								this.draw2Cards(Game.players.get(Game.gameWindow.turn).playerCardsPanel,
										Game.players.get(Game.gameWindow.turn));
								this.draw2Cards(Game.players.get(Game.gameWindow.turn).playerCardsPanel,
										Game.players.get(Game.gameWindow.turn));
								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
								Game.players.get(Game.gameWindow.turn).setTurn(true);

							}
							if ((((SpecialCard) card).getDraw() == 2)) {
								Game.players.get(Game.gameWindow.turn).setTurn(false);
								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
								this.draw2Cards(Game.players.get(Game.gameWindow.turn).playerCardsPanel,
										Game.players.get(Game.gameWindow.turn));
								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
								Game.players.get(Game.gameWindow.turn).setTurn(true);
							}
							if (((SpecialCard) card).isWild() && (((SpecialCard) card).getDraw() == 0)) {
								Game.gameWindow.colorPanel.addListener(new ColorHandler());
								Game.players.get(Game.gameWindow.turn).setTurn(false);
								this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
								Game.players.get(Game.gameWindow.turn).setTurn(true);
							}

						} else if (card.isSpecial == false) {
							Game.players.get(Game.gameWindow.turn).setTurn(false);
							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							Game.players.get(Game.gameWindow.turn).setTurn(true);
						}
						Game.gameWindow.gameColor = card.getColor();
					}
					break;
				}
			} else if (Game.gameWindow.releasedCards.cards.isEmpty() == false) {
				if (Game.players.get(Game.gameWindow.turn).isTurn()) {
					if (card.isSpecial == false) {
						if (card.getColor().equals(Game.gameWindow.gameColor)
								|| (card.getValue() == Game.gameWindow.releasedCards.cards
										.get(Game.gameWindow.releasedCards.cards.size() - 1).getValue())) {
							this.releaseCard(card);
							this.refreshPanel(panel, card, Game.players.get(Game.gameWindow.turn));
							Game.players.get(Game.gameWindow.turn).setTurn(false);
							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							Game.players.get(Game.gameWindow.turn).setTurn(true);
							Game.gameWindow.gameColor = card.getColor();

						}
					} else if (card.isSpecial) {
						if ((((SpecialCard) card).isSkip()
								&& ((SpecialCard) card).getColor().equals(Game.gameWindow.gameColor))
								|| (((SpecialCard) card).isSkip() && ((SpecialCard) Game.gameWindow.releasedCards.cards
										.get(Game.gameWindow.releasedCards.cards.size() - 1)).isSkip())) { // Skip

							this.releaseCard(card);
							this.refreshPanel(panel, card, Game.players.get(Game.gameWindow.turn));
							Game.players.get(Game.gameWindow.turn).setTurn(false);
							this.gameSecurity(Game.gameWindow.turn + 2 * Game.gameWindow.reverse);
							Game.players.get(Game.gameWindow.turn).setTurn(true);

							System.out.println(card.getCardName() + " " + card.getColor());
							Game.gameWindow.gameColor = card.getColor();

						}
						if (((SpecialCard) card).isWild() && (((SpecialCard) card).getDraw() == Card.DRAW_FOUR)) {
							Game.gameWindow.colorPanel.addListener(new ColorHandler());
							this.releaseCard(card);
							this.refreshPanel(panel, card, Game.players.get(Game.gameWindow.turn));
							Game.players.get(Game.gameWindow.turn).setTurn(false);

							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							this.draw2Cards(Game.players.get(Game.gameWindow.turn).playerCardsPanel,
									Game.players.get(Game.gameWindow.turn));
							this.draw2Cards(Game.players.get(Game.gameWindow.turn).playerCardsPanel,
									Game.players.get(Game.gameWindow.turn));
							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							Game.players.get(Game.gameWindow.turn).setTurn(true);
							Game.gameWindow.gameColor = card.getColor();

						}
						if ((((SpecialCard) card).getDraw() == 2) /// Draw 2
								&& ((SpecialCard) card).getColor().equals(Game.gameWindow.gameColor)
								|| (((SpecialCard) card).getDraw() == 2)
										&& ((SpecialCard) Game.gameWindow.releasedCards.cards
												.get(Game.gameWindow.releasedCards.cards.size() - 1)).getDraw() == 2) {

							this.releaseCard(card);
							this.refreshPanel(panel, card, Game.players.get(Game.gameWindow.turn));
							Game.players.get(Game.gameWindow.turn).setTurn(false);

							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							this.draw2Cards(Game.players.get(Game.gameWindow.turn).playerCardsPanel,
									Game.players.get(Game.gameWindow.turn));

							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							Game.players.get(Game.gameWindow.turn).setTurn(true);
							Game.gameWindow.gameColor = card.getColor();

						}
						if (((SpecialCard) card).isReverse()
								&& ((SpecialCard) card).getColor().equals(Game.gameWindow.gameColor)
								|| (((SpecialCard) card).isReverse()
										&& ((SpecialCard) Game.gameWindow.releasedCards.cards
												.get(Game.gameWindow.releasedCards.cards.size() - 1)).isReverse())) {
							this.releaseCard(card);
							this.refreshPanel(panel, card, Game.players.get(Game.gameWindow.turn));
							Game.gameWindow.reverse = -Game.gameWindow.reverse;
							Game.players.get(Game.gameWindow.turn).setTurn(false);
							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							Game.players.get(Game.gameWindow.turn).setTurn(true);
							Game.gameWindow.gameColor = card.getColor();
						}
						if (((SpecialCard) card).isWild() && (((SpecialCard) card).getDraw() == 0)) {
							this.releaseCard(card);
							this.refreshPanel(panel, card, Game.players.get(Game.gameWindow.turn));
							Game.gameWindow.colorPanel.addListener(new ColorHandler());
							Game.players.get(Game.gameWindow.turn).setTurn(false);
							this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
							Game.players.get(Game.gameWindow.turn).setTurn(true);
						}
					}

				}

			}
			if (Game.deckOfCards.deck.isEmpty()) {
				Game.deckOfCards.deck.clear();
				Game.deckOfCards.deck.addAll(Game.gameWindow.releasedCards.cards);
				Game.gameWindow.releasedCards.cards.clear();
				Game.gameWindow.releasedCards.cards.add(Game.deckOfCards.deck.get(Game.deckOfCards.deck.size() - 1));
				Game.deckOfCards.deck.remove(Game.deckOfCards.deck.size() - 1);
				Game.deckOfCards.shuffleDeck();

			}
		}

	}

	public void gameFor3(PlayerCardsPanel panel, Card card) {
		Game.gameWindow.turn = Game.gameWindow.turn + Game.gameWindow.reverse;
		System.out.println(Game.gameWindow.turn);
		if (Game.gameWindow.turn == -1) {
			Game.gameWindow.turn = 2;
		} else if (Game.gameWindow.turn == 3) {
			Game.gameWindow.turn = 0;
		}
		System.out.println(Game.gameWindow.turn);
	}

}
