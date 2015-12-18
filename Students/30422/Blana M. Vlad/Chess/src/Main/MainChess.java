package Main;

import PieceManipulation.Colors;
import PieceManipulation.NoPiece;
import PieceManipulation.Piece;

public class MainChess {
	public static Board board = new Board();
	public static boolean whiteTurn = true;
	public static Piece emptySpace = new NoPiece();
	public static String whiteKingX = "7";
	public static String whiteKingY = "4";
	public static String blackKingX = "7";
	public static String blackKingY = "3";

	// public ChessFrame chessFrame = new ChessFrame();

	public static void main(String[] args) {
		emptySpace.setColor(Colors.WHITE);
		new ChessFrame();
	}

}
