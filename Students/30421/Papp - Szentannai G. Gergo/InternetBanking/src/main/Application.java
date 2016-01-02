package main;

import javax.swing.SwingUtilities;

import db.manager.DatabaseConnect;
import gui.MainFrame;

public class Application {

	public static MainFrame mainFrame;

	public Application() throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new MainFrame();
			}
		});
		DatabaseConnect databaseConnect = new DatabaseConnect();
	}

}
