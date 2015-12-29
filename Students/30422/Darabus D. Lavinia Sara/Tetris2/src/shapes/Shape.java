package shapes;

import java.awt.Color;
import java.util.Random;

import core.TetrominoController;

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
	
	public static int TETROMINO_MATRIX_ROWS = 2, TETROMINO_MATRIX_COLS = 4;

	private int pieceRow, pieceCol, prevPieceRow, prevPieceCol;
	
	private Color randomPieceColor;
	private int randomPieceNumber;
	
	public boolean[][] fallingPiece;
	
	
	public Shape(){
		this.generateNewFallingPiece();
		this.generatePieceColor();
		this.setPieceRow(0);
		this.setPieceCol(7);
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
	
	public void setPrevPieceRow(int prevRow){
		this.prevPieceRow = prevRow;
	}
	
	public int getPrevPieceRow(){
		return this.prevPieceRow;
	}
	
	public void setPrevPieceCol(int prevCol){
		this.prevPieceCol = prevCol;
	}
	
	public int getPrevPieceCol(){
		return this.prevPieceCol;
	}

	public int generateRandomValue() {

		Random random = new Random();
		int randomValue = Math.abs(random.nextInt() % 6 + 1);
		
		return randomValue;

	}

	public void generateNewFallingPiece(){
		
		 this.randomPieceNumber = generateRandomValue();
		 
		 }
	
	public int getRandomPieceNumber(){
		return this.randomPieceNumber;
	}

	public void generatePieceColor(){
		int randomColorNumber = generateRandomValue();
		this.randomPieceColor = TETROMINO_COLORS[randomColorNumber];
		//System.out.println("Color is: " + randomPieceColor);
	}
	
	public Color getRandomPieceColor(){
		return this.randomPieceColor;
	}

	public static void move(String direction, Shape shape) {
		// TODO Auto-generated method stub
		if(direction.equals("DOWN")){
			shape.setPrevPieceRow(shape.getPieceRow());
			shape.setPrevPieceCol(shape.getPieceCol());
			shape.setPieceRow(shape.getPieceRow() + 1);
		}
		else if(direction.equals("RIGHT")){
			shape.setPrevPieceRow(shape.getPieceRow());
			shape.setPrevPieceCol(shape.getPieceCol());
			shape.setPieceCol(shape.getPieceCol() + 1);
		}
		else if(direction.equals("LEFT")){
			shape.setPrevPieceRow(shape.getPieceRow());
			shape.setPrevPieceCol(shape.getPieceCol());
			shape.setPieceCol(shape.getPieceCol() -1);
		}
		
	}

}
