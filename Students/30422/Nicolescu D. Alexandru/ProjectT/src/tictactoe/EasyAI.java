package tictactoe;

public class EasyAI extends RuleBasedStrategy {
	private boolean moveMade;
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
		moveMade = false;
		int randomVariable = (int) (Math.random() * 9);
		while (moveMade == false) {
			randomVariable = (int) (Math.random() * 9);
			if (board[randomVariable / 3][randomVariable % 3] == 0) {
				board[randomVariable / 3][randomVariable % 3] = PC_MOVE;
				moveMade = true;
				return randomVariable;
			}
		}
		return -1;
	}
}
