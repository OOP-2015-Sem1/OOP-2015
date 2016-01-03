package chess.logic;

public class Move {
	public int sourceRow;
	public int sourceColumn;
	public int targetRow;
	public int targetColumn;
	
	public Move(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		this.sourceRow = sourceRow;
		this.sourceColumn = sourceColumn;
		this.targetRow = targetRow;
		this.targetColumn = targetColumn;
	}
}
