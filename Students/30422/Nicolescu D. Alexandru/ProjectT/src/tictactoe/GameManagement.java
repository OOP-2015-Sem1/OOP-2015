package tictactoe;

import javax.swing.JOptionPane;

import account.AccountRepository;
import userInterface.Sound;

public class GameManagement {

	private int nrOfMove = 0;
	private boolean pvp = false;
	private AccountRepository accounts = AccountRepository.getInstance();
	private int board[][] = new int[3][3];
	private static final int NO_RESPONSE = -1;
	private static final int HUMAN_MOVE = 1;
	private static final int PC_MOVE = 2;
	private static final int HARD = 0;
	private static final int NORMAL = 1;
	private static final int EASY = 2;
	private static final int PVP = -1;
	private static final int HUMAN = 1;
	private static final int PLAYER1_MOVE = 2;
	private static final int PLAYER2_MOVE = 1;
	private static final int PC = 2;
	private static final int SCORE_IF_WIN = 20;
	private static final int SCORE_IF_TIE = 5;
	private static final int SCORE_IF_LOST = -5;
	private static final int PVP_MOVE = 0;
	private static final int PVPC_MOVE = 1;

	private Sound sound = new Sound();
	private AI computerPlayer = null;

	public GameManagement(int difficulty) {
		switch (difficulty) {
		case PVP:
			pvp = true;
			break;
		case HARD:
			computerPlayer = new HardAI();
			break;
		case NORMAL:
			computerPlayer = new NormalAI();
			break;
		case EASY:
			computerPlayer = new EasyAI();
			break;
		}
	}

	public int manageLastPlayerMove(int playerLastMove) {

		nrOfMove++;
		if (pvp) {
			managePVPGame(playerLastMove);
			return NO_RESPONSE;
		} else {
			int computerMoveDecision = managePVPCGame(playerLastMove);
			return computerMoveDecision;
		}

	}

	private int managePVPCGame(int playerLastMove) {
		setMove(playerLastMove,PVPC_MOVE);
		int pcMove = computerPlayer.executeMove(playerLastMove);
		int i = pcMove / 3;
		int j = pcMove % 3;
		board[i][j] = PC_MOVE;
		return pcMove;

	}

	private void managePVPGame(int playerLastMove) {
		setMove(playerLastMove, PVP_MOVE);
	}

	private void setMove(int playerLastMove, int moveType){
		int activePlayer = nrOfMove % 2 == 0 ? PLAYER2_MOVE : PLAYER1_MOVE;
		if (moveType==PVPC_MOVE)
			activePlayer=HUMAN_MOVE;
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
		checkWin();
	}

	public int winner() {

		// if human wins
		if ((board[0][0] == HUMAN_MOVE) && (board[0][1] == HUMAN_MOVE) && (board[0][2] == HUMAN_MOVE))
			return 1;
		if ((board[0][0] == HUMAN_MOVE) && (board[1][0] == HUMAN_MOVE) && (board[2][0] == HUMAN_MOVE))
			return 1;
		if ((board[2][0] == HUMAN_MOVE) && (board[2][1] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE))
			return 1;
		if ((board[0][2] == HUMAN_MOVE) && (board[1][2] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE))
			return 1;
		if ((board[0][0] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[2][2] == HUMAN_MOVE))
			return 1;
		if ((board[0][2] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[2][0] == HUMAN_MOVE))
			return 1;
		if ((board[0][1] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[2][1] == HUMAN_MOVE))
			return 1;
		if ((board[1][0] == HUMAN_MOVE) && (board[1][1] == HUMAN_MOVE) && (board[1][2] == HUMAN_MOVE))
			return 1;
		// if pc wins
		if ((board[0][0] == PC_MOVE) && (board[0][1] == PC_MOVE) && (board[0][2] == PC_MOVE))
			return 2;
		if ((board[0][0] == PC_MOVE) && (board[1][0] == PC_MOVE) && (board[2][0] == PC_MOVE))
			return 2;
		if ((board[2][0] == PC_MOVE) && (board[2][1] == PC_MOVE) && (board[2][2] == PC_MOVE))
			return 2;
		if ((board[0][2] == PC_MOVE) && (board[1][2] == PC_MOVE) && (board[2][2] == PC_MOVE))
			return 2;
		if ((board[0][0] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[2][2] == PC_MOVE))
			return 2;
		if ((board[0][2] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[2][0] == PC_MOVE))
			return 2;
		if ((board[0][1] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[2][1] == PC_MOVE))
			return 2;
		if ((board[1][0] == PC_MOVE) && (board[1][1] == PC_MOVE) && (board[1][2] == PC_MOVE))
			return 2;

		return 0;
	}

	public boolean tableFull(int boardToCheck[][]) {
		boolean full = true;
		for (int u = 0; u < 3; u++)
			for (int y = 0; y < 3; y++) {
				if (boardToCheck[u][y] == 0)
					full = false;
			}
		return full;
	}

	public void checkWin() {
		if (winner() == HUMAN) {
			if (pvp) {
				JOptionPane.showMessageDialog(null, "Player 2 wins!");
				System.exit(0);
			} else {
				sound.SoundIt(HUMAN);
				accounts.replace(accounts.getAccountScore(accounts.getAccountNr()) + SCORE_IF_WIN);
				JOptionPane.showMessageDialog(null, "Player Wins");
				System.exit(0);
			}
		}

		if (winner() == PC) {
			if (pvp) {
				JOptionPane.showMessageDialog(null, "Player 1 wins!");
				System.exit(0);
			} else {
				sound.SoundIt(PC);
				if (accounts.getAccountNr() != 0)
					accounts.replace(accounts.getAccountScore(accounts.getAccountNr()) + SCORE_IF_LOST);
				JOptionPane.showMessageDialog(null, "I WIN!!");
				System.exit(0);
			}
		}

		if (tableFull(board)) {

			if (winner() == 0) {
				if (accounts.getAccountNr() != 0)
					accounts.replace(accounts.getAccountScore(accounts.getAccountNr()) + SCORE_IF_TIE);
				JOptionPane.showMessageDialog(null, "It's a Tie");
				System.exit(0);
			}
		}
	}
}