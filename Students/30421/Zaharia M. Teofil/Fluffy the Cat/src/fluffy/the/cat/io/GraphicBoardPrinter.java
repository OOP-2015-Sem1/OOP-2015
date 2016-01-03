package fluffy.the.cat.io;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fluffly.the.cat.game.BoardConfiguration;

public class GraphicBoardPrinter extends AbstractBoardPrinter {

	private JFrame frame;
	private JFrame grid;
	
	public GraphicBoardPrinter() {
		frame = new JFrame();
		grid = null;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
	
		frame.setVisible(true);
	}
	
	public void print(BoardConfiguration board) {
		char[][] boardCell = board.getBoard();
		
		int rowCount = boardCell.length;
		int colCount = boardCell[0].length;
		
		if (grid != null) {
			frame.getContentPane().remove(grid);
		}
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(rowCount, colCount));
		frame.getContentPane().add(grid, BorderLayout.CENTER);
		
		grid.setLayout(new GridLayout(rowCount, colCount));
		
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				JPanel cell = new JPanel();
				
				if (this.withinRangeOfFluffy(i, j, board)) {
					switch (boardCell[i][j]) {
					case 'F' : cell.setBackground(Color.blue); break;
					case '*' : cell.setBackground(Color.black); break;
					case 'W' : cell.setBackground(Color.green); break;
					case 'H' : cell.setBackground(Color.cyan); break;
					default  : cell.setBackground(Color.LIGHT_GRAY); break;
					}
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
