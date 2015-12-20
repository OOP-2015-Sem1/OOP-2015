package Main;

import javax.swing.JFrame;

public class ChessFrame {
	// public static Board board = new Board();
	// public static boolean whiteTurn = true;
	// public static Piece emptySpace = new NoPiece();
	public Movement movement = new Movement();

	public ChessFrame() {

		// emptySpace.setColor(Colors.WHITE);
		JFrame f = new JFrame("2 player Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		f.add(ui);
		f.setSize(1000, 1000);
		f.setVisible(true);
	}
}
