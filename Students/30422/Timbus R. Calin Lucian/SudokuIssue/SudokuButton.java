package view;

import javax.swing.JButton;

public class SudokuButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1417479360311492704L;
	private int x, y;
	private String text;

	public SudokuButton(String text, int x, int y) {
		super(text);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

}
