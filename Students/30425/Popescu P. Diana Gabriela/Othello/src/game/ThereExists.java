package game;

public class ThereExists {
/*
	protected boolean thereExistsAtLeastOnePossibleMoveForRandomColor(BoardConfiguration boardConfiguration) {
		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(boardConfiguration, i, j,
						BoardConfiguration.WHITE))
					return true;
				if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(boardConfiguration, i, j,
						BoardConfiguration.BLACK))
					return true;
			}
		}
		return false;
	}
*/
	protected boolean thereExistsAtLeastOnePossibleMoveForThisColor(BoardConfiguration boardConfiguration, int color) {
		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(boardConfiguration, i, j, color))
					return true;
			}
		}
		return false;
	}

	protected boolean thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(
			BoardConfiguration boardConfiguration, int i, int j, int colorToBePut) {

		if (isGreenAndHasAtLeastOneNeighborOfOppositeColor(boardConfiguration, i, j, colorToBePut)) {
			int colorTheNeighborShouldBe;
			if (colorToBePut == BoardConfiguration.WHITE) {
				colorTheNeighborShouldBe = BoardConfiguration.BLACK;
			} else {
				colorTheNeighborShouldBe = BoardConfiguration.WHITE;
			}
			// horizontal right
			if (thereExistChangeableTokensOnHorizontalRight(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("horizontalRight OK");
				return true;
			}
			// horizontal left
			if (thereExistChangeableTokensOnHorizontalLeft(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("horizontalLeft OK");
				return true;
			}
			// vertical up
			if (thereExistChangeableTokensOnVerticalUp(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("verticalUp OK");
				return true;
			}
			// vertical down
			if (thereExistChangeableTokensOnVerticalDown(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("verticalDown OK");
				return true;
			}
			// leading diagonal right
			if (thereExistChangeableTokensOnLeadingRight(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("leadingRight OK");
				return true;
			}
			// leading diagonal left
			if (thereExistChangeableTokensOnLeadingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("leadingLeft OK");
				return true;
			}
			// trailing diagonal right
			if (thereExistChangeableTokensOnTrailingRight(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("trailingRight OK");
				return true;
			}
			// trailing diagonal left
			if (thereExistChangeableTokensOnTrailingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
				System.out.println("trailingLeft OK");
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnHorizontalRight(BoardConfiguration boardConfiguration, int i, int j,
			int colorTheNeighborShouldBe) {
		if (j < BoardConfiguration.COLS - 2 && boardConfiguration.getBoard()[i][j + 1] == colorTheNeighborShouldBe) {
			int searchTheOtherColor = j + 2;
			while (searchTheOtherColor < BoardConfiguration.COLS
					&& boardConfiguration.getBoard()[i][searchTheOtherColor] == colorTheNeighborShouldBe) {
				searchTheOtherColor++;
			}
			if (boardConfiguration.getBoard()[i][searchTheOtherColor] != BoardConfiguration.GREEN
					&& searchTheOtherColor < BoardConfiguration.COLS) {
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnHorizontalLeft(BoardConfiguration boardConfiguration, int i, int j,
			int colorTheNeighborShouldBe) {
		if (j > 1 && boardConfiguration.getBoard()[i][j - 1] == colorTheNeighborShouldBe) {
			int searchTheOtherColor = j - 2;
			while (searchTheOtherColor >= 0
					&& boardConfiguration.getBoard()[i][searchTheOtherColor] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
			}
			if (boardConfiguration.getBoard()[i][searchTheOtherColor] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0) {
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnVerticalUp(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe) {
		if (i > 1 && boardConfiguration.getBoard()[i - 1][j] == colorTheNeighborShouldBe) {
			int searchTheOtherColor = i - 2;
			while (searchTheOtherColor >= 0
					&& boardConfiguration.getBoard()[searchTheOtherColor][j] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
			}
			if (boardConfiguration.getBoard()[searchTheOtherColor][j] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0) {
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnVerticalDown(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe) {
		if (i < BoardConfiguration.ROWS - 2 && boardConfiguration.getBoard()[i + 1][j] == colorTheNeighborShouldBe) {
			int searchTheOtherColor = i + 2;
			while (searchTheOtherColor < BoardConfiguration.ROWS
					&& boardConfiguration.getBoard()[searchTheOtherColor][j] == colorTheNeighborShouldBe) {
				searchTheOtherColor++;
			}
			if (boardConfiguration.getBoard()[searchTheOtherColor][j] != BoardConfiguration.GREEN
					&& searchTheOtherColor < BoardConfiguration.ROWS) {
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnLeadingRight(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe) {
		if (i < BoardConfiguration.ROWS - 2 && j < BoardConfiguration.COLS - 2
				&& boardConfiguration.getBoard()[i + 1][j + 1] == colorTheNeighborShouldBe) {
			// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i +
			// 1][j + 1];
			int searchTheOtherColor = i + 2;
			int searchTheOtherColorSecond = j + 2;
			while (searchTheOtherColor < BoardConfiguration.ROWS && searchTheOtherColorSecond < BoardConfiguration.COLS
					&& boardConfiguration
							.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] == colorTheNeighborShouldBe) {
				searchTheOtherColor++;
				searchTheOtherColorSecond++;
			}
			if (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != BoardConfiguration.GREEN
					&& searchTheOtherColor < BoardConfiguration.ROWS
					&& searchTheOtherColorSecond < BoardConfiguration.COLS) {
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnLeadingLeft(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe) {
		if (i > 1 && j > 1 && boardConfiguration.getBoard()[i - 1][j - 1] == colorTheNeighborShouldBe) {
			int searchTheOtherColor = i - 2;
			int searchTheOtherColorSecond = j - 2;
			while (searchTheOtherColor >= 0 && searchTheOtherColorSecond >= 0 && boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
				searchTheOtherColorSecond--;
			}
			if (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0 && searchTheOtherColorSecond >= 0) {
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnTrailingRight(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe) {
		if (i > 1 && j < BoardConfiguration.COLS - 2
				&& boardConfiguration.getBoard()[i - 1][j + 1] == colorTheNeighborShouldBe) {
			// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i -
			// 1][j + 1];
			int searchTheOtherColor = i - 2;
			int searchTheOtherColorSecond = j + 2;
			while (searchTheOtherColor >= 0 && searchTheOtherColorSecond < BoardConfiguration.COLS && boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
				searchTheOtherColorSecond++;
			}
			if (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0 && searchTheOtherColorSecond < BoardConfiguration.COLS) {
				return true;
			}
		}
		return false;
	}

	protected boolean thereExistChangeableTokensOnTrailingLeft(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe) {
		if (i < BoardConfiguration.ROWS - 2 && j > 1
				&& boardConfiguration.getBoard()[i + 1][j - 1] == colorTheNeighborShouldBe) {
			// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i +
			// 1][j - 1];
			int searchTheOtherColor = i + 2;
			int searchTheOtherColorSecond = j - 2;
			while (searchTheOtherColor < BoardConfiguration.ROWS && searchTheOtherColorSecond >= 0 && boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] == colorTheNeighborShouldBe) {
				searchTheOtherColor++;
				searchTheOtherColorSecond--;
			}
			if (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != BoardConfiguration.GREEN
					&& searchTheOtherColor < BoardConfiguration.ROWS && searchTheOtherColorSecond >= 0) {
				return true;
			}
		}
		return false;
	}

	protected boolean isGreenAndHasAtLeastOneNeighborOfOppositeColor(BoardConfiguration boardConfiguration, int i,
			int j, int colorToBePut) {
		if (boardConfiguration.getBoard()[i][j] == BoardConfiguration.GREEN) {
			if (greenHasAtLeastOneNeighborOfOppositeColor(boardConfiguration, i, j, colorToBePut)) {
				return true;
			}
		}
		return false;
	}

	protected boolean greenHasAtLeastOneNeighborOfOppositeColor(BoardConfiguration boardConfiguration, int i, int j,
			int colorToBePut) {
		int positionI;
		int positionJ;
		for (int aux1 = 0; aux1 < 3; aux1++) {
			for (int aux2 = 0; aux2 < 3; aux2++) {
				positionI=i-1+aux1;
				positionJ=j-1+aux2;
				if ((positionI >= 0 && positionI < BoardConfiguration.ROWS)
						&& (positionJ >= 0 && positionJ < BoardConfiguration.COLS) && !(positionI==i && positionJ==j)) {
					if (boardConfiguration.getBoard()[positionI][positionJ] != BoardConfiguration.GREEN
							&& boardConfiguration.getBoard()[positionI][positionJ] != colorToBePut) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
