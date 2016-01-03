package chess.logic;

public class Piece {

	private int color;
	public static final int COLOR_WHITE = 0;
	public static final int COLOR_BLACK = 1;
	public boolean wkFlag = true;
	public boolean bkFlag = true;
	public boolean wrrFlag = true;
	public boolean wrlFlag = true;
	public boolean brrFlag = true;
	public boolean brlFlag = true;
	private int type;
	public static final int TYPE_ROOK = 1;
	public static final int TYPE_KNIGHT = 2;
	public static final int TYPE_BISHOP = 3;
	public static final int TYPE_QUEEN = 4;
	public static final int TYPE_KING = 5;
	public static final int TYPE_PAWN = 6;

	private int row;

	public static final int ROW_1 = 0;
	public static final int ROW_2 = 1;
	public static final int ROW_3 = 2;
	public static final int ROW_4 = 3;
	public static final int ROW_5 = 4;
	public static final int ROW_6 = 5;
	public static final int ROW_7 = 6;
	public static final int ROW_8 = 7;

	private int column;

	public static final int COLUMN_A = 0;
	public static final int COLUMN_B = 1;
	public static final int COLUMN_C = 2;
	public static final int COLUMN_D = 3;
	public static final int COLUMN_E = 4;
	public static final int COLUMN_F = 5;
	public static final int COLUMN_G = 6;
	public static final int COLUMN_H = 7;

	private boolean isCaptured = false;

	public Piece(int color, int type, int row, int column) {
		this.row = row;
		this.column = column;
		this.color = color;
		this.type = type;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		String strColor = (this.color == COLOR_WHITE ? "white" : "black");

		String strType = "unknown";
		switch (this.type) {
		case TYPE_BISHOP:
			strType = "B";
			break;
		case TYPE_KING:
			strType = "K";
			break;
		case TYPE_KNIGHT:
			strType = "N";
			break;
		case TYPE_PAWN:
			strType = "P";
			break;
		case TYPE_QUEEN:
			strType = "Q";
			break;
		case TYPE_ROOK:
			strType = "R";
			break;
		}

		String strRow = getRowString(this.row);
		String strColumn = getColumnString(this.column);

		return strColor + " " + strType + " " + strRow + "/" + strColumn;
	}

	public int getType() {
		return this.type;
	}

	public static String getRowString(int row) {
		String strRow = "unknown";
		switch (row) {
		case ROW_1:
			strRow = "1";
			break;
		case ROW_2:
			strRow = "2";
			break;
		case ROW_3:
			strRow = "3";
			break;
		case ROW_4:
			strRow = "4";
			break;
		case ROW_5:
			strRow = "5";
			break;
		case ROW_6:
			strRow = "6";
			break;
		case ROW_7:
			strRow = "7";
			break;
		case ROW_8:
			strRow = "8";
			break;
		}
		return strRow;
	}

	public static String getColumnString(int column) {
		String strColumn = "unknown";
		switch (column) {
		case COLUMN_A:
			strColumn = "A";
			break;
		case COLUMN_B:
			strColumn = "B";
			break;
		case COLUMN_C:
			strColumn = "C";
			break;
		case COLUMN_D:
			strColumn = "D";
			break;
		case COLUMN_E:
			strColumn = "E";
			break;
		case COLUMN_F:
			strColumn = "F";
			break;
		case COLUMN_G:
			strColumn = "G";
			break;
		case COLUMN_H:
			strColumn = "H";
			break;
		}
		return strColumn;
	}

	public void isCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public boolean isCaptured() {
		return this.isCaptured;
	}

}
