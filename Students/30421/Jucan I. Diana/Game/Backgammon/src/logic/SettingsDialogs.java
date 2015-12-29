package logic;

import javax.swing.JOptionPane;

public class SettingsDialogs {

	public String queryForPlayer1() {
		String player1 = JOptionPane.showInputDialog("Player1: ");

		return player1;
	}
	
	public String queryForPlayer2() {
		String player2 = JOptionPane.showInputDialog("Player2: ");

		return player2;
	}

}
