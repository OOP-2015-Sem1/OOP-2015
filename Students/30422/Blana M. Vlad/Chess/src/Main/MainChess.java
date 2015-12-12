package Main;

import javax.swing.JFrame;

import Pieces.Colors;
import Pieces.ListOfPieces;
import Pieces.Piece;
import Pieces.Queen;

public class MainChess {
	public static Board board = new Board();
	public static boolean whiteTurn = true;
	public static Piece emptySpace = new Piece();

	public static void main(String[] args) {
		emptySpace.setColor(Colors.WHITE);
		JFrame f = new JFrame("2 player Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		f.add(ui);
		f.setSize(1000, 1000);
		f.setVisible(true);

	}

	public static void makeMove(String move) {
		Piece[][] chessBoard = board.getBoard();
		try {
			if (move.charAt(4) == 'p') {
				chessBoard[Character.getNumericValue(move.charAt(2))][Character
						.getNumericValue(move.charAt(3))] = chessBoard[Character
						.getNumericValue(move.charAt(0))][Character
						.getNumericValue(move.charAt(1))];

				chessBoard[Character.getNumericValue(move.charAt(0))][Character
						.getNumericValue(move.charAt(1))] = emptySpace;
			}
		} catch (Exception e) {

			chessBoard[Character.getNumericValue(move.charAt(2))][Character
					.getNumericValue(move.charAt(3))] = chessBoard[Character
					.getNumericValue(move.charAt(0))][Character
					.getNumericValue(move.charAt(1))];

			chessBoard[Character.getNumericValue(move.charAt(0))][Character
					.getNumericValue(move.charAt(1))] = emptySpace;
		}
		for (int j = 0; j <= 7; j++) {
			if (chessBoard[0][j].getType() == ListOfPieces.PAWN) {
				chessBoard[0][j] = new Queen();
				if (whiteTurn == true) {
					chessBoard[0][j].setColor(Colors.WHITE);
				} else {
					chessBoard[0][j].setColor(Colors.BLACK);
				}
			}
		}
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				System.out.print(chessBoard[i][j].getType() + " ");
			}
			System.out.println();
		}
		System.out.println();
		MainChess.board.setBoard(chessBoard);
	}

	public static Boolean checkValidMove(String move) {
		Piece[][] chessBoard = board.getBoard();
		String list = "";
		list += chessBoard[Character.getNumericValue(move.charAt(0))][Character
				.getNumericValue(move.charAt(1))].possibleMove(
				Character.getNumericValue(move.charAt(0)),
				Character.getNumericValue(move.charAt(1)));
		if (list.replaceAll(move, "").length() < list.length()) {
			return true;// x1,y1,x2,y2,captured piece
		} else
			return false;
	}

	public static boolean kingSafety() {
		Piece[][] chessBoard = board.getBoard();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				// System.out.print(chessBoard[i][j].getType() + " ");
			}
			// System.out.println();
		}
		return true;
	}
}
