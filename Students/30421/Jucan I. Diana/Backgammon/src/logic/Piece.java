package logic;

public class Piece {

	private int color;
	// Backgammon is played on a board of 23 rows and 16 columns

	private int row;

	public static final int ROW_1 = 1;
	public static final int ROW_2 = 2;
	public static final int ROW_3 = 3;
	public static final int ROW_4 = 4;
	public static final int ROW_5 = 5;
	public static final int ROW_6 = 6;
	public static final int ROW_7 = 7;
	public static final int ROW_8 = 8;
	public static final int ROW_9 = 9;
	public static final int ROW_10 = 10;
	public static final int ROW_11 = 11;
	public static final int ROW_12 = 12;
	public static final int ROW_13 = 13;
	public static final int ROW_14 = 14;
	public static final int ROW_15 = 15;
	public static final int ROW_16 = 16;
	public static final int ROW_17 = 17;
	public static final int ROW_18 = 18;
	public static final int ROW_19 = 19;
	public static final int ROW_20 = 20;
	public static final int ROW_21 = 21;
	public static final int ROW_22 = 22;
	public static final int ROW_23 = 23;

	private int column;

	public static final int COLUMN_1 = 0;
	public static final int COLUMN_2 = 1;
	public static final int COLUMN_3 = 2;
	public static final int COLUMN_4 = 3;
	public static final int COLUMN_5 = 4;
	public static final int COLUMN_6 = 5;
	public static final int COLUMN_7 = 6;
	public static final int COLUMN_8 = 7;
	public static final int COLUMN_9 = 8;
	public static final int COLUMN_10 = 9;
	public static final int COLUMN_11 = 10;
	public static final int COLUMN_12 = 11;
	public static final int COLUMN_13 = 12;
	public static final int COLUMN_14 = 13;
	public static final int COLUMN_15 = 14;
	public static final int COLUMN_16 = 15;

	private boolean isCaptured = false;

	public Piece(int color, int row, int column) {
		this.row = row;
		this.column = column;
		this.color = color;
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

	public void isCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public boolean isCaptured() {
		return this.isCaptured;
	}

}
