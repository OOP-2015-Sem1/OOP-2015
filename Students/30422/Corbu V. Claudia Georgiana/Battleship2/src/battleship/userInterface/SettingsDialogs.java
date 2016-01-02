package battleship.userInterface;

import javax.swing.JOptionPane;

public class SettingsDialogs {

	
	public static void notifyIllegalPlacement() {
		JOptionPane.showMessageDialog(null, "You can not place this ship there.");
		
	}
	public static void notifyShipsLeftToPlace() {
		JOptionPane.showMessageDialog(null, "You still need to place some ships");
		
	}
	
	
	public static void notifyLoss() {
		JOptionPane.showMessageDialog(null, " Computer won!!!");
		System.exit(0);
	}


	public static void notifyWin() {
		JOptionPane.showMessageDialog(null, "Congratulations!!!You won!");
		System.exit(0);
	}
	

}
