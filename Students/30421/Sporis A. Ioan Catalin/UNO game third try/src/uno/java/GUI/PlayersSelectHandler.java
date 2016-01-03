package uno.java.GUI;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uno.java.constants.Constants;
import uno.java.entities.Player;
import uno.java.game.Game;

public class PlayersSelectHandler implements ListSelectionListener, ActionListener, KeyListener {

	static String nrOfPlayers;
	String playerName;

	@Override
	public void valueChanged(ListSelectionEvent list) {
		nrOfPlayers = Constants.PLAYERS[((JList) list.getSource()).getSelectedIndex()];
	}

	@Override
	public void actionPerformed(ActionEvent btn) {
		Game.enterName = new EnterPlayerNameFrame();
		Game.nrOfPlayersFrame.setVisible(false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.playerName = ((TextField) e.getSource()).getText();
			((TextField) e.getSource()).setText(null);

			Player player = new Player();
			player.setNickname(this.playerName);
			Game.addPlayers(player);

			if (Game.nrOfPlayers == Integer.parseInt(this.nrOfPlayers)) {
				Game.dealCards();

				Game.gameWindow = new GameWindow();
				Game.gameWindow.placePlayers(Game.nrOfPlayers, Game.players);

				Game.enterName.setVisible(false);
				Game.players.get(0).setTurn(true);

			} else {
				Game.nrOfPlayers++;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
