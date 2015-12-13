import javax.swing.JButton;
import javax.swing.JOptionPane;

public class GameLogic {
	public JButton[][] b = new JButton[13][13];
	public int[][] mat = new int[13][13];
	private static final int MAX_PLAYER_PIECES = 9;
	private static final int PLAYER_ONE = 1;
	private static final int PLAYER_TWO = 2;
	private int currentPlayer;
	private boolean deleteMode = false;
	private boolean movementInProgress = false;
	private int selectedPiece;
	private MorrisIcons icons = MorrisIcons.getInstance();
	private Player playerOne = new Player();
	private Player playerTwo = new Player();
	private static GameLogic logic;

	public static GameLogic getInstance() {
		if (logic == null) {
			logic = new GameLogic();
		}
		return logic;
	}

	public GameLogic() {
		playerOne.setPlayerMoves(0);
		playerTwo.setPlayerMoves(0);
		playerOne.setPlayerPieces(MAX_PLAYER_PIECES);
		playerTwo.setPlayerPieces(MAX_PLAYER_PIECES);
		playerOne.setPlayerTurn(true);
	}

	public int gamePartOne(JButton source, int indexI, int indexJ, int gamePart) {
		if (newMorrisOnTable(currentPlayer)) {
			deleteMode = true;
		}
		if (playerOne.getPlayerMoves() == MAX_PLAYER_PIECES && playerTwo.getPlayerMoves() == MAX_PLAYER_PIECES
				&& !deleteMode) {
			switchPlayer();
			gamePart = 2;
		}
		if (!deleteMode) {
			putPiecesOnBoard(source, playerOne.getPlayerTurn());
		} else if (pieceNotInMorris(source, currentPlayer)) {
			switchPlayer();
			if (mat[indexI][indexJ] == currentPlayer) {
				deletePiece(source);
				deleteMode = false;
			}
		}
		return gamePart;
	}

	public int gamePartTwo(JButton source, int indexI, int indexJ, int gamePart) {
		if (deleteMode && pieceNotInMorris(source, currentPlayer)) {
			if (mat[indexI][indexJ] == currentPlayer) {
				deletePiece(source);
				deleteMode = false;
			}
		}
		if (!deleteMode) {
			if (!movementInProgress) {
				if (mat[indexI][indexJ] == currentPlayer) {
					selectedPiece = getPosition(source);
					movementInProgress = true;
				}
			} else if (selectedPiece != getPosition(source)) {
				if (validMove(source, selectedPiece, currentPlayer)) {
					mat[selectedPiece / 100][selectedPiece % 100] = 0;
					b[selectedPiece / 100][selectedPiece % 100].setIcon(icons.Button);
					if (currentPlayer == PLAYER_ONE) {
						playerOne.setOldNumberOfMorris(numberOfMorrisOnBoard(PLAYER_ONE));
					}
					if (currentPlayer == PLAYER_TWO) {
						playerTwo.setOldNumberOfMorris(numberOfMorrisOnBoard(PLAYER_TWO));
					}
					mat[indexI][indexJ] = currentPlayer;
					if (currentPlayer == PLAYER_ONE && !deleteMode) {
						if (newMorrisOnTable(currentPlayer)) {
							deleteMode = true;
						}
						b[indexI][indexJ].setIcon(icons.R);
					}
					if (currentPlayer == PLAYER_TWO && !deleteMode) {
						if (newMorrisOnTable(currentPlayer)) {
							deleteMode = true;
						}
						b[indexI][indexJ].setIcon(icons.B);
					}
					switchPlayer();
					movementInProgress = false;
				}
			}
		}
		if (playerOne.getPlayerPieces() == 2 || playerTwo.getPlayerPieces() == 2 && !deleteMode) {
			gamePart = 3;
		}
		return gamePart;
	}

	public void gamePartThree() {
		if (playerOne.getPlayerPieces() == 2) {
			JOptionPane.showMessageDialog(null, "Player 2 WINS");
			System.exit(0);
		}
		if (playerTwo.getPlayerPieces() == 2) {
			JOptionPane.showMessageDialog(null, "Player 1 WINS");
			System.exit(0);
		}
	}

	public void putPiecesOnBoard(JButton source, boolean playerTurn) {
		int position = getPosition(source);
		if (mat[position / 100][position % 100] == 0) {
			if (playerOne.getPlayerTurn() && (playerOne.getPlayerMoves() < MAX_PLAYER_PIECES)) {
				source.setIcon(icons.R);
				playerOne.incrementMoves();
				playerOne.setPlayerTurn(false);
				currentPlayer = PLAYER_ONE;
				mat[position / 100][position % 100] = 1;
			} else if (!playerOne.getPlayerTurn() && playerTwo.getPlayerMoves() < MAX_PLAYER_PIECES) {
				source.setIcon(icons.B);
				playerTwo.incrementMoves();
				playerOne.setPlayerTurn(true);
				currentPlayer = PLAYER_TWO;
				mat[position / 100][position % 100] = 2;
			}
		}
	}

	public boolean newMorrisOnTable(int currentPlayer) {
		boolean isMorris = false;
		if (currentPlayer == PLAYER_ONE) {
			if (numberOfMorrisOnBoard(PLAYER_ONE) > playerOne.getOldNumberOfMorris()) {
				playerOne.setOldNumberOfMorris(numberOfMorrisOnBoard(PLAYER_ONE));
				isMorris = true;
			}
		} else if (currentPlayer == PLAYER_TWO) {
			if (numberOfMorrisOnBoard(PLAYER_TWO) > playerTwo.getOldNumberOfMorris()) {
				playerTwo.setOldNumberOfMorris(numberOfMorrisOnBoard(PLAYER_TWO));
				isMorris = true;
			}
		}
		return isMorris;
	}

	public int numberOfMorrisOnBoard(int currentPlayer) {
		int actualNumberOfMorris = 0;
		if (mat[0][0] == currentPlayer && mat[0][6] == currentPlayer && mat[0][12] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[2][2] == currentPlayer && mat[2][6] == currentPlayer && mat[2][10] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[4][4] == currentPlayer && mat[4][6] == currentPlayer && mat[4][8] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[6][0] == currentPlayer && mat[6][2] == currentPlayer && mat[6][4] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[6][8] == currentPlayer && mat[6][10] == currentPlayer && mat[6][12] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[8][4] == currentPlayer && mat[8][6] == currentPlayer && mat[8][8] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[10][2] == currentPlayer && mat[10][6] == currentPlayer && mat[10][10] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[12][0] == currentPlayer && mat[12][6] == currentPlayer && mat[12][12] == currentPlayer)
			actualNumberOfMorris++;

		if (mat[0][0] == currentPlayer && mat[6][0] == currentPlayer && mat[12][0] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[2][2] == currentPlayer && mat[6][2] == currentPlayer && mat[10][2] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[4][4] == currentPlayer && mat[6][4] == currentPlayer && mat[8][4] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[0][6] == currentPlayer && mat[2][6] == currentPlayer && mat[4][6] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[8][6] == currentPlayer && mat[10][6] == currentPlayer && mat[12][6] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[4][8] == currentPlayer && mat[6][8] == currentPlayer && mat[8][8] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[2][10] == currentPlayer && mat[6][10] == currentPlayer && mat[10][10] == currentPlayer)
			actualNumberOfMorris++;
		if (mat[0][12] == currentPlayer && mat[6][12] == currentPlayer && mat[12][12] == currentPlayer)
			actualNumberOfMorris++;
		return actualNumberOfMorris;
	}

	public boolean pieceNotInMorris(JButton source, int currentPlayer) {
		boolean canTakePiece = true;
		int position = getPosition(source);
		int valueInMatrix = mat[position / 100][position % 100];
		int numberOfMorris = numberOfMorrisOnBoard(currentPlayer);
		mat[position / 100][position % 100] = 0;
		if (numberOfMorrisOnBoard(currentPlayer) < numberOfMorris)
			canTakePiece = false;
		mat[position / 100][position % 100] = valueInMatrix;
		return canTakePiece;
	}

	public boolean validMove(JButton source, int selectedPiece, int currentPlayer) {
		int position = getPosition(source);
		int iStart, jStart, iFinish, jFinish;
		if (currentPlayer == PLAYER_ONE) {
			if (playerOne.getPlayerPieces() == 3) {
				return true;
			}
		}
		if (currentPlayer == PLAYER_TWO) {
			if (playerTwo.getPlayerPieces() == 3)
				return true;
		}
		if (position / 100 < selectedPiece / 100) {
			iStart = position / 100;
			iFinish = selectedPiece / 100;
		} else {
			iFinish = position / 100;
			iStart = selectedPiece / 100;
		}

		if (position % 100 < selectedPiece % 100) {
			jStart = position % 100;
			jFinish = selectedPiece % 100;
		} else {
			jFinish = position % 100;
			jStart = selectedPiece % 100;
		}
		if (mat[position / 100][position % 100] == 0) {
			if (jStart == jFinish) {
				for (int i = iStart + 1; i < iFinish; i++) {
					if (mat[i][jStart] != -2 && mat[i][jFinish] != -3)
						return false;
				}
			} else if (iStart == iFinish) {
				for (int j = jStart + 1; j < jFinish; j++) {
					if (mat[iStart][j] != -2 && mat[iStart][j] != -3)
						return false;
				}
			} else
				return false;
		}
		return true;
	}

	public void deletePiece(JButton source) {
		source.setIcon(icons.Button);
		source.setEnabled(true);
		int position = getPosition(source);
		if (mat[position / 100][position % 100] == 1)
			playerOne.decrementPieces();
		if (mat[position / 100][position % 100] == 2)
			playerTwo.decrementPieces();
		mat[position / 100][position % 100] = 0;
	}

	public void switchPlayer() {
		this.currentPlayer = 3 - this.currentPlayer;
	}

	public int getPosition(JButton source) {
		return Integer.parseInt(source.getName());
	}
}
