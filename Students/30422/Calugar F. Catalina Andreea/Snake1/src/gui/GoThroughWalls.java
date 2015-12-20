package gui;

import javax.swing.JOptionPane;

public class GoThroughWalls {

	public static boolean queryForGoingThroughWalls() {
		int reply = JOptionPane.showConfirmDialog(null,
				"Should it go through walls?", "Options",
				JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}
}
