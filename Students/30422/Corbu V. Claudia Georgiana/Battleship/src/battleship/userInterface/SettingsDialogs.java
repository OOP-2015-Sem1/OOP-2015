package battleship.userInterface;

import static battleship.background.code.Constants.*;

import javax.swing.JOptionPane;

public class SettingsDialogs {
	
	public static int queryForShipSize() {
		String size = JOptionPane.showInputDialog(String.format(
				"Set Ship dimension: (%d-%d)", MIN_SHIP_SIZE, MAX_SHIP_SIZE));

		Integer shipSize;
		try {
			shipSize = Integer.parseInt(size);
			if (shipSize < MIN_SHIP_SIZE || shipSize > MAX_SHIP_SIZE)
				shipSize = DEFAULT_SHIP_SIZE;
		} catch (NumberFormatException e) {
			shipSize = DEFAULT_SHIP_SIZE;
		}
		return shipSize;
	}

	public static boolean queryForHorizontalPlacementOfShip() {
		int reply = JOptionPane.showConfirmDialog(null,
				"Should the ship be placed horizotally?", "Options",
				JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}
	
	public static void notifyIllegalPlacement() {
		JOptionPane.showMessageDialog(null, "You can not place this ship there.");
		
	}
	
	
	public static void notifyLoss() {
		JOptionPane.showMessageDialog(null, "You lost.");
		System.exit(0);
	}


	public static void notifyWin() {
		JOptionPane.showMessageDialog(null, "You won!");
		System.exit(0);
	}
	

}
