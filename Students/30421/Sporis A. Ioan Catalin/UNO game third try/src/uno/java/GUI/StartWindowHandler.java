package uno.java.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uno.java.constants.Constants;
import uno.java.game.Game;

public class StartWindowHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent btn) {
		if (btn.getActionCommand().equals(Constants.START_NEW_GAME)) {
			Game.nrOfPlayersFrame = new NrOfPlayersFrame();
			Game.startWindow.setVisible(false);
		} else if (btn.getActionCommand().equals(Constants.HELP)) {
			System.out.println("Help");
		} else if (btn.getActionCommand().equals(Constants.EXIT_GAME)) {
			System.exit(0);
		}

	}

}
