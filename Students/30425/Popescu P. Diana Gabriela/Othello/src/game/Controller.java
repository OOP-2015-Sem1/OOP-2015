package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.DimensionOfBoardFrame;
import io.OthelloFrame;
import methods.constants.auxiliary.Constants;
import methods.constants.auxiliary.ThereExists;

public class Controller implements ActionListener {

	private OthelloFrame frame;
	private DimensionOfBoardFrame dimensionOfBoardFrame;
	private BoardConfiguration boardConfiguration;
	private int scoreWhite;
	private int scoreBlack;
	private int nrGreen;
	private int currentPlayer;
	private int oppositePlayer;
	private ThereExists thereExists;

	public Controller(DimensionOfBoardFrame dimensionOfBoardFrame) {

		this.dimensionOfBoardFrame = dimensionOfBoardFrame;
		dimensionOfBoardFrame.okButton.addActionListener(this);

		thereExists = new ThereExists();
		scoreWhite = 2;
		scoreBlack = 2;

		currentPlayer = Constants.WHITE; // white starts
		oppositePlayer = Constants.BLACK;
	}

	public void actionPerformed(ActionEvent event) {

		if (currentPlayer != Constants.GAME_OVER) {
			Object source = event.getSource();
			boolean sourceFound = false;

			if (source == dimensionOfBoardFrame.okButton) {
				sourceFound = true;
				dimensionOfBoardFrame.dFrame.setVisible(false);
				this.frame = new OthelloFrame(dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS,
						dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS);
				for (int i = 0; i < dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS; i++) {
					for (int j = 0; j < dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS; j++) {
						this.frame.buttonsForGame[i][j].addActionListener((ActionListener) this);
					}
				}
				this.boardConfiguration = new BoardConfiguration(dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS,
						dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS);
				nrGreen = dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS * dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS
						- scoreWhite - scoreBlack;
				frame.updateOthelloBoard(boardConfiguration.getBoard(), scoreWhite, scoreBlack, currentPlayer);
				// dimensionOfBoardFrame.dispose();
			}

			else if (!sourceFound) {

				for (int i = 0; i < dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS && sourceFound == false; i++) {
					for (int j = 0; j < dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS && sourceFound == false; j++) {
						if (source == frame.buttonsForGame[i][j]) {
							sourceFound = true;
							if (thereExists.thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(
									boardConfiguration, i, j, currentPlayer,
									dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS,
									dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS)) {
								modifyBoardForCurrentAllowedPosition(i, j, currentPlayer,
										dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS,
										dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS);
								calculateScores(dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS,
										dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS);
								if (thereExists.thereExistsAtLeastOnePossibleMoveForThisColor(boardConfiguration,
										oppositePlayer, dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS,
										dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS)) {
									swapPlayers();
								} else {
									if (!thereExists.thereExistsAtLeastOnePossibleMoveForThisColor(boardConfiguration,
											currentPlayer, dimensionOfBoardFrame.DIMENSION_OF_BOARD_ROWS,
											dimensionOfBoardFrame.DIMENSION_OF_BOARD_COLS)) {
										currentPlayer = Constants.GAME_OVER;
										frame.writeTheResultOnFrame(nrGreen, scoreWhite, scoreBlack);
									}
								}
								frame.updateOthelloBoard(boardConfiguration.getBoard(), scoreWhite, scoreBlack,
										currentPlayer);
							}
						}
					}
				}
			}
		}
	}

	private void modifyBoardForCurrentAllowedPosition(int i, int j, int colorToBePut, int ROWS, int COLS) {

		int colorTheNeighborShouldBe;
		if (colorToBePut == Constants.WHITE) {
			colorTheNeighborShouldBe = Constants.BLACK;
		} else {
			colorTheNeighborShouldBe = Constants.WHITE;
		}

		boardConfiguration.modifyPositionOnBoard(i, j, colorToBePut);
		nrGreen--;

		// horizontal right
		if (thereExists.thereExistChangeableTokensOnHorizontalRight(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut, ROWS, COLS)) {
			modifyHorizontalRight(i, j, colorToBePut);
		}

		// horizontal left
		if (thereExists.thereExistChangeableTokensOnHorizontalLeft(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut)) {
			modifyHorizontalLeft(i, j, colorToBePut);
		}
		// vertical up
		if (thereExists.thereExistChangeableTokensOnVerticalUp(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut)) {
			modifyVerticalUp(i, j, colorToBePut);
		}
		// vertical down
		if (thereExists.thereExistChangeableTokensOnVerticalDown(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut, ROWS, COLS)) {
			modifyVerticalDown(i, j, colorToBePut);
		}
		// leading diagonal right
		if (thereExists.thereExistChangeableTokensOnLeadingRight(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut, ROWS, COLS)) {
			modifyLeadingRight(i, j, colorToBePut);
		}
		// leading diagonal left
		if (thereExists.thereExistChangeableTokensOnLeadingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut)) {
			modifyLeadingLeft(i, j, colorToBePut);
		}
		// trailing diagonal right
		if (thereExists.thereExistChangeableTokensOnTrailingRight(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut, ROWS, COLS)) {
			modifyTrailingRight(i, j, colorToBePut);
		}
		// trailing diagonal left
		if (thereExists.thereExistChangeableTokensOnTrailingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe,
				colorToBePut, ROWS, COLS)) {
			modifyTrailingLeft(i, j, colorToBePut);
		}
	}

	protected void modifyHorizontalRight(int i, int j, int colorToBePut) {

		int searchTheOtherColor = j + 1;
		while (boardConfiguration.getBoard()[i][searchTheOtherColor] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(i, searchTheOtherColor, colorToBePut);
			searchTheOtherColor++;
		}
	}

	protected void modifyHorizontalLeft(int i, int j, int colorToBePut) {
		int searchTheOtherColor = j - 1;
		while (boardConfiguration.getBoard()[i][searchTheOtherColor] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(i, searchTheOtherColor, colorToBePut);
			searchTheOtherColor--;
		}
	}

	protected void modifyVerticalUp(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i - 1;
		while (boardConfiguration.getBoard()[searchTheOtherColor][j] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, j, colorToBePut);
			searchTheOtherColor--;
		}
	}

	protected void modifyVerticalDown(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i + 1;
		while (boardConfiguration.getBoard()[searchTheOtherColor][j] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, j, colorToBePut);
			searchTheOtherColor++;
		}
	}

	protected void modifyLeadingRight(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i + 1;
		int searchTheOtherColorSecond = j + 1;
		while (boardConfiguration.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, searchTheOtherColorSecond, colorToBePut);
			searchTheOtherColor++;
			searchTheOtherColorSecond++;
		}
	}

	protected void modifyLeadingLeft(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i - 1;
		int searchTheOtherColorSecond = j - 1;
		while (boardConfiguration.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, searchTheOtherColorSecond, colorToBePut);
			searchTheOtherColor--;
			searchTheOtherColorSecond--;
		}
	}

	protected void modifyTrailingRight(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i - 1;
		int searchTheOtherColorSecond = j + 1;
		while (boardConfiguration.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, searchTheOtherColorSecond, colorToBePut);
			searchTheOtherColor--;
			searchTheOtherColorSecond++;
		}
	}

	protected void modifyTrailingLeft(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i + 1;
		int searchTheOtherColorSecond = j - 1;
		while (boardConfiguration.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] != colorToBePut) {
			boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, searchTheOtherColorSecond, colorToBePut);
			searchTheOtherColor++;
			searchTheOtherColorSecond--;
		}
	}

	private void calculateScores(int ROWS, int COLS) {
		scoreWhite = calculateScoreForColor(Constants.WHITE, ROWS, COLS);
		// scoreBlack = calculateScoreForColor(Constants.BLACK, ROWS, COLS);
		scoreBlack = ROWS * COLS - scoreWhite - nrGreen;
	}

	private void swapPlayers() {
		int aux = currentPlayer;
		currentPlayer = oppositePlayer;
		oppositePlayer = aux;
	}

	private int calculateScoreForColor(int color, int ROWS, int COLS) {
		int score = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (boardConfiguration.getBoard()[i][j] == color) {
					score++;
				}
			}
		}
		return score;
	}
}