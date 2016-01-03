import java.util.List;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Comparator;

public class AI {

	private int movesAhead;
	private Chip me;

	public AI(int movesAhead) {
		this.movesAhead = movesAhead;

	}

	public int nextMove(Board board) {
		Board copy = new Board(board);
		List<MoveWinRate> winChances = new ArrayList<>();

		for (int i = 0; i < copy.getColumns(); i++) {
			if (copy.isValidMove(i)) {
				copy.putDisk(i);
				winChances.add(new MoveWinRate(i, computeMove(copy, movesAhead)));
				copy.removeDisk(i);
			}
		}

		winChances.sort(new Comparator<MoveWinRate>() {
			@Override
			public int compare(MoveWinRate o1, MoveWinRate o2) {
				return o2.rate.compareTo(o1.rate);
			}
		});

		if (winChances.size() > 0)
			return winChances.get(0).move;
		else
			return -1;

	}

	private double computeMove(Board board, int movesLeft) {
		Chip winner = board.check4();
		if (movesLeft == 0 || winner != null)
			return quantifyWinner(winner, movesLeft);
		else {
			double winValue = 0;
			for (int i = 0; i < board.getColumns(); i++) {
				if (board.isValidMove(i)) {
					board.putDisk(i);
					winValue += computeMove(board, movesLeft - 1);
					board.removeDisk(i);
				}
			}
			return winValue;

		}
	}

	private double quantifyWinner(Chip winner, int movesLeft) {
		int points;
		int sign=1;
		if (winner == me)
			points = 2;
		else if (winner == null)
			return 0;
		else{
			points = -2;
			if (movesLeft % 2 == 0) sign=-1;
		}
		return sign*Math.pow(points, movesLeft + 2);
	}

	private class MoveWinRate {
		public int move;
		public Double rate;

		public MoveWinRate(int move, Double rate) {
			this.move = move;
			this.rate = rate;
		}

		@Override
		public String toString() {
			return "M: " + move + " RATE: " + rate;
		}
	}

}