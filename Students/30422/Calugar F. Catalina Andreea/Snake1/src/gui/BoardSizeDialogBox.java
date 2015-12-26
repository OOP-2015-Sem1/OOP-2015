package gui;

import static entities.Constants.DEFAULT_BOARD_SIZE;
import static entities.Constants.MAX_BOARD_SIZE;
import static entities.Constants.MIN_BOARD_SIZE;

import javax.swing.JOptionPane;

public class BoardSizeDialogBox {

	public static int queryForBoardSize() {
		String size = JOptionPane
				.showInputDialog(String.format("Board dimension: (%d-%d)", MIN_BOARD_SIZE, MAX_BOARD_SIZE));

		Integer boardSize;
		try {
			boardSize = Integer.parseInt(size);
			if (boardSize < MIN_BOARD_SIZE || boardSize > MAX_BOARD_SIZE)
				boardSize = DEFAULT_BOARD_SIZE;
		} catch (NumberFormatException e) {
			boardSize = DEFAULT_BOARD_SIZE;
		}
		return boardSize;
	}
}
