package View;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/*Main class of program.*/
public class SudokuMain extends JFrame {

	public SudokuMain() {
		super("Sudoku");
		getContentPane().setLayout(new BorderLayout());

		SudokuPanel sudokuPanel = new SudokuPanel();
		add(sudokuPanel, BorderLayout.CENTER);

		ButtonPanel buttonPanel = new ButtonPanel();
		add(buttonPanel, BorderLayout.EAST);

		pack();

	}

	/* Main part of program. */
	public static void main(String[] args) {
		SudokuMain S = new SudokuMain();
		S.setDefaultCloseOperation(EXIT_ON_CLOSE);
		S.setSize(800, 400);
		S.setLocationRelativeTo(null);// to put the window in the center of the
										// screen
		S.setVisible(true);

	}
}
