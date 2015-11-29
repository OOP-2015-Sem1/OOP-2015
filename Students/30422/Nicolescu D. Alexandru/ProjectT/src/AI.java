import javax.swing.JOptionPane;

public class AI {

	// human=1 pc=2
	int nrOfMove = 0;
	int moveMade = 0;
	int player1_move = 1;
	int player2_move = 2;
	boolean pvp = false;
	boolean FirstEntry = false;
	String playerName = null;
	int m[][] = new int[3][3];
	private static final int HUMAN_MOVE = 1;
	private static final int PC_MOVE = 2;
	private static final int HARD = 0;
	private static final int NORMAL = 1;
	private static final int EASY = 2;
	private static final int PVP = 3;
	private static final int HUMAN = 1;
	private static final int PC = 2;

	Sound s = new Sound();
	MinMax mm = new MinMax();
	Board b = new Board();
	LoginDialog input = new LoginDialog();

	public int MakeMove(int playerLastMove, int Difficulty) {

		nrOfMove++;
		if (((pvp) && (nrOfMove % 2 == 0)) || (!pvp)) {
			switch (playerLastMove) {
			case 0:
				m[0][0] = HUMAN_MOVE;
				break;
			case 1:
				m[0][1] = HUMAN_MOVE;
				break;
			case 2:
				m[0][2] = HUMAN_MOVE;
				break;
			case 3:
				m[1][0] = HUMAN_MOVE;
				break;
			case 4:
				m[1][1] = HUMAN_MOVE;
				break;
			case 5:
				m[1][2] = HUMAN_MOVE;
				break;
			case 6:
				m[2][0] = HUMAN_MOVE;
				break;
			case 7:
				m[2][1] = HUMAN_MOVE;
				break;
			case 8:
				m[2][2] = HUMAN_MOVE;
				break;
			}
		}
		CheckWin();

		// Strategies are from
		// https://www.quora.com/Is-there-a-way-to-never-lose-at-Tic-Tac-Toe

		// ------------------------------HARD---------------------------

		if (Difficulty == HARD) {

			int pcMove = mm.HardWay(playerLastMove);
			int i = pcMove / 3;
			int j = pcMove % 3;
			m[i][j] = PC_MOVE;
			return pcMove;

			// -------------------------------------NORMAL-------------------------------

		} else if (Difficulty == NORMAL) {
			int randomVariable = (int) (Math.random() * 8);

			switch (nrOfMove) {
			case 1:
				while (moveMade == 0) {
					randomVariable = (int) (Math.random() * 8);
					if (m[randomVariable / 3][randomVariable % 3] == 0) {
						m[randomVariable / 3][randomVariable % 3] = PC_MOVE;
						moveMade = 1;
						return randomVariable;
					}
				}
				break;
			case 2:
				int temp = NotLose();
				if (temp != (-1))
					return temp;

				while (moveMade == 1) {
					randomVariable = (int) (Math.random() * 8);
					if (m[randomVariable / 3][randomVariable % 3] == 0) {
						m[randomVariable / 3][randomVariable % 3] = PC_MOVE;
						moveMade = 2;
						return randomVariable;
					}
				}
				break;
			case 3:
				temp = NotLose();
				if (temp != (-1))
					return temp;
				temp = WinLines();
				if (temp != (-1))
					return temp;
				temp = WinDiagonals();
				if (temp != (-1))
					return temp;
				temp = WinDiagonals();
				if (temp != (-1))
					return temp;
				for (int k = 0; k < 3; k++)
					for (int j = 0; j < 3; j++) {
						if (m[k][j] == 0) {
							m[k][j] = PC_MOVE;
							int l = k * 3 + j;
							return l;
						}
					}
				break;
			case 4:
				temp = NotLose();
				if (temp != (-1))
					return temp;
				temp = WinLines();
				if (temp != (-1))
					return temp;
				temp = WinDiagonals();
				if (temp != (-1))

					for (int k = 0; k < 3; k++)
						for (int j = 0; j < 3; j++) {
							if (m[k][j] == 0) {
								m[k][j] = PC_MOVE;
								System.out.println("nasol");
								int l = k * 3 + j;
								return l;
							}
						}
				break;

			case 5:
				temp = NotLose();
				if (temp != (-1))
					return temp;
				for (int k = 0; k < 3; k++)
					for (int j = 0; j < 3; j++) {
						if (m[k][j] == 0) {
							m[k][j] = PC_MOVE;
							System.out.println("nasol2");
							int l = k * 3 + j;
							return l;
						}
					}
				break;
			}

			return 7;

			// --------------------------------EASY------------------------------------
		} else if (Difficulty == EASY) {
			moveMade = 0;
			int randomVariable = (int) (Math.random() * 8);
			while (moveMade == 0) {
				randomVariable = (int) (Math.random() * 8);
				if (m[randomVariable / 3][randomVariable % 3] == 0) {
					m[randomVariable / 3][randomVariable % 3] = PC_MOVE;
					moveMade = 1;
					return randomVariable;
				}
			}
			// -----------------------------PVP-----------------------------
		} else if (Difficulty == PVP) {
			pvp = true;
			if (nrOfMove % 2 == 1)
				switch (playerLastMove) {
				case 0:
					m[0][0] = PC_MOVE;
					break;
				case 1:
					m[0][1] = PC_MOVE;
					break;
				case 2:
					m[0][2] = PC_MOVE;
					break;
				case 3:
					m[1][0] = PC_MOVE;
					break;
				case 4:
					m[1][1] = PC_MOVE;
					break;
				case 5:
					m[1][2] = PC_MOVE;
					break;
				case 6:
					m[2][0] = PC_MOVE;
					break;
				case 7:
					m[2][1] = PC_MOVE;
					break;
				case 8:
					m[2][2] = PC_MOVE;
					break;
				}

			return -1;
		}
		return 8;
	}

	public int PlaceOppositeSide() {

		if (m[0][0] == PC_MOVE) {
			m[2][2] = HUMAN_MOVE;
			return 8;
		}
		if (m[0][2] == PC_MOVE) {
			m[2][0] = HUMAN_MOVE;
			return 6;
		}
		if (m[2][0] == PC_MOVE) {
			m[0][2] = HUMAN_MOVE;
			return 2;
		}
		if (m[2][2] == PC_MOVE) {
			m[0][0] = HUMAN_MOVE;
			return 0;
		}
		return -1;
	}

	public int PlaceOppositeCorner() {
		if ((m[1][0] == HUMAN_MOVE) || (m[0][0] == HUMAN_MOVE)) {
			m[2][2] = PC_MOVE;
			return 8;
		} else if ((m[1][2] == HUMAN_MOVE) || (m[2][2] == HUMAN_MOVE)) {
			m[0][0] = PC_MOVE;
			return 0;
		} else if ((m[0][1] == HUMAN_MOVE) || (m[0][2] == HUMAN_MOVE)) {
			m[2][0] = PC_MOVE;
			return 6;
		} else if ((m[2][1] == HUMAN_MOVE) || (m[2][0] == HUMAN_MOVE)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		return -1;
	}

	public int WinLines() {
		if ((m[0][0] == PC_MOVE) && (m[0][2] == PC_MOVE) && m[0][1] == 0) {
			m[0][1] = PC_MOVE;
			return 1;
		}
		if ((m[2][0] == PC_MOVE) && (m[2][2] == PC_MOVE) && m[2][1] == 0) {

			m[2][1] = PC_MOVE;
			return 7;
		}
		if ((m[0][0] == PC_MOVE) && (m[2][0] == PC_MOVE) && (m[1][0] == 0)) {
			m[1][0] = PC_MOVE;
			return 3;
		}
		if ((m[0][2] == PC_MOVE) && (m[2][2] == PC_MOVE) && (m[1][2] == 0)) {
			m[1][2] = PC_MOVE;
			return 5;
		}
		if ((m[1][0] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[1][2] == 0)) {
			m[1][2] = PC_MOVE;
			return 5;
		}
		if ((m[0][1] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[2][1] == 0)) {
			m[2][1] = PC_MOVE;
			return 7;
		}
		if ((m[1][2] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[1][0] == 0)) {
			m[1][0] = PC_MOVE;
			return 3;
		}
		if ((m[2][1] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[0][1] == 0)) {
			m[0][1] = PC_MOVE;
			return 1;
		}
		return -1;
	}

	public int WinDiagonals() {
		if ((m[0][0] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[2][2] == 0)) {
			m[2][2] = PC_MOVE;
			return 8;
		} else if ((m[2][2] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[0][0] == 0)) {
			m[0][0] = PC_MOVE;
			return 0;
		} else if ((m[0][2] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[2][0] == 0)) {
			m[2][0] = PC_MOVE;
			return 6;
		} else if ((m[2][0] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[0][2] == 0)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		return -1;
	}

	public int SetUpTrap() {
		if ((m[2][0] == HUMAN_MOVE) && (m[0][1] == HUMAN_MOVE)) {
			m[2][2] = PC_MOVE;
			return 8;
		}
		if ((m[2][2] == HUMAN_MOVE) && (m[0][1] == HUMAN_MOVE)) {
			m[2][0] = PC_MOVE;
			return 6;
		}
		if ((m[1][2] == HUMAN_MOVE) && (m[0][0] == HUMAN_MOVE)) {
			m[2][0] = PC_MOVE;
			return 6;
		}
		if ((m[1][2] == HUMAN_MOVE) && (m[0][2] == HUMAN_MOVE)) {
			m[0][0] = PC_MOVE;
			return 0;
		}
		if ((m[2][1] == HUMAN_MOVE) && (m[0][0] == HUMAN_MOVE)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		if ((m[2][1] == HUMAN_MOVE) && (m[0][2] == HUMAN_MOVE)) {
			m[0][0] = PC_MOVE;
			return 0;
		}
		if ((m[1][0] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		if ((m[1][0] == HUMAN_MOVE) && (m[0][2] == HUMAN_MOVE)) {
			m[2][2] = PC_MOVE;
			return 8;
		}
		return -1;
	}

	public int NotLose() {
		if ((m[0][2] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE) && (m[1][2] == 0)) {
			m[1][2] = PC_MOVE;
			return 5;
		}
		if ((m[0][2] == HUMAN_MOVE) && (m[1][2] == HUMAN_MOVE) && (m[2][2] == 0)) {
			m[2][2] = PC_MOVE;
			return 8;
		}
		if ((m[1][2] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE) && (m[0][2] == 0)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		if ((m[0][0] == HUMAN_MOVE) && (m[0][2] == HUMAN_MOVE) && (m[0][1] == 0)) {
			m[0][1] = PC_MOVE;
			return 1;
		}
		if ((m[0][0] == HUMAN_MOVE) && (m[0][1] == HUMAN_MOVE) && (m[0][2] == 0)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		if ((m[1][2] == HUMAN_MOVE) && (m[0][2] == HUMAN_MOVE) && (m[0][0] == 0)) {
			m[0][0] = PC_MOVE;
			return 0;
		}
		if ((m[2][0] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE) && (m[2][1] == 0)) {
			m[2][1] = PC_MOVE;
			return 7;
		}

		if ((m[2][0] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE) && (m[2][1] == 0)) {
			m[2][1] = PC_MOVE;
			return 7;
		}
		if ((m[0][0] == HUMAN_MOVE) && (m[2][0] == HUMAN_MOVE) && (m[1][0] == 0)) {
			m[1][0] = PC_MOVE;
			return 3;
		}
		if ((m[1][0] == HUMAN_MOVE) && (m[2][0] == HUMAN_MOVE) && (m[0][0] == 0)) {
			m[0][0] = PC_MOVE;
			return 0;
		}

		if ((m[0][0] == HUMAN_MOVE) && (m[1][0] == HUMAN_MOVE) && (m[2][0] == 0)) {
			m[2][0] = PC_MOVE;
			return 6;
		}

		if ((m[0][2] == HUMAN_MOVE) && (m[0][1] == HUMAN_MOVE) && (m[0][0] == 0)) {
			m[0][0] = PC_MOVE;
			return 0;
		}

		if ((m[1][0] == HUMAN_MOVE) && (m[2][0] == HUMAN_MOVE) && (m[0][0] == 0)) {
			m[0][0] = PC_MOVE;
			return 0;
		}
		if ((m[2][0] == HUMAN_MOVE) && (m[2][1] == HUMAN_MOVE) && (m[2][2] == 0)) {
			m[2][2] = PC_MOVE;
			return 8;
		}
		if ((m[2][1] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE) && (m[2][0] == 0)) {
			m[2][0] = PC_MOVE;
			return 6;
		}
		if ((m[2][2] == HUMAN_MOVE) && (m[1][2] == HUMAN_MOVE) && (m[0][2] == 0)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		// with center
		if ((m[0][0] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE) && (m[1][1] == 0)) {
			m[1][1] = PC_MOVE;
			return 4;
		}
		if ((m[1][1] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE) && (m[0][0] == 0)) {
			m[0][0] = PC_MOVE;
			return 0;
		}
		if ((m[0][0] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[2][2] == 0)) {
			m[2][2] = PC_MOVE;
			return 8;
		}
		if ((m[0][2] == HUMAN_MOVE) && (m[2][0] == HUMAN_MOVE) && (m[1][1] == 0)) {
			m[1][1] = PC_MOVE;
			return 4;
		}
		if ((m[0][2] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[2][0] == 0)) {
			m[2][0] = PC_MOVE;
			return 6;
		}
		if ((m[2][0] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[0][2] == 0)) {
			m[0][2] = PC_MOVE;
			return 2;
		}
		if ((m[1][0] == HUMAN_MOVE) && (m[1][2] == HUMAN_MOVE) && (m[1][1] == 0)) {
			m[1][1] = PC_MOVE;
			return 4;
		}
		if ((m[1][0] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[1][2] == 0)) {
			m[1][2] = PC_MOVE;
			return 5;
		}
		if ((m[1][2] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[1][0] == 0)) {
			m[1][0] = PC_MOVE;
			return 3;
		}
		if ((m[0][1] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[2][1] == 0)) {
			m[2][1] = PC_MOVE;
			return 7;
		}
		if ((m[1][1] == HUMAN_MOVE) && (m[2][1] == HUMAN_MOVE) && (m[0][1] == 0)) {
			m[0][1] = PC_MOVE;
			return 1;
		}
		if ((m[0][1] == HUMAN_MOVE) && (m[2][1] == HUMAN_MOVE) && (m[1][1] == 0)) {
			m[1][1] = PC_MOVE;
			return 4;
		}
		return -1;
	}

	public int Winner() {

		// if player wins
		if ((m[0][0] == HUMAN_MOVE) && (m[0][1] == HUMAN_MOVE) && (m[0][2] == HUMAN_MOVE))
			return 1;
		if ((m[0][0] == HUMAN_MOVE) && (m[1][0] == HUMAN_MOVE) && (m[2][0] == HUMAN_MOVE))
			return 1;
		if ((m[2][0] == HUMAN_MOVE) && (m[2][1] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE))
			return 1;
		if ((m[0][2] == HUMAN_MOVE) && (m[1][2] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE))
			return 1;
		if ((m[0][0] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[2][2] == HUMAN_MOVE))
			return 1;
		if ((m[0][2] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[2][0] == HUMAN_MOVE))
			return 1;
		if ((m[0][1] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[2][1] == HUMAN_MOVE))
			return 1;
		if ((m[1][0] == HUMAN_MOVE) && (m[1][1] == HUMAN_MOVE) && (m[1][2] == HUMAN_MOVE))
			return 1;
		// if pc wins
		if ((m[0][0] == PC_MOVE) && (m[0][1] == PC_MOVE) && (m[0][2] == PC_MOVE))
			return 2;
		if ((m[0][0] == PC_MOVE) && (m[1][0] == PC_MOVE) && (m[2][0] == PC_MOVE))
			return 2;
		if ((m[2][0] == PC_MOVE) && (m[2][1] == PC_MOVE) && (m[2][2] == PC_MOVE))
			return 2;
		if ((m[0][2] == PC_MOVE) && (m[1][2] == PC_MOVE) && (m[2][2] == PC_MOVE))
			return 2;
		if ((m[0][0] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[2][2] == PC_MOVE))
			return 2;
		if ((m[0][2] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[2][0] == PC_MOVE))
			return 2;
		if ((m[0][1] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[2][1] == PC_MOVE))
			return 2;
		if ((m[1][0] == PC_MOVE) && (m[1][1] == PC_MOVE) && (m[1][2] == PC_MOVE))
			return 2;

		return 0;
	}

	public boolean TableFull(int n[][]) {
		boolean full = true;
		for (int u = 0; u < 3; u++)
			for (int y = 0; y < 3; y++) {
				if (n[u][y] == 0)
					full = false;
			}
		return full;
	}

	public void setName(String name) {
		playerName = name;
	}

	public void CheckWin() {
		if (Winner() == HUMAN) {
			if (pvp) {
				JOptionPane.showMessageDialog(null, "Player 2 wins!");
				System.exit(0);
			} else {
				s.SoundIt(HUMAN);
				JOptionPane.showMessageDialog(null, "Player Wins");
				System.exit(0);
			}
		}

		if (Winner() == PC) {
			if (pvp) {
				JOptionPane.showMessageDialog(null, "Player 1 wins!");
				System.exit(0);
			} else {
				s.SoundIt(PC);
				JOptionPane.showMessageDialog(null, "I WIN!!");
				System.exit(0);
			}
		}

		if (TableFull(m)) {

			if (Winner() == 0) {
				JOptionPane.showMessageDialog(null, "It's a Tie");
				System.exit(0);
			}
		}
	}
}