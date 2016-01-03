package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RollDiceActionListener implements ActionListener {

	private BackgammonGui backgammonGui;

	public RollDiceActionListener(BackgammonGui backgammonGui) {
		this.backgammonGui = backgammonGui;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		//roll dice if button is pressed
		this.backgammonGui.rollDice();
		this.backgammonGui.changeGameState();
	}
}

