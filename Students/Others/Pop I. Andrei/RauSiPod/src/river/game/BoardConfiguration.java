package river.game;

import java.awt.Point;
import java.util.List;

import river.io.BoardPrinter;
import river.models.Curl;
import river.models.Entity;

public class BoardConfiguration {
	
	public static final int ROWS = 15;
	public static final int COLS = 85;
	private char[][] board = new char[ROWS][COLS];
	
	public char[][] getBoard(){
		return board;
	}
	
	public void updateBoard(List<Entity> entities) {
		
		
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j<COLS; j++) {
			if(i==ROWS-1)
				board[i][j] = '=';
			else
				board[i][j] = '.';
			}
		}
		
		Point location;
		for(Entity e : entities) {
			if(e.getBoardRepresentation() == 'C'){
				Curl c = (Curl) e;
				if(c.isOnRiver()) {
					location = c.getPosition();
					board[location.x][location.y] = 'C';
				}
			}
			else {
				location = e.getPosition();
				board[location.x][location.y] = e.getBoardRepresentation();
			}
			//System.out.println(e.getBoardRepresentation()+ " " + e.getPosition().x + " " + e.getPosition().y);

		}
		
	}
	
	public void printBoard(BoardPrinter printer) {
		printer.printTheBoard(this);
	}
	
	public boolean isOcupiedByBoat(Point position) {
		if(board[position.x][position.y] == 'B')
			return true;
		else
			return false;
	}
	
}
