package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import Controller.ButtonController;
import Controller.SudokuController;
import Model.Game;

/*Main class of program.*/

public class SudokuMain extends JFrame {

	private static final long serialVersionUID = 1L;

	public SudokuMain() {
		super("Sudoku");

		getContentPane().setLayout(new BorderLayout());

		Game game = new Game();

		ButtonController buttonController = new ButtonController(game);
		ButtonPanel buttonPanel = new ButtonPanel();
		buttonPanel.setController(buttonController);
		add(buttonPanel, BorderLayout.EAST);

		SudokuPanel sudokuPanel = new SudokuPanel();
		SudokuController sudokuController = new SudokuController(sudokuPanel, game);
		sudokuPanel.setGame(game);
		sudokuPanel.setController(sudokuController);
		add(sudokuPanel, BorderLayout.CENTER);

		game.addObserver(buttonPanel);
		game.addObserver(sudokuPanel);

		pack();

	}

	/* Main entry point of program. */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		SudokuMain s = new SudokuMain();
		s.setDefaultCloseOperation(EXIT_ON_CLOSE);
		s.setLocationRelativeTo(null);
		s.setVisible(true);
	}
}
