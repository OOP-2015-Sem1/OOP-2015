package fluffy.the.cat.io;

import static fluffly.the.cat.game.BoardConfiguration.MAX_VIEW_DISTANCE;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.text.Position;

import fluffly.the.cat.game.BoardConfiguration;
import fluffly.the.cat.game.Game;

public class BoardFrame extends JFrame{
	
	private MyPanel[][] panel;
	private static final int path = 0;
	private static final int wall = 1;
	private static final int cat= 2;
	private static final int hat = 3;
	private Game g;
	
	public BoardFrame(Game g) {
		super("The game");
		panel = new MyPanel[25][50];
		this.g = g;
		
		ListenClass listener = new ListenClass();
		addKeyListener(listener);
		
		createInitialBoard(g.getBoardConfiguration());
	}
	
	public class ListenClass implements KeyListener {

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
			g.executeCommand(command);
			printTheBoard(g.getBoardConfiguration());
		}
	}
	
	private boolean withinRangeOfFluffy(int i, int j, BoardConfiguration boardConfiguration) {
		int fluffyX = boardConfiguration.getFluffy().getPosition().x;
		int fluffyY = boardConfiguration.getFluffy().getPosition().y;

		double dist = Math.sqrt(Math.pow(fluffyX - i, 2)
				+ Math.pow(fluffyY - j, 2));

		return dist < MAX_VIEW_DISTANCE;
	}
	
	protected void printTheBoard(BoardConfiguration board) {
		char[][] matrix = board.getBoard();
		int panelType;
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++) {
				if(withinRangeOfFluffy(i, j, board)) {
					panelType = getPanelType(matrix[i][j]);
				}
				else {
					panelType = 0;
				}
				
//				panel[i][j].setBackground(Color.BLACK);
				panel[i][j].setCareImagine(panelType);
		}
		Point p = g.getFluffy().getPosition();
		panel[p.x][p.y].setCareImagine(cat);
		this.repaint();
	}

	private int getPanelType(char x) {
		
		switch(x) {
		case '*': {
			return wall;
		}
		case ' ': {
			return path;
		}
		case 'H': {
			return hat;
		}
		case 'F': {
			return cat;
		}
		case 'W' : {
			return hat;
		}
		default : {
			return 0;
		}
		}
	}
	
	private void createInitialBoard(BoardConfiguration board) {
		
		char[][] matrix = board.getBoard();
		
		setLayout(new GridLayout(20, 40));int panelType;
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++) {
				panelType = getPanelType(matrix[i][j]);
				panel[i][j] = new MyPanel();
				panel[i][j].setBackground(Color.BLACK);
				panel[i][j].setCareImagine(panelType);
				add(panel[i][j]);
		}
		Point p = g.getFluffy().getPosition();
		panel[p.x][p.y].setCareImagine(cat);
		this.repaint();
	}
}
