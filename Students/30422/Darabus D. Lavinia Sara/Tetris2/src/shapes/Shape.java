package shapes;

import java.awt.Color;
import java.util.Random;

public class Shape {

	private static final boolean[][] I_PIECE = { { true, true, true, true }, {false, false, false, false}};

	private static final boolean[][] J_PIECE = { { true, false, false, false }, { true, true, true, false } };

	private static final boolean[][] L_PIECE = { { false, false, true, false }, { true, true, true, false } };

	private static final boolean[][] O_PIECE = { { true, true, false, false }, { true, true, false, false } };

	private static final boolean[][] S_PIECE = { { false, true, true, false }, { true, true, false, false } };

	private static final boolean[][] T_PIECE = { { false, true, false, false }, { true, true, true, false } };

	private static final boolean[][] Z_PIECE = { { true, true, false, false }, { false, true, true, false } };
	
	

	public static boolean[][][] TETROMINOES = { 
			I_PIECE, J_PIECE, L_PIECE, O_PIECE, S_PIECE, T_PIECE, Z_PIECE };

	private static Color[] TETROMINO_COLORS = 
		{ Color.red, Color.yellow, Color.magenta, Color.pink, Color.cyan, Color.green, Color.orange };
	
	private static int DEFAULT_PIECE_ROW = 0, DEFAULT_PIECE_COL = 10;
	
	public static int TETROMINO_MATRIX_ROWS = 2, TETROMINO_MATRIX_COLS = 4;

	private int pieceRow, pieceCol;
	
	public Color randomPieceColor;
	public int randomPieceNumber;
	
	public boolean[][] fallingPiece;
	
	
	public Shape(){
		this.generateNewFallingPiece();
		this.generatePieceColor();
		
	}

	public void setPieceRow(int row) {
		this.pieceRow = row;
	}

	public int getPieceRow() {
		return this.pieceRow;
	}

	public void setPieceCol(int y) {
		this.pieceCol = y;
	}

	public int getPieceCol() {
		return this.pieceCol;
	}

	public int generateRandomValue() {

		Random random = new Random();
		int randomValue = Math.abs(random.nextInt() % 6 + 1);
		
		return randomValue;

	}

	public void generateNewFallingPiece(){
		
		 this.randomPieceNumber = generateRandomValue();
		 
		 
		 
	
	}

	public void generatePieceColor(){
		int randomColorNumber = generateRandomValue();
		this.randomPieceColor = TETROMINO_COLORS[randomColorNumber];
		System.out.println("Color is: " + randomPieceColor);
	}
	
	

	public static void move(String direction) {
		// TODO Auto-generated method stub

	}

}
