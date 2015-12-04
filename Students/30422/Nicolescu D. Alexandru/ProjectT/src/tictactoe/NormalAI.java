package tictactoe;

public class NormalAI extends RuleBasedStrategy {
	
	private int nrOfMove=0;
	private int moveMade=0;
	private int board[][]=new int[3][3];
	private static final int HUMAN_MOVE = 1;
	private static final int PC_MOVE = 2;
	
	public int executeMove(int playerLastMove){
			switch (playerLastMove) {
			case 0:
				board[0][0] = HUMAN_MOVE;
				break;
			case 1:
				board[0][1] = HUMAN_MOVE;
				break;
			case 2:
				board[0][2] = HUMAN_MOVE;
				break;
			case 3:
				board[1][0] = HUMAN_MOVE;
				break;
			case 4:
				board[1][1] = HUMAN_MOVE;
				break;
			case 5:
				board[1][2] = HUMAN_MOVE;
				break;
			case 6:
				board[2][0] = HUMAN_MOVE;
				break;
			case 7:
				board[2][1] = HUMAN_MOVE;
				break;
			case 8:
				board[2][2] = HUMAN_MOVE;
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
			if (temp != (-1))
				return temp;

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
			if (temp != (-1))
				return temp;
			temp = winLines(board);
			if (temp != (-1))
				return temp;
			temp = winDiagonals(board);
			if (temp != (-1))
				return temp;
			temp = winDiagonals(board);
			if (temp != (-1))
				return temp;
			for (int k = 0; k < 3; k++)
				for (int j = 0; j < 3; j++) {
					if (board[k][j] == 0) {
						board[k][j] = PC_MOVE;
						int l = k * 3 + j;
						return l;
					}
				}
			break;
		case 4:
			temp = notLose(board);
			if (temp != (-1))
				return temp;
			temp = winLines(board);
			if (temp != (-1))
				return temp;
			temp = winDiagonals(board);
			if (temp != (-1))

				for (int k = 0; k < 3; k++)
					for (int j = 0; j < 3; j++) {
						if (board[k][j] == 0) {
							board[k][j] = PC_MOVE;
							System.out.println("nasol");
							int l = k * 3 + j;
							return l;
						}
					}
			break;

		case 5:
			temp = notLose(board);
			if (temp != (-1))
				return temp;
			for (int k = 0; k < 3; k++)
				for (int j = 0; j < 3; j++) {
					if (board[k][j] == 0) {
						board[k][j] = PC_MOVE;
						System.out.println("nasol2");
						int l = k * 3 + j;
						return l;
					}
				}
			break;
		}

		return 7;
	}
}
