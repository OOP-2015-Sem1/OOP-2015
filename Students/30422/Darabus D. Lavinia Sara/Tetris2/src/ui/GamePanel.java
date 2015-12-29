package ui;





import java.awt.Color;
import java.awt.GridLayout;
import shapes.Shape;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;



public class GamePanel extends JPanel {
	
	private static final int BOARD_ROWS = 35;
	private static final int BOARD_COLS = 18;
	private static final int OFFSET = 7;
	public JLabel[][] squares = new JLabel[BOARD_ROWS][BOARD_COLS];;
	public static Shape shape;
	public int currentRow, currentColumn;
	
	
	public GamePanel(){
		shape = new Shape();
		setLayout(new GridLayout(BOARD_ROWS, BOARD_COLS));
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				squares[i][j] = new JLabel();
				squares[i][j].setBackground(Color.LIGHT_GRAY);
				squares[i][j].setOpaque(true);
				squares[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(squares[i][j]);
			}
		}
				
		paintTetromino(shape.getPieceRow(), shape.getPieceCol());	
	}
	
	public void paintCell(int row, int col, Color color){
		squares[row][col].setBackground(color);
	}
	
	
	public void paintTetromino(int deltaRow, int deltaColumn){
		for(int i = 0; i < shape.TETROMINO_MATRIX_ROWS; i++){
			for(int j = 0; j < (shape.TETROMINO_MATRIX_COLS); j++){
						if(shape.TETROMINOES[shape.getRandomPieceNumber()][i][j]){
							paintCell(i+deltaRow,j + deltaColumn ,shape.getRandomPieceColor());
						}
					}
			 }
	}	
	
	public void paintGrey(){
		
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				
				squares[i][j].setBackground(Color.LIGHT_GRAY);
				squares[i][j].setOpaque(true);
				squares[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		paintGrey();
		checkIfMoveWasLegal();
		paintTetromino(shape.getPieceRow(), shape.getPieceCol());
		
	}
	
	public void checkIfMoveWasLegal(){
		for(int i = 0; i < shape.TETROMINO_MATRIX_ROWS; i++){
			for(int j = 0; j < (shape.TETROMINO_MATRIX_COLS); j++){
						if(shape.TETROMINOES[shape.getRandomPieceNumber()][i][j]){
							if(((j + shape.getPieceCol()) > (BOARD_COLS - 1)) || ((j + shape.getPieceCol()) < 0) ||
							((i + shape.getPieceRow()) > (BOARD_ROWS - 1)) || //check for bounds
							((squares[i + shape.getPieceRow()][j + shape.getPieceCol()].getBackground()) != 
							Color.LIGHT_GRAY)){                                //check for other pieces already there
								shape.setPieceRow(shape.getPrevPieceRow());
								shape.setPieceCol(shape.getPrevPieceCol());
							}
						}
					}
			 }
	}
		
			
		
	
		
		/*for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				squares[i][j] = new JLabel();
				if((i == 36) && (j == 10))
					squares[i][j].setBackground(Color.RED);
				else if((i == 37) && (j == 10))
					squares[i][j].setBackground(Color.RED);
				else if((i == 38) && (j == 10))
					squares[i][j].setBackground(Color.RED);
				else if((i == 39) && (j == 10))
					squares[i][j].setBackground(Color.RED);
				else{ 
					squares[i][j].setBackground(Color.WHITE);
				}
				squares[i][j].setOpaque(true);
				squares[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(squares[i][j]);
			}
		}*/
	}

