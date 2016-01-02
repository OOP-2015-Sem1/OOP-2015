package Main;

import javax.swing.JFrame;

public class ChessFrame {
	public ChessFrame() {
		JFrame f = new JFrame("2 player Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		f.add(ui);
		f.setSize(1000, 1000);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

	}
}
