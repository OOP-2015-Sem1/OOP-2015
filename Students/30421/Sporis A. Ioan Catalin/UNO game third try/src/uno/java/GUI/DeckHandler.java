package uno.java.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import uno.java.entities.Card;
import uno.java.entities.Player;
import uno.java.game.Game;

public class DeckHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (Game.players.get(0).isTurn()) {
			this.refreshPanel(Game.players.get(0).playerCardsPanel,
					((DeckPanel) e.getSource()).getDeck().get(((DeckPanel) e.getSource()).getDeck().size() - 1),
					Game.players.get(0));
		} else if (Game.players.get(1).isTurn()) {
			this.refreshPanel(Game.players.get(1).playerCardsPanel,
					((DeckPanel) e.getSource()).getDeck().get(((DeckPanel) e.getSource()).getDeck().size() - 1),
					Game.players.get(1));
		} else if (Game.players.get(2).isTurn()) {
			this.refreshPanel(Game.players.get(2).playerCardsPanel,
					((DeckPanel) e.getSource()).getDeck().get(((DeckPanel) e.getSource()).getDeck().size() - 1),
					Game.players.get(2));
		} else if (Game.players.get(3).isTurn()) {
			this.refreshPanel(Game.players.get(3).playerCardsPanel,
					((DeckPanel) e.getSource()).getDeck().get(((DeckPanel) e.getSource()).getDeck().size() - 1),
					Game.players.get(3));
		}
		Game.players.get(Game.gameWindow.turn).setTurn(false);
		this.gameSecurity(Game.gameWindow.turn + Game.gameWindow.reverse);
		Game.players.get(Game.gameWindow.turn).setTurn(true);
		Game.gameWindow.playerTurn.setText(Game.players.get(Game.gameWindow.turn).getNickname());
		Game.gameWindow.playerTurn.setBackground(Game.gameWindow.gameColor);

		Game.gameWindow.deckPanel.getDeck()
				.remove(((DeckPanel) e.getSource()).getDeck().get(((DeckPanel) e.getSource()).getDeck().size() - 1));

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
		player.getHand().add(card);
		panel.removeAll();
		panel.updateUI();
		panel.arrangeCards(player.getHand());
	}

	public void gameSecurity(int turn) {
		if (turn == -1) {
			turn = Game.players.size() - 1;
		} else if (turn == Game.players.size()) {
			turn = 0;
		}
		Game.gameWindow.turn = turn;

	}
}
