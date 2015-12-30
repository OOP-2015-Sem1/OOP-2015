package logic;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import graphics.MorrisIcons;
import login.FileSerialization;

public class GameLogic {
	public JButton[][] buttons = new JButton[13][13];
	public int[][] matrix = new int[13][13];
	public JButton hint = new JButton();
	private static final int MAX_PLAYER_PIECES = 4;
	private static final int WIN_BONUS = 1;
	private boolean deleteMode = false;
	private boolean movementInProgress = false;
	private int selectedPiece;
	private MorrisIcons icons = MorrisIcons.getInstance();
	FileSerialization serial = FileSerialization.getInstance();
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
		playerOne.setActive(true);
		playerTwo.setActive(false);
		playerOne.setTag(1);
		playerTwo.setTag(2);
	}

	public int gamePartOne(JButton source, int indexI, int indexJ, int gamePart) {
		switchPlayer();
		if (newMorrisOnTable()) {
			deleteMode = true;
		}
		switchPlayer();
		if (playerOne.getPlayerMoves() == MAX_PLAYER_PIECES && playerTwo.getPlayerMoves() == MAX_PLAYER_PIECES
				&& !deleteMode) {
			gamePart = 2;
		}
		if (!deleteMode) {
			putPiecesOnBoard(source);
		} else if (pieceNotInMorris(source)) {
			if (matrix[indexI][indexJ] == getCurrentPlayer().getTag()) {
				deletePiece(source);
				deleteMode = false;
			}
		}
		return gamePart;
	}

	public int gamePartTwo(JButton source, int indexI, int indexJ, int gamePart) {
		if (deleteMode && pieceNotInMorris(source)) {
			if (matrix[indexI][indexJ] == getCurrentPlayer().getTag()) {
				deletePiece(source);
				deleteMode = false;
			}
		}
		if (!deleteMode) {
			if (!movementInProgress) {
				if (matrix[indexI][indexJ] == getCurrentPlayer().getTag()) {
					if (playerOne.isActive())
						gameHint(5);
					else if (playerTwo.isActive())
						gameHint(7);
					selectedPiece = getPosition(source);
					movementInProgress = true;
				}
			} else if (selectedPiece != getPosition(source)) {
				if (validMove(source, selectedPiece)) {
					Point selectedPieceIndeces = getPieceIndeces(selectedPiece);
					int pieceIndexI = selectedPieceIndeces.x;
					int pieceIndexJ = selectedPieceIndeces.y;
					matrix[pieceIndexI][pieceIndexJ] = 0;
					buttons[pieceIndexI][pieceIndexJ].setIcon(icons.Button);
					if (getCurrentPlayer().getTag() == 1) {
						gameHint(6);
						playerOne.setOldNumberOfMorris(numberOfMorrisOnBoard(playerOne));
					}
					if (getCurrentPlayer().getTag() == 2) {
						gameHint(4);
						playerTwo.setOldNumberOfMorris(numberOfMorrisOnBoard(playerTwo));
					}
					matrix[indexI][indexJ] = getCurrentPlayer().getTag();
					if (getCurrentPlayer().getTag() == 1 && !deleteMode) {
						displayRemovalHint(2);
						if (newMorrisOnTable()) {
							deleteMode = true;
						}
						buttons[indexI][indexJ].setIcon(icons.R);
					}
					if (getCurrentPlayer().getTag() == 2 && !deleteMode) {
						displayRemovalHint(2);
						if (newMorrisOnTable()) {
							deleteMode = true;
						}
						buttons[indexI][indexJ].setIcon(icons.B);
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
			serial.account[serial.getFirstPlayerAccountNr()]
					.setScore(serial.account[serial.getFirstPlayerAccountNr()].getScore() - WIN_BONUS);
			serial.account[serial.getSecondPlayerAccountNr()]
					.setScore(serial.account[serial.getSecondPlayerAccountNr()].getScore() + WIN_BONUS);
			JOptionPane.showMessageDialog(null, serial.account[serial.getSecondPlayerAccountNr()].getName()
					+ " WINS. Score : " + serial.account[serial.getSecondPlayerAccountNr()].getScore());
			JOptionPane.showMessageDialog(null, serial.account[serial.getFirstPlayerAccountNr()].getName()
					+ " LOSES. Score : " + serial.account[serial.getFirstPlayerAccountNr()].getScore());
			serial.createAccount("onlySave");
			System.exit(0);
		}
		if (playerTwo.getPlayerPieces() == 2) {
			serial.account[serial.getFirstPlayerAccountNr()]
					.setScore(serial.account[serial.getFirstPlayerAccountNr()].getScore() + WIN_BONUS);
			serial.account[serial.getSecondPlayerAccountNr()]
					.setScore(serial.account[serial.getSecondPlayerAccountNr()].getScore() - WIN_BONUS);
			JOptionPane.showMessageDialog(null, serial.account[serial.getFirstPlayerAccountNr()].getName()
					+ " WINS. Score : " + serial.account[serial.getFirstPlayerAccountNr()].getScore());
			JOptionPane.showMessageDialog(null, serial.account[serial.getSecondPlayerAccountNr()].getName()
					+ " LOSES. Score : " + serial.account[serial.getSecondPlayerAccountNr()].getScore());
			serial.createAccount("onlySave");
			System.exit(0);
		}
	}

	public void putPiecesOnBoard(JButton source) {
		int position = getPosition(source);
		if (matrix[position / 100][position % 100] == 0) {
			if (playerOne.isActive() && (playerOne.getPlayerMoves() < MAX_PLAYER_PIECES)) {
				gameHint(1);
				source.setIcon(icons.R);
				playerOne.incrementMoves();
				switchPlayer();
				matrix[position / 100][position % 100] = 1;
			} else if (playerTwo.isActive() && playerTwo.getPlayerMoves() < MAX_PLAYER_PIECES) {
				gameHint(0);
				source.setIcon(icons.B);
				playerTwo.incrementMoves();
				switchPlayer();
				matrix[position / 100][position % 100] = 2;
			}
		}
		displayRemovalHint(1);
		if (playerOne.getPlayerMoves() == MAX_PLAYER_PIECES && playerTwo.getPlayerMoves() == MAX_PLAYER_PIECES) {
			if (playerOne.isActive())
				gameHint(4);
			else if (playerTwo.isActive())
				gameHint(6);
		}
	}

	public void displayRemovalHint(int gamePart) {
		if (gamePart==1){
			if (getCurrentPlayer().getTag() == 2) {
				if (numberOfMorrisOnBoard(playerOne) > playerOne.getOldNumberOfMorris()) {
					gameHint(2);
				}
			} else if (getCurrentPlayer().getTag() == 1) {
				if (numberOfMorrisOnBoard(playerTwo) > playerTwo.getOldNumberOfMorris()) {
					gameHint(3);
				}
			}
		}
		if (gamePart == 2) {
			if (getCurrentPlayer().getTag() == 1) {
				if (numberOfMorrisOnBoard(playerOne) > playerOne.getOldNumberOfMorris()) {
					gameHint(2);
				}
			} else if (getCurrentPlayer().getTag() == 2) {
				if (numberOfMorrisOnBoard(playerTwo) > playerTwo.getOldNumberOfMorris()) {
					gameHint(3);
				}
			}
		}
	}

	public boolean newMorrisOnTable() {
		boolean isMorris = false;
		if (getCurrentPlayer().getTag() == 1) {
			if (numberOfMorrisOnBoard(playerOne) > playerOne.getOldNumberOfMorris()) {
				playerOne.setOldNumberOfMorris(numberOfMorrisOnBoard(playerOne));
				isMorris = true;
			}
		} else if (getCurrentPlayer().getTag() == 2) {
			if (numberOfMorrisOnBoard(playerTwo) > playerTwo.getOldNumberOfMorris()) {
				playerTwo.setOldNumberOfMorris(numberOfMorrisOnBoard(playerTwo));
				isMorris = true;
			}
		}
		return isMorris;
	}

	public int numberOfMorrisOnBoard(Player player) {
		int actualNumberOfMorris = 0;
		int tag = player.getTag();
		if (matrix[0][0] == tag && matrix[0][6] == tag && matrix[0][12] == tag) {
			actualNumberOfMorris++;
		}
		if (matrix[2][2] == tag && matrix[2][6] == tag && matrix[2][10] == tag)
			actualNumberOfMorris++;
		if (matrix[4][4] == tag && matrix[4][6] == tag && matrix[4][8] == tag)
			actualNumberOfMorris++;
		if (matrix[6][0] == tag && matrix[6][2] == tag && matrix[6][4] == tag)
			actualNumberOfMorris++;
		if (matrix[6][8] == tag && matrix[6][10] == tag && matrix[6][12] == tag)
			actualNumberOfMorris++;
		if (matrix[8][4] == tag && matrix[8][6] == tag && matrix[8][8] == tag)
			actualNumberOfMorris++;
		if (matrix[10][2] == tag && matrix[10][6] == tag && matrix[10][10] == tag)
			actualNumberOfMorris++;
		if (matrix[12][0] == tag && matrix[12][6] == tag && matrix[12][12] == tag)
			actualNumberOfMorris++;

		if (matrix[0][0] == tag && matrix[6][0] == tag && matrix[12][0] == tag)
			actualNumberOfMorris++;
		if (matrix[2][2] == tag && matrix[6][2] == tag && matrix[10][2] == tag)
			actualNumberOfMorris++;
		if (matrix[4][4] == tag && matrix[6][4] == tag && matrix[8][4] == tag)
			actualNumberOfMorris++;
		if (matrix[0][6] == tag && matrix[2][6] == tag && matrix[4][6] == tag)
			actualNumberOfMorris++;
		if (matrix[8][6] == tag && matrix[10][6] == tag && matrix[12][6] == tag)
			actualNumberOfMorris++;
		if (matrix[4][8] == tag && matrix[6][8] == tag && matrix[8][8] == tag)
			actualNumberOfMorris++;
		if (matrix[2][10] == tag && matrix[6][10] == tag && matrix[10][10] == tag)
			actualNumberOfMorris++;
		if (matrix[0][12] == tag && matrix[6][12] == tag && matrix[12][12] == tag)
			actualNumberOfMorris++;
		return actualNumberOfMorris;
	}

	public boolean pieceNotInMorris(JButton source) {
		boolean canTakePiece = true;
		int position = getPosition(source);
		int valueInMatrix = matrix[position / 100][position % 100];
		int numberOfMorris = numberOfMorrisOnBoard(getCurrentPlayer());
		matrix[position / 100][position % 100] = 0;
		if (numberOfMorrisOnBoard(getCurrentPlayer()) < numberOfMorris)
			canTakePiece = false;
		matrix[position / 100][position % 100] = valueInMatrix;
		return canTakePiece;
	}

	public boolean validMove(JButton source, int selectedPiece) {
		int position = getPosition(source);
		int iStart, jStart, iFinish, jFinish;
		if (getCurrentPlayer().getTag() == 1) {
			if (playerOne.getPlayerPieces() == 3) {
				return true;
			}
		}
		if (getCurrentPlayer().getTag() == 2) {
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
		if (matrix[position / 100][position % 100] == 0) {
			if (jStart == jFinish) {
				for (int i = iStart + 1; i < iFinish; i++) {
					if (matrix[i][jStart] != -2 && matrix[i][jFinish] != -3)
						return false;
				}
			} else if (iStart == iFinish) {
				for (int j = jStart + 1; j < jFinish; j++) {
					if (matrix[iStart][j] != -2 && matrix[iStart][j] != -3)
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
		if (matrix[position / 100][position % 100] == 1)
			playerOne.decrementPieces();
		if (matrix[position / 100][position % 100] == 2)
			playerTwo.decrementPieces();
		matrix[position / 100][position % 100] = 0;
		if (playerOne.getPlayerMoves() != MAX_PLAYER_PIECES && playerTwo.getPlayerMoves() != MAX_PLAYER_PIECES) {
			if (playerOne.isActive())
				gameHint(0);
			else if (playerTwo.isActive())
				gameHint(1);
		} else if (playerOne.getPlayerMoves() == MAX_PLAYER_PIECES && playerTwo.getPlayerMoves() == MAX_PLAYER_PIECES) {
			if (playerOne.isActive())
				gameHint(4);
			else if (playerTwo.isActive())
				gameHint(6);
		}
	}

	public void switchPlayer() {
		Player player = getCurrentPlayer();
		if (player.getTag() == 1) {
			playerOne.setActive(false);
			playerTwo.setActive(true);
		}
		if (player.getTag() == 2) {
			playerOne.setActive(true);
			playerTwo.setActive(false);
		}
	}

	public Player getCurrentPlayer() {
		return playerOne.isActive() ? playerOne : playerTwo;
	}

	public int getPosition(JButton source) {
		return Integer.parseInt(source.getName());
	}

	protected Point getPieceIndeces(int position) {
		Point pieceIndeces = new Point();

		pieceIndeces.x = position / 100;
		pieceIndeces.y = position % 100;
		return pieceIndeces;
	}

	private void gameHint(int hintNumber) {
		switch (hintNumber) {
		case 0:
			hint.setText("Red turn to place");
			break;
		case 1:
			hint.setText("Black turn to place");
			break;

		case 2:
			hint.setText("Red turn to remove black piece");
			break;

		case 3:
			hint.setText("Black turn to remove red piece");
			break;

		case 4:
			hint.setText("Red turn to select piece to move");
			break;

		case 5:
			hint.setText("Red turn to select where to move the piece");
			break;
		case 6:
			hint.setText("Black turn to select piece to move");
			break;

		case 7:
			hint.setText("Black turn to select where to move the piece");
			break;
		}
	}
}
