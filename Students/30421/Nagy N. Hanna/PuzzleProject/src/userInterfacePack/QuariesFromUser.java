package userInterfacePack;

import javax.swing.JOptionPane;

public class QuariesFromUser {

	public static boolean queryForBoardSize() {
		int reply = JOptionPane.showConfirmDialog(null, "The Board'll have 8 segments do you want more (15)?",
				"Options", JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}

	public static boolean queryForSound() {
		int reply = JOptionPane.showConfirmDialog(null, "Do you want sound?", "Options", JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}

	public static String queryForName() {
		String name = JOptionPane.showInputDialog("What's your name?");
		return name;
	}

}
