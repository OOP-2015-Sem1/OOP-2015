package Main;

import pieces.Colors;
import pieces.NoPiece;
import pieces.Piece;

public class Controller {
	public MovementManager movementManager = new MovementManager();
	public Board board = new Board();
	public boolean whiteTurn = true;
	public Piece emptySpace = new NoPiece();
	public Movement whiteKingPosition = new Movement();
	public Movement blackKingPosition = new Movement();
	public boolean enableLeftBlackCastle = true;
	public boolean enableRightBlackCastle = true;
	public boolean enableLeftWhiteCastle = true;
	public boolean enableRightWhiteCastle = true;

	public Controller() {
		emptySpace.setColor(Colors.WHITE);
		whiteKingPosition.setMove(9, 9, 7, 4, true);
		blackKingPosition.setMove(9, 9, 7, 3, true);
	}
}
