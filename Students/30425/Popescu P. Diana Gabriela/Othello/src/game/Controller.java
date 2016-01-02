package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.OthelloFrame;

public class Controller extends ThereExists implements ActionListener {

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
		if (currentPlayer != BoardConfiguration.GAME_OVER) {
			Object source = event.getSource();
			boolean sourceFound = false;
			for (int i = 0; i < 8 && sourceFound == false; i++) {
				for (int j = 0; j < 8 && sourceFound == false; j++) {
					if (source == frame.buttonsForGame[i][j]) {
						sourceFound = true;
						if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(boardConfiguration, i, j,
								currentPlayer)) {
							modifyBoardForCurrentAllowedPosition(i, j, currentPlayer);
							calculateScores();
							if (thereExistsAtLeastOnePossibleMoveForThisColor(boardConfiguration, oppositePlayer)) {
								swapPlayers();
							} else {
								if (!thereExistsAtLeastOnePossibleMoveForThisColor(boardConfiguration, currentPlayer)) {
									currentPlayer = BoardConfiguration.GAME_OVER;
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

	private void modifyBoardForCurrentAllowedPosition(int i, int j, int colorToBePut) {

		int colorTheNeighborShouldBe;
		if (colorToBePut == BoardConfiguration.WHITE) {
			colorTheNeighborShouldBe = BoardConfiguration.BLACK;
		} else {
			colorTheNeighborShouldBe = BoardConfiguration.WHITE;
		}
		
		boardConfiguration.modifyPositionOnBoard(i, j, colorToBePut);
		nrGreen--;

		// horizontal right
		if (thereExistChangeableTokensOnHorizontalRight(boardConfiguration, i, j, colorToBePut)) {
			modifyHorizontalRight(i, j, colorToBePut);
		}

		// horizontal left
		if (thereExistChangeableTokensOnHorizontalLeft(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
			modifyHorizontalLeft(i, j, colorToBePut);
		}
		// vertical up
		if (thereExistChangeableTokensOnVerticalUp(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
			modifyVerticalUp(i, j, colorToBePut);
		}
		// vertical down
		if (thereExistChangeableTokensOnVerticalDown(boardConfiguration, i, j, colorTheNeighborShouldBe)) {
			modifyVerticalDown(i, j, colorToBePut);
		}
		// leading diagonal right
		if (thereExistChangeableTokensOnLeadingRight(boardConfiguration, i, j, colorTheNeighborShouldBe)){
			modifyLeadingRight(i, j, colorToBePut);
		}
		// leading diagonal left
		if (thereExistChangeableTokensOnLeadingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe)){
			modifyLeadingLeft(i, j, colorToBePut);
		}
		// trailing diagonal right
		if (thereExistChangeableTokensOnTrailingRight(boardConfiguration, i, j, colorTheNeighborShouldBe)){
			modifyTrailingRight(i, j, colorToBePut);
		}
		// trailing diagonal left
		if (thereExistChangeableTokensOnTrailingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe)){
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
			while (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] !=colorToBePut) {
				boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, searchTheOtherColorSecond, colorToBePut);
				searchTheOtherColor--;
				searchTheOtherColorSecond--;
			}
	}

	protected void modifyTrailingRight(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i - 1;
			int searchTheOtherColorSecond = j + 1;
			while (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] !=colorToBePut) {
				boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, searchTheOtherColorSecond, colorToBePut);
				searchTheOtherColor--;
				searchTheOtherColorSecond++;
			}
	}

	protected void modifyTrailingLeft(int i, int j, int colorToBePut) {
		int searchTheOtherColor = i + 1;
			int searchTheOtherColorSecond = j - 1;
			while (boardConfiguration
					.getBoard()[searchTheOtherColor][searchTheOtherColorSecond] !=colorToBePut) {
				boardConfiguration.modifyPositionOnBoard(searchTheOtherColor, searchTheOtherColorSecond, colorToBePut);
				searchTheOtherColor++;
				searchTheOtherColorSecond--;
			}
	}

	private void calculateScores() {
		scoreWhite = calculateScoreForColor(BoardConfiguration.WHITE);
		// scoreBlack = calculateScoreForColor(BoardConfiguration.BLACK);
		scoreBlack = BoardConfiguration.ROWS * BoardConfiguration.COLS - scoreWhite - nrGreen;
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