package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.OthelloFrame;

public class Controller implements ActionListener {

	OthelloFrame frame;
	BoardConfiguration boardConfiguration;
	private int scoreWhite;
	private int scoreBlack;
	private int nrGreen;
	private int currentPlayer;
	private int oppositePlayer;

	public Controller(OthelloFrame othelloFrame, BoardConfiguration boardConfiguration) {
		this.frame = othelloFrame;
		this.boardConfiguration = boardConfiguration;
		scoreWhite = 2;
		scoreBlack = 2;
		nrGreen = BoardConfiguration.ROWS * BoardConfiguration.COLS - scoreWhite - scoreBlack;
		currentPlayer = BoardConfiguration.WHITE; // white starts
		oppositePlayer = BoardConfiguration.BLACK;
		frame.updateOthelloBoard(boardConfiguration.getBoard(), scoreWhite, scoreBlack, currentPlayer);

		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				this.frame.buttonsForGame[i][j].addActionListener((ActionListener) this);
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		boolean sourceFound = false;
		for (int i = 0; i < 8 && sourceFound == false; i++) {
			for (int j = 0; j < 8 && sourceFound == false; j++) {
				if (source == frame.buttonsForGame[i][j]) {
					sourceFound = true;
					if (thereExistsAtLeastOnePossibleMoveForRandomColor()) {
						System.out.println("OK for RandomColor");
						if (thereExistsAtLeastOnePossibleMoveForThisColor(currentPlayer)) {
							System.out.println("OK for ThisColor");
							if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(i, j, currentPlayer)) {
								System.out.println("OK for ThisColor And ThisPosition");
								modifyBoardForCurrentAllowedPosition(i, j, currentPlayer);
								System.out.println("currentPlayer" + currentPlayer);
								calculateScoresAndSwapPlayers();
								System.out.println("currentPlayer" + currentPlayer);
								frame.updateOthelloBoard(boardConfiguration.getBoard(), scoreWhite, scoreBlack,
										currentPlayer);
								System.out.println("nr GREEN" + nrGreen);
							}
						} else {
							swapPlayers();
							frame.updateOthelloBoard(boardConfiguration.getBoard(), scoreWhite, scoreBlack,
									currentPlayer);
						}
					} else {
						frame.writeTheResultOnFrame(nrGreen, scoreWhite, scoreBlack);
						frame.setLabelForTurn(5); // random integer: game over
					}
				}
			}
		}
	}

	/*
	 * public void continueOrFinishGame() { // condition finished, blocked both,
	 * continue-possible moves, // continue-the other ...??? if (nrGreen == 0) {
	 * showTheResultIfNoFreeCellOrBothBlocked(); } else {
	 * if(!thereExistsAtLeastOnePossibleMoveForRandomColor()) {
	 * showTheResultIfNoFreeCellOrBothBlocked(); } else {
	 *
	 * } } }
	 */
	private boolean thereExistsAtLeastOnePossibleMoveForRandomColor() {
		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(i, j, BoardConfiguration.WHITE))
					return true;
				if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(i, j, BoardConfiguration.BLACK))
					return true;
			}
		}
		return false;
	}

	private boolean thereExistsAtLeastOnePossibleMoveForThisColor(int color) {
		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(i, j, color))
					return true;
			}
		}
		return false;
	}

	private boolean thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(int i, int j, int colorToBePut) {

		if (isGreenAndHasAtLeastOneNeighborOfOppositeColor(i, j, colorToBePut)) {
			int colorTheNeighborShouldBe;
			if (colorToBePut == BoardConfiguration.WHITE) {
				colorTheNeighborShouldBe = BoardConfiguration.BLACK;
			} else {
				colorTheNeighborShouldBe = BoardConfiguration.WHITE;
			}
			int searchTheOtherColor;
			int searchTheOtherColorSecond;

			// horizontal right
			if (j < BoardConfiguration.COLS - 2
					&& boardConfiguration.getBoard()[i][j + 1] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i][j
				// + 1];
				searchTheOtherColor = j + 2;
				while (searchTheOtherColor < BoardConfiguration.COLS
						&& boardConfiguration.getBoard()[i][searchTheOtherColor] == colorTheNeighborShouldBe) {
					searchTheOtherColor++;
				}
				if (boardConfiguration.getBoard()[i][searchTheOtherColor] != BoardConfiguration.GREEN
						&& searchTheOtherColor < BoardConfiguration.COLS) {
					return true;
				}
			}
			// horizontal left
			if (j > BoardConfiguration.COLS - 7
					&& boardConfiguration.getBoard()[i][j - 1] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i][j
				// - 1];
				searchTheOtherColor = j - 2;
				while (searchTheOtherColor >= 0
						&& boardConfiguration.getBoard()[i][searchTheOtherColor] == colorTheNeighborShouldBe) {
					searchTheOtherColor--;
				}
				if (boardConfiguration.getBoard()[i][searchTheOtherColor] != BoardConfiguration.GREEN
						&& searchTheOtherColor >= 0) {
					return true;
				}
			}
			// vertical up
			if (i > BoardConfiguration.ROWS - 7
					&& boardConfiguration.getBoard()[i - 1][j] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i -
				// 1][j];
				searchTheOtherColor = i - 2;
				while (searchTheOtherColor >= 0
						&& boardConfiguration.getBoard()[searchTheOtherColor][j] == colorTheNeighborShouldBe) {
					searchTheOtherColor--;
				}
				if (boardConfiguration.getBoard()[searchTheOtherColor][j] != BoardConfiguration.GREEN
						&& searchTheOtherColor >= 0) {
					return true;
				}
			}
			// vertical down
			if (i < BoardConfiguration.ROWS - 2
					&& boardConfiguration.getBoard()[i + 1][j] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i +
				// 1][j];
				searchTheOtherColor = i + 2;
				while (searchTheOtherColor < BoardConfiguration.ROWS
						&& boardConfiguration.getBoard()[searchTheOtherColor][j] == colorTheNeighborShouldBe) {
					searchTheOtherColor++;
				}
				if (boardConfiguration.getBoard()[searchTheOtherColor][j] != BoardConfiguration.GREEN
						&& searchTheOtherColor < BoardConfiguration.ROWS) {
					return true;
				}
			}
			// leading diagonal left
			if (i > 1 && j > 1 && boardConfiguration.getBoard()[i - 1][j - 1] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i -
				// 1][j - 1];
				searchTheOtherColor = i - 2;
				searchTheOtherColorSecond = j - 2;
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
			// leading diagonal right
			if (i < BoardConfiguration.ROWS - 2 && j < BoardConfiguration.COLS - 2
					&& boardConfiguration.getBoard()[i + 1][j + 1] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i +
				// 1][j + 1];
				searchTheOtherColor = i + 2;
				searchTheOtherColorSecond = j + 2;
				while (searchTheOtherColor < BoardConfiguration.ROWS
						&& searchTheOtherColorSecond < BoardConfiguration.COLS && boardConfiguration
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
			// trailing diagonal left
			if (i < BoardConfiguration.ROWS - 2 && j > 1
					&& boardConfiguration.getBoard()[i + 1][j - 1] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i +
				// 1][j - 1];
				searchTheOtherColor = i + 2;
				searchTheOtherColorSecond = j - 2;
				while (searchTheOtherColor < BoardConfiguration.ROWS && searchTheOtherColorSecond >= 0
						&& boardConfiguration
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
			// trailing diagonal right
			if (i > 1 && j < BoardConfiguration.COLS - 2
					&& boardConfiguration.getBoard()[i - 1][j + 1] == colorTheNeighborShouldBe) {
				// colorTheNeighborShouldBe = boardConfiguration.getBoard()[i -
				// 1][j + 1];
				searchTheOtherColor = i - 2;
				searchTheOtherColorSecond = j + 2;
				while (searchTheOtherColor >= 0 && searchTheOtherColorSecond < BoardConfiguration.COLS
						&& boardConfiguration
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
		}
		return false;
	}

	private boolean isGreenAndHasAtLeastOneNeighborOfOppositeColor(int i, int j, int colorToBePut) {
		if (boardConfiguration.getBoard()[i][j] == BoardConfiguration.GREEN) {
			if (greenHasAtLeastOneNeighborOfOppositeColor(i, j, colorToBePut)) {
				return true;
			}
		}
		return false;
	}

	private boolean greenHasAtLeastOneNeighborOfOppositeColor(int i, int j, int colorToBePut) {
		for (int aux1 = 0; aux1 < 3; aux1++) {
			for (int aux2 = 0; aux2 < 3; aux2++) {
				if ((i - 1 + aux1 >= 0 && i - 1 + aux1 < BoardConfiguration.ROWS)
						&& (j - 1 + aux2 >= 0 && j - 1 + aux2 < BoardConfiguration.COLS) && (aux1 != i && aux2 != j)) {
					if (boardConfiguration.getBoard()[i - 1 + aux1][j - 1 + aux2] != BoardConfiguration.GREEN
							&& boardConfiguration.getBoard()[i - 1 + aux1][j - 1 + aux2] != colorToBePut) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void modifyBoardForCurrentAllowedPosition(int i, int j, int colorToBePut) {

		int colorTheNeighborShouldBe;
		if (colorToBePut == BoardConfiguration.WHITE) {
			colorTheNeighborShouldBe = BoardConfiguration.BLACK;
		} else {
			colorTheNeighborShouldBe = BoardConfiguration.WHITE;
		}
		int searchTheOtherColor;
		int searchTheOtherColorSecond;
		/*
		 * boolean horizontalRight; boolean horizontalLeft; boolean verticalUp;
		 * boolean verticalDown; boolean leadingRight; boolean leadingLeft;
		 * boolean trailingRight; boolean trailingLeft;
		 */
		boardConfiguration.modifyPositionOnBoard(i, j, colorToBePut);
		nrGreen--;

		// horizontal right
		if (j < BoardConfiguration.COLS - 2 && boardConfiguration.getBoard()[i][j + 1] == colorTheNeighborShouldBe) {
			searchTheOtherColor = j + 2;
			while (searchTheOtherColor < BoardConfiguration.COLS
					&& boardConfiguration.getBoard()[i][searchTheOtherColor] == colorTheNeighborShouldBe) {
				searchTheOtherColor++;
			}
			if (boardConfiguration.getBoard()[i][searchTheOtherColor] != BoardConfiguration.GREEN
					&& searchTheOtherColor < BoardConfiguration.COLS) {
				// horizontalRight = true;
				for (int aux = j + 1; aux != searchTheOtherColor; aux++) {
					boardConfiguration.modifyPositionOnBoard(i, aux, colorToBePut);
				}
			}
		}
		// horizontal left
		if (j > BoardConfiguration.COLS - 7 && boardConfiguration.getBoard()[i][j - 1] == colorTheNeighborShouldBe) {
			searchTheOtherColor = j - 2;
			while (searchTheOtherColor >= 0
					&& boardConfiguration.getBoard()[i][searchTheOtherColor] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
			}
			if (boardConfiguration.getBoard()[i][searchTheOtherColor] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0) {
				// horizontalLeft = true;
				for (int aux = j - 1; aux != searchTheOtherColor; aux--) {
					boardConfiguration.modifyPositionOnBoard(i, aux, colorToBePut);
				}
			}
		}
		// vertical up
		if (i > BoardConfiguration.ROWS - 7 && boardConfiguration.getBoard()[i - 1][j] == colorTheNeighborShouldBe) {

			searchTheOtherColor = i - 2;
			while (searchTheOtherColor >= 0
					&& boardConfiguration.getBoard()[searchTheOtherColor][j] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
			}
			if (boardConfiguration.getBoard()[searchTheOtherColor][j] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0) {
				// verticalUp = true;
				for (int aux = i - 1; aux != searchTheOtherColor; aux--) {
					boardConfiguration.modifyPositionOnBoard(aux, j, colorToBePut);
				}
			}
		}
		// vertical down
		if (i < BoardConfiguration.ROWS - 2 && boardConfiguration.getBoard()[i + 1][j] == colorTheNeighborShouldBe) {
			searchTheOtherColor = i + 2;
			while (searchTheOtherColor < BoardConfiguration.ROWS
					&& boardConfiguration.getBoard()[searchTheOtherColor][j] == colorTheNeighborShouldBe) {
				searchTheOtherColor++;
			}
			if (boardConfiguration.getBoard()[searchTheOtherColor][j] != BoardConfiguration.GREEN
					&& searchTheOtherColor < BoardConfiguration.ROWS) {
				// verticalDown = true;
				for (int aux = i + 1; aux != searchTheOtherColor; aux++) {
					boardConfiguration.modifyPositionOnBoard(aux, j, colorToBePut);
				}
			}
		}
		// leading diagonal left
		if (i > 1 && j > 1 && boardConfiguration.getBoard()[i - 1][j - 1] == colorTheNeighborShouldBe) {
			searchTheOtherColor = i - 2;
			searchTheOtherColorSecond = j - 2;
			while (searchTheOtherColor >= 0 && searchTheOtherColorSecond >= 0 && boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
				searchTheOtherColorSecond--;
			}
			if (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0 && searchTheOtherColorSecond >= 0) {
				// leadingLeft = true;
				for (int aux = 0; i - 1 - aux != searchTheOtherColor; aux++) {
					boardConfiguration.modifyPositionOnBoard(i - 1 - aux, j - 1 - aux, colorToBePut);
				}
			}
		}
		// leading diagonal right
		if (i < BoardConfiguration.ROWS - 2 && j < BoardConfiguration.COLS - 2
				&& boardConfiguration.getBoard()[i + 1][j + 1] == colorTheNeighborShouldBe) {
			searchTheOtherColor = i + 2;
			searchTheOtherColorSecond = j + 2;
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
				// leadingRight = true;
				for (int aux = 0; i + 1 + aux != searchTheOtherColor; aux++) {
					boardConfiguration.modifyPositionOnBoard(i + 1 + aux, j + 1 + aux, colorToBePut);
				}
			}
		}
		// trailing diagonal left
		if (i < BoardConfiguration.ROWS - 2 && j > 1
				&& boardConfiguration.getBoard()[i + 1][j - 1] == colorTheNeighborShouldBe) {
			searchTheOtherColor = i + 2;
			searchTheOtherColorSecond = j - 2;
			while (searchTheOtherColor < BoardConfiguration.ROWS && searchTheOtherColorSecond >= 0 && boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] == colorTheNeighborShouldBe) {
				searchTheOtherColor++;
				searchTheOtherColorSecond--;
			}
			if (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != BoardConfiguration.GREEN
					&& searchTheOtherColor < BoardConfiguration.ROWS && searchTheOtherColorSecond >= 0) {
				// trailingLeft = true;
				for (int aux = 0; i + 1 + aux != searchTheOtherColor; aux++) {
					boardConfiguration.modifyPositionOnBoard(i + 1 + aux, j - 1 - aux, colorToBePut);
				}
			}
		}
		// trailing diagonal right
		if (i > 1 && j < BoardConfiguration.COLS - 2
				&& boardConfiguration.getBoard()[i - 1][j + 1] == colorTheNeighborShouldBe) {
			searchTheOtherColor = i - 2;
			searchTheOtherColorSecond = j + 2;
			while (searchTheOtherColor >= 0 && searchTheOtherColorSecond < BoardConfiguration.COLS && boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] == colorTheNeighborShouldBe) {
				searchTheOtherColor--;
				searchTheOtherColorSecond++;
			}
			if (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != BoardConfiguration.GREEN
					&& searchTheOtherColor >= 0 && searchTheOtherColorSecond < BoardConfiguration.COLS) {
				// trailingRight = true;
				for (int aux = 0; i - 1 - aux != searchTheOtherColor; aux++) {
					boardConfiguration.modifyPositionOnBoard(i - 1 - aux, j + 1 + aux, colorToBePut);
				}
			}
		}
	}

	private void calculateScoresAndSwapPlayers() {
		scoreWhite = calculateScoreForColor(BoardConfiguration.WHITE);
		// scoreBlack = calculateScoreForColor(BoardConfiguration.BLACK);
		scoreBlack = BoardConfiguration.ROWS * BoardConfiguration.COLS - scoreWhite - nrGreen;
		swapPlayers();
	}

	private void swapPlayers() {
		int aux = currentPlayer;
		currentPlayer = oppositePlayer;
		oppositePlayer = aux;
	}

	private int calculateScoreForColor(int color) {
		int score = 0;
		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				if (boardConfiguration.getBoard()[i][j] == color) {
					score++;
				}
			}
		}
		return score;
	}
}
/*
 * public void actionPerformed(ActionEvent e) { if (e.getSource() == c[0][1]) {
 * mSn(Constants.UP); } if (e.getSource() == c[1][1]) { mSn(Constants.STEP); }
 * if (e.getSource() == c[1][2]) { mSn(Constants.RIGHT); } if (e.getSource() ==
 * c[1][0]) { mSn(Constants.LEFT); } if (e.getSource() == c[2][1]) {
 * mSn(Constants.DOWN); } }
 */
