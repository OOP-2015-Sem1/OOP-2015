package fluffly.the.cat.game.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fluffly.the.cat.game.BoardConfiguration;
import fluffy.the.cat.io.AbstractGameBoard;

public class GamePanel extends AbstractGameBoard {

	private JFrame frame;
	private JFrame grid;
	ImageIcon cat = new ImageIcon("res/nyancat.png");
	
	public GamePanel() {
		frame = new JFrame();
		//grid = null;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		frame.setSize(800, 568);
	
		frame.setVisible(true);
	}
	
	public void print(BoardConfiguration board) {
		char[][] boardCell = board.getBoard();
		
		int rowCount = boardCell.length;
		int colCount = boardCell[0].length;
		
		/*if (grid != null) {
			frame.getContentPane().remove(grid);
		}*/
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(rowCount, colCount));
		//frame.add(grid, BorderLayout.CENTER); //frame.getContentPane().add(grid, BorderLayout.CENTER);
		frame.getContentPane().add(grid, BorderLayout.CENTER);
		grid.setLayout(new GridLayout(rowCount, colCount));
		
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				JPanel cell = new JPanel();
				JLabel imageLabel = new JLabel();
				
				if (this.withinRangeOfFluffy(i, j, board)) {
					if(boardCell[i][j] == 'F'){
						cell.setBackground(Color.blue);
						//imageLabel.setIcon(new ImageIcon("nyancat.png"));
						//cell.add(imageLabel);
						
					} 
					if(boardCell[i][j] == '*'){
						//cell.add(new ImagePanel(new ImageIcon("res/wall.png").getImage()));
						cell.setBackground(Color.black);
					}
					if(boardCell[i][j] == 'W'){
						cell.setBackground(Color.green);
					}
					if(boardCell[i][j] == 'H'){
						cell.setBackground(Color.cyan);
					}
					/*
					switch (boardCell[i][j]) {
					case 'F' : cell.setBackground(Color.blue); break;
					case '*' : cell.setBackground(Color.black); break;
					case 'W' : cell.setBackground(Color.green); break;
					case 'H' : cell.setBackground(Color.cyan); break;
					default  : cell.setBackground(Color.LIGHT_GRAY); break;
					}*/
				} else {
					cell.setBackground(Color.LIGHT_GRAY);
				}
				
				grid.add(cell);
			}
		}
		
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}