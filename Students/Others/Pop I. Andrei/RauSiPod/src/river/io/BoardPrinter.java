package river.io;

import river.game.BoardConfiguration;

public class BoardPrinter {
	
	private char[][] board;
	
	public void printTheBoard(BoardConfiguration bf) {
		this.board = bf.getBoard();
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
			}
			
			System.out.println();
		}
		
		System.out.println();
		
	}
	
	
	
	
}
