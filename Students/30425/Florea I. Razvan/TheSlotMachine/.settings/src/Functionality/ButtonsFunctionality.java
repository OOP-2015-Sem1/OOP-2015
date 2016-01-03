
package Functionality;

import static Main.ValuesToWorkWith.bet;
import static Main.ValuesToWorkWith.credit;
import static Main.ValuesToWorkWith.numberOfLines;
import static Main.ValuesToWorkWith.winning;

import javax.swing.JOptionPane;

import UserInterface.ButtonsPanel;
import UserInterface.CustomizedFrame;
import UserInterface.GamblingFrame;
import UserInterface.Labels;
import UserInterface.TilesPanel;

public class ButtonsFunctionality {

	private Winnings win = new Winnings();

	public void actionForSpin() {

		// automatic collect
		actionForCollect();

		if (credit - bet < 0) {
			JOptionPane.showMessageDialog(null, "Please select a smaller Bet", "Insufficient funds !",
					JOptionPane.WARNING_MESSAGE);

		} else if (credit - bet >= 0) {

			credit -= bet;
			CustomizedFrame.display.setVisible(false);
			TilesPanel.displayValues();
			ButtonsPanel.newCredit();
			CustomizedFrame.display.setVisible(true);

			winning = win.lookForWinnings(numberOfLines);
			ButtonsPanel.newWinning();

		}

		if (credit == 0 && winning == 0) {
			Labels.creditLabel.setText("" + 0);
			JOptionPane.showMessageDialog(null, "Thanks for Playing", "Out of Credit !",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	public void actionForCollect() {

		if (winning != 0) {
			credit += winning;
			ButtonsPanel.newCredit();
			winning = 0;
			ButtonsPanel.newWinning();
		}
	}

	public void actionForGamble() {

		if (winning != 0) {
			new GamblingFrame("GAMBLE");
		}

	}
}
