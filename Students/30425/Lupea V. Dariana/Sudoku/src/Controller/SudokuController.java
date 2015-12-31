package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import Model.Game;
import Model.UpdateAction;
import View.Field;
import View.SudokuPanel;

/*This class controls all user actions from SudokuPanel.*/

public class SudokuController implements MouseListener {
	private SudokuPanel sudokuPanel; // Panel to control.
	private Game game; // Current Sudoku game.

	/* Constructor, sets game */
	public SudokuController(SudokuPanel sudokuPanel, Game game) {
		this.sudokuPanel = sudokuPanel;
		this.game = game;
	}

	/*
	 * Recovers if user clicked field in game. If so it sets the selected number
	 * at clicked position in game and updates clicked field. If user clicked a
	 * field and used left mouse button, number at clicked position will be
	 * cleared in game and clicked field will be updated.
	 */
	public void mousePressed(MouseEvent event) {
		JPanel panel = (JPanel) event.getSource();// object on which event
													// occurred
		Component component = panel.getComponentAt(event.getPoint());
		if (component instanceof Field) {
			Field field = (Field) component;
			int x = field.getFieldX();
			int y = field.getFieldY();

			if (event.getButton() == MouseEvent.BUTTON1
					&& (game.getNumber(x, y) == 0 || field.getForeground().equals(Color.BLUE))) {// if
																									// blank
				int number = game.getSelectedNumber();
				if (number == -1)
					return;
				game.setNumber(x, y, number);// put the number in the field
				field.setNumber(number, true);
			} else if (event.getButton() == MouseEvent.BUTTON3 && !field.getForeground().equals(Color.BLACK)) {
				game.setNumber(x, y, 0);
				field.setNumber(0, false);
			}
			sudokuPanel.update(game, UpdateAction.CANDIDATES);
		}
	}

	// methods not used
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
