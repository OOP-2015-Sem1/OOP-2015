package com.snake.ui;

import javax.swing.JOptionPane;
import static com.snake.core.Constants.*;

public class SettingsDialogs {

	public static int queryForBoardSize() {
		String size = JOptionPane.showInputDialog(String.format(
				"Board dimension: (%d-%d)", MIN_BOARD_SIZE, MAX_BOARD_SIZE));

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

	public static boolean queryForGoingThroughWalls() {
		int reply = JOptionPane.showConfirmDialog(null,
				"Should it go through walls?", "Options",
				JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}

}
