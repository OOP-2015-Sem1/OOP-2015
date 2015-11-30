package fluffy.the.cat.io;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.text.Position;

import fluffly.the.cat.game.BoardConfiguration;
import fluffly.the.cat.game.Game;

public class BoardFrame extends BoardPrinterClass{
	
	private MyPanel[][] panel;
	private static final int PATH = 0;
	private static final int WALL = 1;
	private static final int CAT= 2;
	private static final int HAT = 3;
	private Game theGame;
	public JFrame myFrame;
	
	public BoardFrame(Game theGame) {
		myFrame = new JFrame();
		myFrame.setTitle("The game");
		myFrame.setLayout(new GridLayout(20, 40));
		panel = new MyPanel[25][50];
		this.theGame = theGame;
		FluffyCommandKeyListener listener = new FluffyCommandKeyListener();
		myFrame.addKeyListener(listener);
		
		createInitialBoard(theGame.getBoardConfiguration());
	}
	
	public class FluffyCommandKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent e) {
			char key = e.getKeyChar();
			System.out.println(key);
			String command = "";
			command+=key;
			theGame.executeCommand(command);
			printTheBoard(theGame.getBoardConfiguration());
		}
	}
	
	
	protected void printTheBoard(BoardConfiguration board) {
		char[][] matrix = board.getBoard();
		int panelType;
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++) {
				if(super.withinRangeOfFluffy(i, j, board)) {
					panelType = getPanelType(matrix[i][j]);
				}
				else {
					panelType = 0;
				}
				
//				panel[i][j].setBackground(Color.BLACK);
				panel[i][j].setCareImagine(panelType);
		}
		Point p = theGame.getFluffy().getPosition();
		panel[p.x][p.y].setCareImagine(CAT);
		myFrame.repaint();
	}

	private int getPanelType(char x) {
		
		switch(x) {
		case '*': {
			return WALL;
		}
		case ' ': {
			return PATH;
		}
		case 'H': {
			return HAT;
		}
		case 'F': {
			return CAT;
		}
		case 'W' : {
			return HAT;
		}
		default : {
			return 0;
		}
		}
	}
	
	private void createInitialBoard(BoardConfiguration board) {
		
		char[][] matrix = board.getBoard();
		
		int panelType;
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++) {
				panelType = getPanelType(matrix[i][j]);
				panel[i][j] = new MyPanel();
				panel[i][j].setBackground(Color.BLACK);
				if(withinRangeOfFluffy(i, j, board)) {
					panelType = getPanelType(matrix[i][j]);
				}
				else {
					panelType = 0;
				}
				panel[i][j].setCareImagine(panelType);
				myFrame.add(panel[i][j]);
		}
		Point p = theGame.getFluffy().getPosition();
		panel[p.x][p.y].setCareImagine(CAT);
		myFrame.repaint();
	}

	@Override
	public void printConsole(BoardConfiguration board) {
		// TODO Auto-generated method stub
		
	}
}
