package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class SudokuButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1417479360311492704L;
	private static final int SQUARE_SIZE = 30;
	private int x;
	private int y;

	public SudokuButton(String text, int x, int y) {
		super(text);
		this.x = x;
		this.y = y;
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, false));
		this.setSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		setPattern(this, x, y);
	}

	// Could have here more setPattern methods, so the problem of "more patters"
	// is already solved//
	private void setPattern(JButton gameButton, int i, int j) {
		if (j == 0 || j == 8) {
			gameButton.setText(" ");
			gameButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
			gameButton.setActionCommand("Alterable");
		} else if ((j == 1 || j == 7) && (i == 0 || i == 2 || i == 3 || i == 5 || i == 6 || i == 8)) {
			gameButton.setText(" ");
			gameButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
			gameButton.setActionCommand("Alterable");
		} else if ((j == 2 || j == 6) && (i == 0 || i == 1 || i == 4 || i == 7 || i == 8)) {
			gameButton.setText(" ");
			gameButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
			gameButton.setActionCommand("Alterable");
		} else if ((j == 3 || j == 5) && (i == 2 || i == 4 || i == 6)) {
			gameButton.setText(" ");
			gameButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
			gameButton.setActionCommand("Alterable");
		} else if ((j == 4) && (i == 0 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 8)) {
			gameButton.setText(" ");
			gameButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
			gameButton.setActionCommand("Alterable");
		}
	}

	public int returnX() {
		return x;
	}

	public int returnY() {
		return y;
	}
}
