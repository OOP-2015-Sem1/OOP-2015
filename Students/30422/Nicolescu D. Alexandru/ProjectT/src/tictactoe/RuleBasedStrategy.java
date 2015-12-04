package tictactoe;

public abstract class RuleBasedStrategy implements AI {
	private static final int HUMAN_MOVE = 1;
	private static final int PC_MOVE = 2;

	public abstract int executeMove(int playerLastMove);

	public int placeOppositeSide(int board[][]) {

		if (board[0][0] == PC_MOVE) {
			board[2][2] = HUMAN_MOVE;
			return 8;
		}
		if (board[0][2] == PC_MOVE) {
			board[2][0] = HUMAN_MOVE;
			return 6;
		}
		if (board[2][0] == PC_MOVE) {
			board[0][2] = HUMAN_MOVE;
			return 2;
		}
		if (board[2][2] == PC_MOVE) {
			board[0][0] = HUMAN_MOVE;
			return 0;
		}
		return -1;
	}

	public int placeOppositeCorner(int board[][]) {
		if ((board[1][0] == HUMAN_MOVE) || (board[0][0] == HUMAN_MOVE)) {
			board[2][2] = PC_MOVE;
			return 8;
		} else if ((board[1][2] == HUMAN_MOVE) || (board[2][2] == HUMAN_MOVE)) {
			board[0][0] = PC_MOVE;
			return 0;
		} else if ((board[0][1] == HUMAN_MOVE) || (board[0][2] == HUMAN_MOVE)) {
			board[2][0] = PC_MOVE;
			return 6;
		} else if ((board[2][1] == HUMAN_MOVE) || (board[2][0] == HUMAN_MOVE)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		return -1;
	}

	public int winLines(int board[][]) {
		if ((board[0][0] == PC_MOVE) && (board[0][2] == PC_MOVE) && board[0][1] == 0) {
			board[0][1] = PC_MOVE;
			return 1;
		}
		if ((board[2][0] == PC_MOVE) && (board[2][2] == PC_MOVE) && board[2][1] == 0) {

			board[2][1] = PC_MOVE;
			return 7;
		}
		if ((board[0][0] == PC_MOVE) && (board[2][0] == PC_MOVE) && (board[1][0] == 0)) {
			board[1][0] = PC_MOVE;
			return 3;
		}
		if ((board[0][2] == PC_MOVE) && (board[2][2] == PC_MOVE) && (board[1][2] == 0)) {
			board[1][2] = PC_MOVE;
			return 5;
		}
		if ((board[1][0] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[1][2] == 0)) {
			board[1][2] = PC_MOVE;
			return 5;
		}
		if ((board[0][1] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[2][1] == 0)) {
			board[2][1] = PC_MOVE;
			return 7;
		}
		if ((board[1][2] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[1][0] == 0)) {
			board[1][0] = PC_MOVE;
			return 3;
		}
		if ((board[2][1] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[0][1] == 0)) {
			board[0][1] = PC_MOVE;
			return 1;
		}
		return -1;
	}

	public int winDiagonals(int board[][]) {
		if ((board[0][0] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[2][2] == 0)) {
			board[2][2] = PC_MOVE;
			return 8;
		} else if ((board[2][2] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[0][0] == 0)) {
			board[0][0] = PC_MOVE;
			return 0;
		} else if ((board[0][2] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[2][0] == 0)) {
			board[2][0] = PC_MOVE;
			return 6;
		} else if ((board[2][0] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[0][2] == 0)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		return -1;
	}

	public int setUpTrap(int board[][]) {
		if ((board[2][0] == HUMAN_MOVE) && (board[0][1] == HUMAN_MOVE)) {
			board[2][2] = PC_MOVE;
			return 8;
		}
		if ((board[2][2] == HUMAN_MOVE) && (board[0][1] == HUMAN_MOVE)) {
			board[2][0] = PC_MOVE;
			return 6;
		}
		if ((board[1][2] == HUMAN_MOVE) && (board[0][0] == HUMAN_MOVE)) {
			board[2][0] = PC_MOVE;
			return 6;
		}
		if ((board[1][2] == HUMAN_MOVE) && (board[0][2] == HUMAN_MOVE)) {
			board[0][0] = PC_MOVE;
			return 0;
		}
		if ((board[2][1] == HUMAN_MOVE) && (board[0][0] == HUMAN_MOVE)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		if ((board[2][1] == HUMAN_MOVE) && (board[0][2] == HUMAN_MOVE)) {
			board[0][0] = PC_MOVE;
			return 0;
		}
		if ((board[1][0] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		if ((board[1][0] == HUMAN_MOVE) && (board[0][2] == HUMAN_MOVE)) {
			board[2][2] = PC_MOVE;
			return 8;
		}
		return -1;
	}

	public int notLose(int board[][]) {
		if ((board[0][2] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE) && (board[1][2] == 0)) {
			board[1][2] = PC_MOVE;
			return 5;
		}
		if ((board[0][2] == HUMAN_MOVE) && (board[1][2] == HUMAN_MOVE) && (board[2][2] == 0)) {
			board[2][2] = PC_MOVE;
			return 8;
		}
		if ((board[1][2] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE) && (board[0][2] == 0)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		if ((board[0][0] == HUMAN_MOVE) && (board[0][2] == HUMAN_MOVE) && (board[0][1] == 0)) {
			board[0][1] = PC_MOVE;
			return 1;
		}
		if ((board[0][0] == HUMAN_MOVE) && (board[0][1] == HUMAN_MOVE) && (board[0][2] == 0)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		if ((board[1][2] == HUMAN_MOVE) && (board[0][2] == HUMAN_MOVE) && (board[0][0] == 0)) {
			board[0][0] = PC_MOVE;
			return 0;
		}
		if ((board[2][0] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE) && (board[2][1] == 0)) {
			board[2][1] = PC_MOVE;
			return 7;
		}

		if ((board[2][0] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE) && (board[2][1] == 0)) {
			board[2][1] = PC_MOVE;
			return 7;
		}
		if ((board[0][0] == HUMAN_MOVE) && (board[2][0] == HUMAN_MOVE) && (board[1][0] == 0)) {
			board[1][0] = PC_MOVE;
			return 3;
		}
		if ((board[1][0] == HUMAN_MOVE) && (board[2][0] == HUMAN_MOVE) && (board[0][0] == 0)) {
			board[0][0] = PC_MOVE;
			return 0;
		}

		if ((board[0][0] == HUMAN_MOVE) && (board[1][0] == HUMAN_MOVE) && (board[2][0] == 0)) {
			board[2][0] = PC_MOVE;
			return 6;
		}

		if ((board[0][2] == HUMAN_MOVE) && (board[0][1] == HUMAN_MOVE) && (board[0][0] == 0)) {
			board[0][0] = PC_MOVE;
			return 0;
		}

		if ((board[1][0] == HUMAN_MOVE) && (board[2][0] == HUMAN_MOVE) && (board[0][0] == 0)) {
			board[0][0] = PC_MOVE;
			return 0;
		}
		if ((board[2][0] == HUMAN_MOVE) && (board[2][1] == HUMAN_MOVE) && (board[2][2] == 0)) {
			board[2][2] = PC_MOVE;
			return 8;
		}
		if ((board[2][1] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE) && (board[2][0] == 0)) {
			board[2][0] = PC_MOVE;
			return 6;
		}
		if ((board[2][2] == HUMAN_MOVE) && (board[1][2] == HUMAN_MOVE) && (board[0][2] == 0)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		// with center
		if ((board[0][0] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE) && (board[1][1] == 0)) {
			board[1][1] = PC_MOVE;
			return 4;
		}
		if ((board[1][1] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE) && (board[0][0] == 0)) {
			board[0][0] = PC_MOVE;
			return 0;
		}
		if ((board[0][0] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[2][2] == 0)) {
			board[2][2] = PC_MOVE;
			return 8;
		}
		if ((board[0][2] == HUMAN_MOVE) && (board[2][0] == HUMAN_MOVE) && (board[1][1] == 0)) {
			board[1][1] = PC_MOVE;
			return 4;
		}
		if ((board[0][2] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[2][0] == 0)) {
			board[2][0] = PC_MOVE;
			return 6;
		}
		if ((board[2][0] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[0][2] == 0)) {
			board[0][2] = PC_MOVE;
			return 2;
		}
		if ((board[1][0] == HUMAN_MOVE) && (board[1][2] == HUMAN_MOVE) && (board[1][1] == 0)) {
			board[1][1] = PC_MOVE;
			return 4;
		}
		if ((board[1][0] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[1][2] == 0)) {
			board[1][2] = PC_MOVE;
			return 5;
		}
		if ((board[1][2] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[1][0] == 0)) {
			board[1][0] = PC_MOVE;
			return 3;
		}
		if ((board[0][1] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[2][1] == 0)) {
			board[2][1] = PC_MOVE;
			return 7;
		}
		if ((board[1][1] == HUMAN_MOVE) && (board[2][1] == HUMAN_MOVE) && (board[0][1] == 0)) {
			board[0][1] = PC_MOVE;
			return 1;
		}
		if ((board[0][1] == HUMAN_MOVE) && (board[2][1] == HUMAN_MOVE) && (board[1][1] == 0)) {
			board[1][1] = PC_MOVE;
			return 4;
		}
		return -1;
	}

}
