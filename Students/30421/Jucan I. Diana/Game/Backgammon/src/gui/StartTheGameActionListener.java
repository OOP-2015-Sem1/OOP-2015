package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartTheGameActionListener implements ActionListener {

	private BackgammonGui backgammonGui;

	public StartTheGameActionListener(BackgammonGui backgammonGui) {
		this.backgammonGui = backgammonGui;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		//change game state if button is pressed
		//
		this.backgammonGui.decideWhoBeginsTheGame();
	}
}
