package Controller;

/*This class controls all user actions from ButtonPanel.*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import Model.Game;

public class ButtonController implements ActionListener {
	private Game game;

	/* Constructor which sets game */
	public ButtonController(Game game) {
		this.game = game;
	}

	/* Performs action after user pressed button. */
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("New"))
			game.newGame();
		else if (event.getActionCommand().equals("Check"))
			game.checkGame();
		else if (event.getActionCommand().equals("Exit"))
			System.exit(0);
		else if (event.getActionCommand().equals("Help on"))
			game.setHelp(((JCheckBox) event.getSource()).isSelected());
		else
			game.setSelectedNumber(Integer.parseInt(event.getActionCommand()));
	}
}
