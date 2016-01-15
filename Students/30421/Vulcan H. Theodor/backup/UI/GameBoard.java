package UI;

import javax.swing.JFrame;


public interface GameBoard {
	
	public static void main(String[] args) {
			JFrame mainFrame= new Board("Monopoly");
			mainFrame.setVisible(true);
			mainFrame.setSize(1500, 1030);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setResizable(false);


			
	}
}
