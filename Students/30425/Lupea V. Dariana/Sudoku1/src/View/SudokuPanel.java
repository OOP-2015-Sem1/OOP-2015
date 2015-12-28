package View;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/*This class draws the sudoku panel */

public class SudokuPanel extends JPanel {

	private Field[][] fields; // Array of fields.
	private JPanel[][] panels; // Panels holding the fields.

	/*
	 * Constructs the panel, adds sub panels and adds fields to these sub
	 * panels.
	 */
	public SudokuPanel() {
		super(new GridLayout(3, 3));

		panels = new JPanel[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				panels[y][x] = new JPanel(new GridLayout(3, 3));
				panels[y][x].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				add(panels[y][x]);
			}
		}

		fields = new Field[9][9];
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				fields[y][x] = new Field(x, y);
				panels[y / 3][x / 3].add(fields[y][x]);
			}
		}
	}
}