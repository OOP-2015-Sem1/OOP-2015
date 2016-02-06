package fluffly.the.cat.game.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import fluffly.the.cat.game.BoardConfiguration;
import fluffy.the.cat.io.AbstractGameBoard;

public class GamePanel extends JPanel {
	

	private static final int PANEL_BORDER_SIZE = 40;

	JPanel[][] boardPanelMatrix;

	public GamePanel(String title, BoardConfiguration boardConfiguration) {
		
		
		char[][] boardData = boardConfiguration.getBoard();
		int rows = boardData.length;
		int cols = boardData[0].length;	
		
		this.setLayout(new GridLayout(rows, cols, 0, 0));
		this.setSize(cols*PANEL_BORDER_SIZE, rows*PANEL_BORDER_SIZE);
		
		
		//System.out.println(rows + " " + cols);
		
		boardPanelMatrix = new JPanel[rows][cols];
		for (int i = 0; i < rows; i++) 
			for(int j = 0; j < cols; j++) {
				boardPanelMatrix[i][j] = new JPanel();
				if (boardData[i][j]=='F') 
					boardPanelMatrix[i][j].setBackground(Color.GREEN);
				else
					boardPanelMatrix[i][j].setBackground(Color.BLACK);
				this.add(boardPanelMatrix[i][j]);
			}
		this.setVisible(true);
	}

	public JPanel[][] getPanelMatrix() {
		return boardPanelMatrix;
	}

	public void setDirectionKeyListener(KeyListener listener) {
		this.addKeyListener(listener);
	}

}