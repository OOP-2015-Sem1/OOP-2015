package go;

import go.GameBoard.State;

/**
 * Provides game logic.
 * 
 *
 */

public class Grid {

	public int captured;

	private final int SIZE;
	/**
	 * [row][column]
	 */
	private Stone[][] stones;

	public Grid(int size) {
		SIZE = size;
		stones = new Stone[SIZE][SIZE];
	}

	/**
	 * Adds Stone to Grid.
	 * 
	 * @param row
	 * @param col
	 * @param black
	 */
	public void addStone(int row, int col, State state) {
		Stone newStone = new Stone(row, col, state);
		stones[row][col] = newStone;
		// Check neighbors
		Stone[] neighbors = new Stone[4];
		// Don't check outside the board
		if (row > 0) {
			neighbors[0] = stones[row - 1][col];
		}
		if (row < SIZE - 1) {
			neighbors[1] = stones[row + 1][col];
		}
		if (col > 1) {
			neighbors[2] = stones[row][col - 1];
		}
		if (col < SIZE - 1) {
			neighbors[3] = stones[row][col + 1];
		}
		// Prepare Chain for this new Stone
		Chain finalChain = new Chain(newStone.state);
		for (Stone neighbor : neighbors) {
			// Do nothing if no adjacent Stone
			if (neighbor == null) {
				continue;
			}

			newStone.liberties--;
			neighbor.liberties--;

			// If it's different color than newStone check him
			if (neighbor.state != newStone.state) {
				checkStone(neighbor);

				continue;
			}

			if (neighbor.chain != null) {
				finalChain.join(neighbor.chain);
			}
		}
		finalChain.addStone(newStone);

		
	}

	/**
	 * Check liberties of Stone
	 * 
	 * @param stone
	 */

	public void checkStone(Stone stone) {
		// Every Stone is part of a Chain so we check total liberties
		if (stone.chain.getLiberties() == 0) {
			captured = 0;
			for (Stone s : stone.chain.stones) {
				s.chain = null;
				stones[s.row][s.col] = null;
				captured++;
				//System.out.println(captured + "this is the capture!");
			}
			/*
			 * if(stone.state==State.BLACK){ scoreBlack-=captured;
			 * System.out.println(scoreBlack+" black after capture?"); }else{
			 * scoreWhite-=captured; System.out.println(scoreWhite+
			 * " white after capture?"); }
			 */

		} else {

		}
	}

	/**
	 * Returns true if given position is occupied by any stone
	 * 
	 * @param row
	 * @param col
	 * @return true if given position is occupied
	 */
	public boolean isOccupied(int row, int col) {
		return stones[row][col] != null;
	}

	/**
	 * Returns State (black/white) of given position or null if it's unoccupied.
	 * Needs valid row and column.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public State getState(int row, int col) {
		Stone stone = stones[row][col];
		if (stone == null) {
			return null;
		} else {
			// System.out.println("getState != null");
			return stone.state;
		}
	}

	
	public int getCapture(){
		return captured;
		
	}
	public void resetCapture(){
		captured=0;
	}
}