package ui;





import java.awt.Color;
import java.awt.GridLayout;
import shapes.Shape;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;



public class GamePanel extends JPanel {
	
	private static final int BOARD_ROWS = 40;
	private static final int BOARD_COLS = 21;
	public JLabel[][] squares = new JLabel[BOARD_ROWS][BOARD_COLS];;
	public Shape shape;

	
	
	public GamePanel(){
		
		setLayout(new GridLayout(BOARD_ROWS, BOARD_COLS));
		shape = new Shape();
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				squares[i][j] = new JLabel();
				squares[i][j].setBackground(Color.LIGHT_GRAY);
				squares[i][j].setOpaque(true);
				squares[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				add(squares[i][j]);
			}
		}
		paintTetromino();
	}
	
	public void paintCell(int row, int col, Color color){
		squares[row][col].setBackground(color);
	}
	
	
	public void paintTetromino(){
		for(int i = 0; i < shape.TETROMINO_MATRIX_ROWS; i++){
			for(int j = 0; i < shape.TETROMINO_MATRIX_COLS; j++){
						if(shape.TETROMINOES[shape.randomPieceNumber][i][j]){
							paintCell(i,j,shape.randomPieceColor);
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

