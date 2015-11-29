import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Point {

	int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}

class PointAndScore {

	int score;
	Point point;

	PointAndScore(int score, Point point) {
		this.score = score;
		this.point = point;
	}
}

class Board {

	List<Point> availablePoints;
	int[][] board = new int[3][3];

	public Board() {
	}

	public boolean isGameOver() {
		// Game is over is someone has won, or board is full (draw)
		return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
	}

	public boolean hasXWon() {
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
			// System.out.println("X Diagonal Win");
			return true;
		}
		for (int i = 0; i < 3; ++i) {
			if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
					|| (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
				// System.out.println("X Row or Column win");
				return true;
			}
		}
		return false;
	}

	public boolean hasOWon() {
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
			// System.out.println("O Diagonal Win");
			return true;
		}
		for (int i = 0; i < 3; ++i) {
			if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
					|| (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
				// System.out.println("O Row or Column win");
				return true;
			}
		}

		return false;
	}

	public List<Point> getAvailableStates() {
		availablePoints = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (board[i][j] == 0) {
					availablePoints.add(new Point(i, j));
				}
			}
		}
		return availablePoints;
	}

	public void placeAMove(Point point, int player) {
		board[point.x][point.y] = player; // player = 1 for X, 2 for O
	}

	public boolean isBoardEmpty() {
		boolean empty = true;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != 0)
					empty = false;
			}
		return empty;
	}

	public void displayBoard() {
		System.out.println();

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();

		}
	}

	Point computersMove;

	public int minimax(int depth, int turn) {
		if (hasXWon())
			return +1;
		if (hasOWon())
			return -1;

		List<Point> pointsAvailable = getAvailableStates();
		if (pointsAvailable.isEmpty())
			return 0;

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

		for (int i = 0; i < pointsAvailable.size(); i++) {
			Point point = pointsAvailable.get(i);
			if (turn == 1) {
				placeAMove(point, 1);
				int currentScore = minimax(depth + 1, 2);
				max = Math.max(currentScore, max);

				if (depth == 0)
					System.out.println("Score for position " + (i + 1) + " = " + currentScore);
				if (currentScore >= 0) {
					if (depth == 0)
						computersMove = point;
				}
				if (currentScore == 1) {
					board[point.x][point.y] = 0;
					break;
				}
				if (i == pointsAvailable.size() - 1 && max < 0) {
					if (depth == 0)
						computersMove = point;
				}
			} else if (turn == 2) {
				placeAMove(point, 2);
				int currentScore = minimax(depth + 1, 1);
				min = Math.min(currentScore, min);
				if (min == -1) {
					board[point.x][point.y] = 0;
					break;
				}
			}
			board[point.x][point.y] = 0; // Reset this point
		}
		return turn == 1 ? max : min;
	}
}

public class MinMax {

	Board b = new Board();

	public int HardWay(int playerMove) {

		System.out.println(playerMove);
		int pcMove = 0;
		if (b.isBoardEmpty() && (playerMove == 10)) {
			Random rand = new Random();
			Point p = new Point(rand.nextInt(3), rand.nextInt(3));
			b.placeAMove(p, 1);
			pcMove = p.x * 3 + p.y;
			return pcMove;
		}
		b.displayBoard();

		int x = playerMove / 3;
		int y = playerMove % 3;
		Point userMove = new Point(x, y);

		b.placeAMove(userMove, 2); // 2 for O and O is the user
		b.displayBoard();

		b.minimax(0, 1);

		System.out.println(b.computersMove + "\n");

		pcMove = b.computersMove.x * 3 + b.computersMove.y;

		b.placeAMove(b.computersMove, 1);
		b.displayBoard();

		return pcMove;

	}
}