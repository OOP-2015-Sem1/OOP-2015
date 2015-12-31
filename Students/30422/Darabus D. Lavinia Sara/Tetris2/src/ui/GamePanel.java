package ui;





import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;

import shapes.Shape;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;



public class GamePanel extends JPanel {
	
	private static final int BOARD_ROWS = 35;
	private static final int BOARD_COLS = 18;
	private static final int OFFSET = 7;
	public JLabel[][] squares = new JLabel[BOARD_ROWS][BOARD_COLS];
	public Color[][] squareColors = new Color[BOARD_ROWS][BOARD_COLS];
	public static Shape shape;
	public int currentRow, currentColumn;
    
	Timer timer;
	
	public GamePanel(){
		shape = new Shape();
		setLayout(new GridLayout(BOARD_ROWS, BOARD_COLS));
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				squares[i][j] = new JLabel();
				squares[i][j].setBackground(Color.LIGHT_GRAY);
				squareColors[i][j] = Color.LIGHT_GRAY;
				squares[i][j].setOpaque(true);
				squares[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(squares[i][j]);
				
			}
		}
				
		paintTetromino(shape.getPieceRow(), shape.getPieceCol(), shape.standardPiece);	
	}
	
	public void paintCell(int row, int col, Color color){
		squares[row][col].setBackground(color);
	}
	
	
	public void paintTetromino(int deltaRow, int deltaColumn, boolean[][] pieceMatrix){
		for(int i = 0; i < shape.getMatrixRows(); i++){
			for(int j = 0; j < (shape.getMatrixCols()); j++){
						if(pieceMatrix[i][j]){
							paintCell(i+deltaRow,j + deltaColumn ,shape.getRandomPieceColor());
						}
					}
			 }
	}	
	
	public void paintSquares(int deltaRow, int deltaColumn, boolean[][] pieceMatrix){
		for(int i = 0; i < shape.getMatrixRows(); i++){
					for(int j = 0; j < (shape.getMatrixCols()); j++){
								if(pieceMatrix[i][j]){
									
									squareColors[i+deltaRow][j + deltaColumn] = shape.getRandomPieceColor();
								}
							}
					 }
				
				}
			
	
		
		
		
	public void updateBoard(){
		/*currentRow = 0;
		currentColumn = 0;
		for(int i = 0; i < BOARD_ROWS; i = i+2){
			if(i == 0)
				currentRow = 0;
			else
				currentRow = i-2;
				
				paintTetromino(currentRow, currentColumn, Color.LIGHT_GRAY);
				
				paintTetromino(i, currentColumn, shape.randomPieceColor);
			}*/
		paintBoard();
		checkIfMoveWasLegal(shape.standardPiece);
		paintTetromino(shape.getPieceRow(), shape.getPieceCol(), shape.standardPiece);
		checkIfFallingTerminated(shape.standardPiece);
		
	}
	
	public void checkIfMoveWasLegal(boolean[][] pieceMatrix){
		for(int i = 0; i < shape.getMatrixRows(); i++){
			for(int j = 0; j < (shape.getMatrixCols()); j++){
						if(pieceMatrix[i][j]){
							if(((j + shape.getPieceCol()) > (BOARD_COLS - 1)) || ((j + shape.getPieceCol()) < 0) ||
							((i + shape.getPieceRow()) > (BOARD_ROWS - 1)) || //check for bounds
							((squares[i + shape.getPieceRow()][j + shape.getPieceCol()].getBackground()) != 
							Color.LIGHT_GRAY)){                                //check for other pieces already there
								shape.setPieceRow(shape.getPrevPieceRow());
								shape.setPieceCol(shape.getPrevPieceCol());
								shape.setMatrixRows(shape.getPrevMatrixRows());
								shape.setMatrixCols(shape.getPrevMatrixCols());
								shape.setLegal(false);
							}
							else{
								shape.setLegal(true);
							}
							
						}
					}
			 }
		}
		
	public void checkIfFallingTerminated(boolean[][] pieceMatrix){
		for(int i = 0; i < shape.getMatrixRows(); i++){
			for(int j = 0; j < (shape.getMatrixCols()); j++){
						if(pieceMatrix[i][j]){
							if((i + shape.getPieceRow()) == (BOARD_ROWS - 1) || 
									(squareColors[i + shape.getPieceRow() + 1][j + shape.getPieceCol()] != 
									Color.LIGHT_GRAY)){
								paintSquares(shape.getPieceRow(),shape.getPieceCol(),shape.standardPiece);
			shape = new Shape();
							}
						}
			}
		}	
	}
	
	public void paintBoard(){
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				squares[i][j].setBackground(squareColors[i][j]);
			}
		}
	}
}

