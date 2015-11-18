package fluffy.the.cat.io;

import java.awt.Color;

import javax.swing.JPanel;

import fluffly.the.cat.game.BoardConfiguration;

public class BoardWindowPrinter extends AbstractBoardPrinter {
	
	private JPanel[][] panelMatrix;

	public BoardWindowPrinter(JPanel[][] panelMatrix)
	{
		this.panelMatrix = panelMatrix;
	}
	
	public void print(BoardConfiguration boardConfiguration) {
		char[][] boardData = boardConfiguration.getBoard();
		int rows = boardData.length;
		int cols = boardData[0].length;	
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (withinRangeOfFluffy(i, j, boardConfiguration)) {
					switch(boardData[i][j]) {
					case ' ': panelMatrix[i][j].setBackground(Color.GRAY); break;
					case '*': panelMatrix[i][j].setBackground(Color.DARK_GRAY); break;
					case 'F': panelMatrix[i][j].setBackground(Color.GREEN); break;
					case 'H': panelMatrix[i][j].setBackground(Color.ORANGE); break;
					case 'W': panelMatrix[i][j].setBackground(Color.RED); break;
					default: panelMatrix[i][j].setBackground(Color.BLACK);
					}
				} else {
					panelMatrix[i][j].setBackground(Color.BLACK);
				}
			}
		}
		
	}
}
