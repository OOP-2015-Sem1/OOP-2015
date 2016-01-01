package main;

import javax.swing.SwingUtilities;
import gui.MainFrame;

public class Application {
	
	public static MainFrame mainFrame;
	
	public Application(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new MainFrame();
			}
		});
	}

}
