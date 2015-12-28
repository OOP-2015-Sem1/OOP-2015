
package Functionality;

import static Main.ValuesToWorkWith.credit;

import javax.swing.JOptionPane;

import Main.ValuesToWorkWith;
import UserInterface.ButtonsPanel;
import UserInterface.CustomizedFrame;
import UserInterface.GamblingFrame;
import UserInterface.Labels;
import UserInterface.TilesPanel;

public class ButtonsFunctionality {
	
	private Winnings win = new Winnings();
	private CustomizedFrame frame = new CustomizedFrame("");
	private ValuesToWorkWith values = new ValuesToWorkWith();
	private ButtonsPanel butPanel = new ButtonsPanel();

	public void actionForSpin(){
		
		//automatic collect
			actionForCollect();
		
		if (credit - values.getBet() < 0) {
			JOptionPane.showMessageDialog(null, "Please select a smaller Bet", "Insufficient funds !",
					JOptionPane.WARNING_MESSAGE);
	
		} else if (credit - values.getBet() >= 0){
			
			credit -= values.getBet();	
			frame.getDisplay().setVisible(false);
			TilesPanel.displayValues();
			ButtonsPanel.newCredit();
			frame.getDisplay().setVisible(true);
			
			values.setWinning(win.lookForWinnings(values.getNumberOfLines()));
			butPanel.newWinning();
			
		}
		
		if (credit == 0 && values.getWinning() == 0) {
					Labels.creditLabel.setText("" + 0);
					JOptionPane.showMessageDialog(null, "Thanks for Playing", "Out of Credit !",
					JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
		}
	}

	public void actionForCollect(){
		
		if(values.getWinning() != 0) {
			credit += values.getWinning();
			ButtonsPanel.newCredit();
			values.setWinning(0);
			butPanel.newWinning();
		}
	}
	
	public void actionForGamble(){
	
		if(values.getWinning() != 0){
			new GamblingFrame("GAMBLE");
		}
		
	}	
}
