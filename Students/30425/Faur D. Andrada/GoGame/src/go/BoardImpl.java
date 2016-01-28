package go;

/*
import javax.swing.JOptionPane;
import go.Board;
import go.Move;
*/

class BoardImpl implements MutableBoard {

	BoardImpl(int n) {
		this.n = n;
		stones = new Stone[n][n];
		clear(n);
	}

	private void clear(int n) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				stones[i][j] = Stone.vacant;
	}

	@Override
	public int size() {
		return n;
	}

	@Override
	public Stone at(int x, int y) {
		return stones[x][y];
	}

	@Override
	public void setAt(int x, int y, Stone color) {
		stones[x][y] = color;
	}

	final int n;
	private final Stone[][] stones;

}