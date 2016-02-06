package fluffly.the.cat.game.views;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fluffly.the.cat.game.BoardConfiguration;
import fluffy.the.cat.io.AbstractGameBoard;

public class BoardPanel extends AbstractGameBoard {

	private JPanel[][] panelMatrix;

	public BoardPanel(JPanel[][] panelMatrix) {
		this.panelMatrix = panelMatrix;
	}

	@Override
	public void print(BoardConfiguration boardConfiguration) {
		char[][] boardData = boardConfiguration.getBoard();
		int rows = boardData.length;
		int cols = boardData[0].length;
		JLabel[][] gameLabelMatrix =  new JLabel[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				gameLabelMatrix[i][j] = new JLabel();
				if (withinRangeOfFluffy(i, j, boardConfiguration)) {

					if (boardData[i][j] == '*') {

						panelMatrix[i][j].setBackground(Color.DARK_GRAY);
						//gameLabelMatrix[i][j].setIcon(new ImageIcon("wall.png"));
						//panelMatrix[i][j].add(gameLabelMatrix[i][j]);

					}
					if (boardData[i][j] == ' ') {
						
						panelMatrix[i][j].setBackground(Color.GRAY);
						
					}
					if (boardData[i][j] == 'F') {
						
						panelMatrix[i][j].setBackground(Color.GREEN);
						//gameLabelMatrix[i][j].setIcon(new ImageIcon("nyancat.png"));
						//panelMatrix[i][j].add(gameLabelMatrix[i][j]);
						
					}
					if (boardData[i][j] == 'H') {
						
						panelMatrix[i][j].setBackground(Color.ORANGE);

					}
					if (boardData[i][j] == 'W') {
						
						panelMatrix[i][j].setBackground(Color.RED);

					}

				} else {
					
					panelMatrix[i][j].setBackground(Color.BLACK);
				}
			}
		}

	}

}
