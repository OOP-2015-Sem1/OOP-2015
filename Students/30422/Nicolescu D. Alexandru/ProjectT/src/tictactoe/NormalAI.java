package tictactoe;

public class NormalAI extends RuleBasedStrategy {

	private int nrOfMove = 0;
	private int moveMade = 0;
	private boolean cvc = false;
	private int board[][] = new int[3][3];
	private static final int HUMAN_MOVE = 1;
	private static final int PC_MOVE = 2;
	private static final int CVC_MODE = 20;
	private static final int PLAYER1_MOVE = 1;
	private static final int PLAYER2_MOVE = 2;

	public int executeMove(int playerLastMove) {
		if (playerLastMove == CVC_MODE)
			cvc = true;
		int activePlayer = nrOfMove % 2 == 0 ? PLAYER2_MOVE : PLAYER1_MOVE;
		if (!cvc)
			activePlayer = HUMAN_MOVE;
		switch (playerLastMove) {
		case 0:
			board[0][0] = activePlayer;
			break;
		case 1:
			board[0][1] = activePlayer;
			break;
		case 2:
			board[0][2] = activePlayer;
			break;
		case 3:
			board[1][0] = activePlayer;
			break;
		case 4:
			board[1][1] = activePlayer;
			break;
		case 5:
			board[1][2] = activePlayer;
			break;
		case 6:
			board[2][0] = activePlayer;
			break;
		case 7:
			board[2][1] = activePlayer;
			break;
		case 8:
			board[2][2] = activePlayer;
			break;

		}
		nrOfMove++;
		int randomVariable = (int) (Math.random() * 8);

		switch (nrOfMove) {
		case 1:
			while (moveMade == 0) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 1;
					return randomVariable;
				}
			}
			break;
		case 2:
			int temp = notLose(board);
			if (temp != (-1)) {
				moveMade = 2;
				return temp;
			}

			while (moveMade == 1) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 2;
					return randomVariable;
				}
			}
			break;
		case 3:
			temp = notLose(board);
			if (temp != (-1)) {
				moveMade = 3;
				return temp;
			}
			temp = winLines(board);
			if (temp != (-1)) {
				moveMade = 3;
				return temp;
			}
			temp = winDiagonals(board);
			if (temp != (-1)) {
				moveMade = 3;
				return temp;
			}
			while (moveMade == 2) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 3;
					return randomVariable;
				}
			}
			break;
		case 4:
			temp = notLose(board);
			if (temp != (-1)) {
				moveMade = 4;
				return temp;
			}
			temp = winLines(board);
			if (temp != (-1)) {
				moveMade = 4;
				return temp;
			}
			temp = winDiagonals(board);
			if (temp != (-1)) {
				moveMade = 4;
				return temp;
			}
			while (moveMade == 3) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 4;
					return randomVariable;
				}
			}
			break;

		case 5:
			temp = notLose(board);
			if (temp != (-1)) {
				moveMade = 5;
				return temp;
			}
			temp = winLines(board);
			if (temp != (-1)) {
				moveMade = 5;
				return temp;
			}
			temp = winDiagonals(board);
			if (temp != (-1)) {
				moveMade = 5;
				return temp;
			}
			while (moveMade == 4) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 5;
					return randomVariable;
				}
			}
			break;
		case 6:
			temp = notLose(board);
			if (temp != (-1)) {
				moveMade = 6;
				return temp;
			}
			temp = winLines(board);
			if (temp != (-1)) {
				moveMade = 6;
				return temp;
			}
			temp = winDiagonals(board);
			if (temp != (-1)) {
				moveMade = 6;
				return temp;
			}
			while (moveMade == 5) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 6;
					return randomVariable;
				}
			}
			break;
		case 7:
			temp = notLose(board);
			if (temp != (-1)) {
				moveMade = 7;
				return temp;
			}
			temp = winLines(board);
			if (temp != (-1)) {
				moveMade = 7;
				return temp;
			}
			temp = winDiagonals(board);
			if (temp != (-1)) {
				moveMade = 7;
				return temp;
			}
			while (moveMade == 6) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 7;
					return randomVariable;
				}
			}
			break;
		case 8:
			while (moveMade == 7) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 8;
					return randomVariable;
				}
			}
			break;
		case 9:
			temp = notLose(board);
			if (temp != (-1)) {
				moveMade = 9;
				return temp;
			}
			temp = winLines(board);
			if (temp != (-1)) {
				moveMade = 9;
				return temp;
			}
			temp = winDiagonals(board);
			if (temp != (-1)) {
				moveMade = 9;
				return temp;
			}
			while (moveMade == 8) {
				randomVariable = (int) (Math.random() * 8);
				if (board[randomVariable / 3][randomVariable % 3] == 0) {
					board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 9;
					return randomVariable;
				}
			}
			break;
		}
		return -1;
	}
}
