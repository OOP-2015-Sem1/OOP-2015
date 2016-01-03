package go;

import java.awt.*;
import java.util.*;

public class Game {
	Game(int n) {
		board = new BoardImpl(n);
	}

	Move makeMove(Point point) {
		Move move = new Move(turn, point);
		move.make(board);
		moves.add(move);
		turn = turn.otherColor();
		return move;
	}

	Move unmove() {
		if (moves.size() > 0) {
			Move move = moves.remove(moves.size() - 1);
			move.unmake(board);
			turn = turn.otherColor();
			return move;
		} else {
			System.out.println("no moves to undo!");
			return null;
		}
	}
	/*
	 * Move pass(){
	 * 
	 * turn = turn.otherColor(); return null; }
	 */

	final MutableBoard board;
	Stone turn = Stone.black;
	final java.util.List<Move> moves = new LinkedList<Move>();

}
