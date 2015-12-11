package Main;

import javax.swing.JFrame;

import Pieces.ListOfPieces;
import Pieces.Piece;

public class MainChess {
	static Board board;
	public static Piece chessBoard[][] = new Piece[8][8];
	public static boolean whiteTurn = true;

	public static void main(String[] args) {
		board = new Board(chessBoard);
		if (chessBoard[0][0].getType() == ListOfPieces.ROOK) {
			System.out.println("matai curva");
		}
		System.out.println(chessBoard[0][0].getType());
		System.out.println(ListOfPieces.ROOK);
		// chessBoard = Board.getBoard();

		JFrame f = new JFrame("2 player Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		f.add(ui);
		f.setSize(1000, 1000);
		f.setVisible(true);

	}

	public static void makeMove(String move) {
		Board.exchange(
				chessBoard[Character.getNumericValue(move.charAt(2))][Character
						.getNumericValue(move.charAt(3))], chessBoard[Character
						.getNumericValue(move.charAt(0))][Character
						.getNumericValue(move.charAt(1))]);
		chessBoard[Character.getNumericValue(move.charAt(0))][Character
				.getNumericValue(move.charAt(1))] = new Piece();
	}

	public static Boolean possibleMove(String move) {
		String list = "";
		// int r = Character.getNumericValue(move.charAt(0));
		// int c = Character.getNumericValue(move.charAt(1));
		for (int i = 0; i < 64; i++) {
			list += chessBoard[i % 8][i / 8].possibleMove(i % 8, i / 8);
		}
		if (list.replaceAll(move, "").length() < list.length()) {
			return true;// x1,y1,x2,y2,captured piece
		} else
			return false;
	}

	public static boolean kingSafety() {
		return true;
	}

	/*
	 * public static String possibleT(int r, int c) { // TURA String list = "";
	 * Piece oldPiece; for (int i = 0; i < 4; i++) { int ro = 0, co = 0;// row
	 * offset, column offset switch (i) { case 0: ro = 0; co = 1;// move right
	 * break; case 1: ro = 0; co = -1;// move left break; case 2: ro = -1; co =
	 * 0;// move up break; case 3: ro = 1; co = 0;// move down break; } try {//
	 * move while (" ".equals(chessBoard[r + ro][c + co])) { oldPiece =
	 * chessBoard[r + ro][c + co]; chessBoard[r][c] = " "; chessBoard[r + ro][c
	 * + co] = "T"; if (kingSafety() == true) { list = list + r + c + (r + ro) +
	 * (c + co) + oldPiece; } chessBoard[r][c] = "T"; chessBoard[r + ro][c + co]
	 * = oldPiece;
	 * 
	 * switch (i) { case 0: co++; break; case 1: co--; break; case 2: ro--;
	 * break; case 3: ro++; break; } }
	 * 
	 * } catch (Exception e) { }
	 * 
	 * try {// move and capture while (" ".charAt(0) != chessBoard[r + ro][c +
	 * co].charAt(0) && "PTCNQK".contains(chessBoard[r + ro][c + co]) == false)
	 * { oldPiece = chessBoard[r + ro][c + co]; chessBoard[r][c] = " ";
	 * chessBoard[r + ro][c + co] = "T"; if (kingSafety() == true) { list = list
	 * + r + c + (r + ro) + (c + co) + oldPiece; } chessBoard[r][c] = "T";
	 * chessBoard[r + ro][c + co] = oldPiece;
	 * 
	 * switch (i) { case 0: co++; break; case 1: co--; break; case 2: ro--;
	 * break; case 3: ro++; break; } }
	 * 
	 * } catch (Exception e) { } } return list; }
	 * 
	 * public static String possibleC(int r, int c) { // CAL String list = "",
	 * oldPiece; for (int i = 0; i < 8; i++) { int ro = 0, co = 0;// row offset,
	 * column offset switch (i) { case 0: ro = -2; co = 1; break; case 1: ro =
	 * -2; co = -1; break; case 2: ro = -1; co = 2; break; case 3: ro = -1; co =
	 * -2; break; case 4: ro = 1; co = -2; break; case 5: ro = 1; co = 2; break;
	 * case 6: ro = 2; co = -1; break; case 7: ro = 2; co = 1; break; } try {//
	 * move if (" ".equals(chessBoard[r + ro][c + co])) { oldPiece =
	 * chessBoard[r + ro][c + co]; chessBoard[r][c] = " "; chessBoard[r + ro][c
	 * + co] = "C"; if (kingSafety() == true) { list = list + r + c + (r + ro) +
	 * (c + co) + oldPiece; } chessBoard[r][c] = "C"; chessBoard[r + ro][c + co]
	 * = oldPiece;
	 * 
	 * }
	 * 
	 * } catch (Exception e) { } try {// move and capture if (" ".charAt(0) !=
	 * chessBoard[r + ro][c + co].charAt(0) && "PTCNQK".contains(chessBoard[r +
	 * ro][c + co]) == false) { oldPiece = chessBoard[r + ro][c + co];
	 * chessBoard[r][c] = " "; chessBoard[r + ro][c + co] = "C"; if
	 * (kingSafety() == true) { list = list + r + c + (r + ro) + (c + co) +
	 * oldPiece; } chessBoard[r][c] = "C"; chessBoard[r + ro][c + co] =
	 * oldPiece; } } catch (Exception e) { } } return list; }
	 * 
	 * public static String possibleN(int r, int c) { // NEBUN String list = "",
	 * oldPiece;
	 * 
	 * for (int i = 0; i < 4; i++) { int ro = 0, co = 0;// row offset, column
	 * offset switch (i) { case 0: ro = -1; co = 1;// move NE break; case 1: ro
	 * = -1; co = -1;// move NW break; case 2: ro = 1; co = 1;// move SE break;
	 * case 3: ro = 1; co = -1;// move SW break; } try {// move while
	 * (" ".equals(chessBoard[r + ro][c + co])) { oldPiece = chessBoard[r +
	 * ro][c + co]; chessBoard[r][c] = " "; chessBoard[r + ro][c + co] = "N"; if
	 * (kingSafety() == true) { list = list + r + c + (r + ro) + (c + co) +
	 * oldPiece; } chessBoard[r][c] = "N"; chessBoard[r + ro][c + co] =
	 * oldPiece;
	 * 
	 * switch (i) { case 0: ro--; co++; break; case 1: ro--; co--; break; case
	 * 2: ro++; co++; break; case 3: ro++; co--; break; } }
	 * 
	 * } catch (Exception e) { }
	 * 
	 * try {// move and capture while (" ".charAt(0) != chessBoard[r + ro][c +
	 * co].charAt(0) && "PTCNQK".contains(chessBoard[r + ro][c + co]) == false)
	 * { oldPiece = chessBoard[r + ro][c + co]; chessBoard[r][c] = " ";
	 * chessBoard[r + ro][c + co] = "N"; if (kingSafety() == true) { list = list
	 * + r + c + (r + ro) + (c + co) + oldPiece; } chessBoard[r][c] = "N";
	 * chessBoard[r + ro][c + co] = oldPiece;
	 * 
	 * switch (i) { case 0: ro--; co++; break; case 1: ro--; co--; break; case
	 * 2: ro++; co++; break; case 3: ro++; co--; break; } }
	 * 
	 * } catch (Exception e) { } } return list; }
	 * 
	 * public static String possibleQ(int r, int c) { // QUEEN String list = "",
	 * oldPiece; for (int i = 0; i < 4; i++) { int ro = 0, co = 0;// row offset,
	 * column offset switch (i) { case 0: ro = -1; co = 1;// move NE break; case
	 * 1: ro = -1; co = -1;// move NW break; case 2: ro = 1; co = 1;// move SE
	 * break; case 3: ro = 1; co = -1;// move SW break; } try {// move
	 * diagonally while (" ".equals(chessBoard[r + ro][c + co])) { oldPiece =
	 * chessBoard[r + ro][c + co]; chessBoard[r][c] = " "; chessBoard[r + ro][c
	 * + co] = "Q"; if (kingSafety() == true) { list = list + r + c + (r + ro) +
	 * (c + co) + oldPiece; } chessBoard[r][c] = "Q"; chessBoard[r + ro][c + co]
	 * = oldPiece;
	 * 
	 * switch (i) { case 0: ro--; co++; break; case 1: ro--; co--; break; case
	 * 2: ro++; co++; break; case 3: ro++; co--; break; } }
	 * 
	 * } catch (Exception e) { }
	 * 
	 * try {// move and capture diagonally while (" ".charAt(0) != chessBoard[r
	 * + ro][c + co].charAt(0) && "PTCNQK".contains(chessBoard[r + ro][c + co])
	 * == false) { oldPiece = chessBoard[r + ro][c + co]; chessBoard[r][c] =
	 * " "; chessBoard[r + ro][c + co] = "Q"; if (kingSafety() == true) { list =
	 * list + r + c + (r + ro) + (c + co) + oldPiece; } chessBoard[r][c] = "Q";
	 * chessBoard[r + ro][c + co] = oldPiece;
	 * 
	 * switch (i) { case 0: ro--; co++; break; case 1: ro--; co--; break; case
	 * 2: ro++; co++; break; case 3: ro++; co--; break; } }
	 * 
	 * } catch (Exception e) { } } for (int i = 0; i < 4; i++) { int ro = 0, co
	 * = 0;// row offset, column offset switch (i) { case 0: ro = 0; co = 1;//
	 * move right break; case 1: ro = 0; co = -1;// move left break; case 2: ro
	 * = -1; co = 0;// move up break; case 3: ro = 1; co = 0;// move down break;
	 * } try {// move orthogonally while (" ".equals(chessBoard[r + ro][c +
	 * co])) { oldPiece = chessBoard[r + ro][c + co]; chessBoard[r][c] = " ";
	 * chessBoard[r + ro][c + co] = "Q"; if (kingSafety() == true) { list = list
	 * + r + c + (r + ro) + (c + co) + oldPiece; } chessBoard[r][c] = "Q";
	 * chessBoard[r + ro][c + co] = oldPiece;
	 * 
	 * switch (i) { case 0: co++; break; case 1: co--; break; case 2: ro--;
	 * break; case 3: ro++; break; } }
	 * 
	 * } catch (Exception e) { }
	 * 
	 * try {// move orthogonally and capture while (" ".charAt(0) !=
	 * chessBoard[r + ro][c + co].charAt(0) && "PTCNQK".contains(chessBoard[r +
	 * ro][c + co]) == false) { oldPiece = chessBoard[r + ro][c + co];
	 * chessBoard[r][c] = " "; chessBoard[r + ro][c + co] = "Q"; if
	 * (kingSafety() == true) { list = list + r + c + (r + ro) + (c + co) +
	 * oldPiece; } chessBoard[r][c] = "Q"; chessBoard[r + ro][c + co] =
	 * oldPiece;
	 * 
	 * switch (i) { case 0: co++; break; case 1: co--; break; case 2: ro--;
	 * break; case 3: ro++; break; } }
	 * 
	 * } catch (Exception e) { } } return list; }
	 * 
	 * public static String possibleK(int r, int c) { // KING String list = "",
	 * oldPiece; for (int i = 0; i < 4; i++) { int ro = 0, co = 0;// row offset,
	 * column offset switch (i) { case 0: ro = -1; co = 1;// move NE break; case
	 * 1: ro = -1; co = -1;// move NW break; case 2: ro = 1; co = 1;// move SE
	 * break; case 3: ro = 1; co = -1;// move SW break; } try {// move
	 * diagonally if (" ".equals(chessBoard[r + ro][c + co])) { oldPiece =
	 * chessBoard[r + ro][c + co]; chessBoard[r][c] = " "; chessBoard[r + ro][c
	 * + co] = "K"; if (kingSafety() == true) { list = list + r + c + (r + ro) +
	 * (c + co) + oldPiece; } chessBoard[r][c] = "K"; chessBoard[r + ro][c + co]
	 * = oldPiece;
	 * 
	 * }
	 * 
	 * } catch (Exception e) { }
	 * 
	 * try {// move and capture diagonally if (" ".charAt(0) != chessBoard[r +
	 * ro][c + co].charAt(0) && "PTCNQK".contains(chessBoard[r + ro][c + co]) ==
	 * false) { oldPiece = chessBoard[r + ro][c + co]; chessBoard[r][c] = " ";
	 * chessBoard[r + ro][c + co] = "K"; if (kingSafety() == true) { list = list
	 * + r + c + (r + ro) + (c + co) + oldPiece; } chessBoard[r][c] = "K";
	 * chessBoard[r + ro][c + co] = oldPiece;
	 * 
	 * }
	 * 
	 * } catch (Exception e) { } } for (int i = 0; i < 4; i++) { int ro = 0, co
	 * = 0;// row offset, column offset switch (i) { case 0: ro = 0; co = 1;//
	 * move right break; case 1: ro = 0; co = -1;// move left break; case 2: ro
	 * = -1; co = 0;// move up break; case 3: ro = 1; co = 0;// move down break;
	 * } try {// move orthogonally if (" ".equals(chessBoard[r + ro][c + co])) {
	 * oldPiece = chessBoard[r + ro][c + co]; chessBoard[r][c] = " ";
	 * chessBoard[r + ro][c + co] = "K"; if (kingSafety() == true) { list = list
	 * + r + c + (r + ro) + (c + co) + oldPiece; } chessBoard[r][c] = "K";
	 * chessBoard[r + ro][c + co] = oldPiece;
	 * 
	 * }
	 * 
	 * } catch (Exception e) { }
	 * 
	 * try {// move orthogonally and capture if (" ".charAt(0) != chessBoard[r +
	 * ro][c + co].charAt(0) && "PTCNQK".contains(chessBoard[r + ro][c + co]) ==
	 * false) { oldPiece = chessBoard[r + ro][c + co]; chessBoard[r][c] = " ";
	 * chessBoard[r + ro][c + co] = "K"; if (kingSafety() == true) { list = list
	 * + r + c + (r + ro) + (c + co) + oldPiece; } chessBoard[r][c] = "K";
	 * chessBoard[r + ro][c + co] = oldPiece;
	 * 
	 * }
	 * 
	 * } catch (Exception e) { } } return list; }
	 */
}
