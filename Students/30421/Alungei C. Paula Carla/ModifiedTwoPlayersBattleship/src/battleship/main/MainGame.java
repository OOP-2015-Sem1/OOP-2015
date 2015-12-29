package battleship.main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import battleship.gui.MainFrame;

public class MainGame {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "Let the game begin");
				JFrame jFrame = new MainFrame("Battleship Game");
				setDimensions(jFrame);

			}

			private void setDimensions(JFrame jFrame) {
				jFrame.setSize(1100, 500);
				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jFrame.setVisible(true);
			}
		});

	}

}
