package Main;

import pieces.Colors;
import pieces.NoPiece;
import pieces.Piece;

public class Controller {
	public MovementManager movementManager = new MovementManager();
	public Board board = new Board();
	public boolean whiteTurn = true;
	public Piece emptySpace = new NoPiece();
	public String whiteKingX = "7";
	public String whiteKingY = "4";
	public String blackKingX = "7";
	public String blackKingY = "3";

	public Controller() {

		emptySpace.setColor(Colors.WHITE);
	}
}
