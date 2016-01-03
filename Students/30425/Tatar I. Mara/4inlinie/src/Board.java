import java.util.Arrays;

public class Board {

	private Chip[][] theArray;
	private boolean end = false;
	Chip activeColour = Chip.RED;
	// private boolean gameStart;

	private int rows;
	private int columns;

	public Board(Board board) {
		this.rows = board.rows;
		this.columns = board.columns;
		this.theArray = Arrays.copyOf(board.theArray, board.theArray.length);
		this.activeColour = board.activeColour;
		this.end = board.end;
	}

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		initialize();
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Chip[][] getArray() {
		return theArray;
	}

	public void putDisk(int n) {
		// put a disk on top of column n
		// if game is won, do nothing
		if (end)
			return;
		// gameStart = true;
		int row;
		for (row = 0; row < rows; row++)
			if (theArray[row][n] != Chip.BLANK)
				break;
		if (row > 0) {
			theArray[--row][n] = activeColour;
			toggleColour();
		}
	}
   public boolean isValidMove(int col) {
		boolean ok = false;
		for (int row = 0; row < rows; row++)
			if (theArray[row][col] == Chip.BLANK) {
				ok=true;
				break;
			}
		return ok;
	}

   public void removeDisk(int n){
		int row;
		for (row = rows-1; row >= 0; row--)
			if (theArray[row][n] == Chip.BLANK)
				break;
		if (row <rows-1) {
			theArray[++row][n] = Chip.BLANK;
			toggleColour();
		}
	}
	   
	private void toggleColour() {
		if (activeColour == Chip.RED)
			activeColour = Chip.YELLOW;
		else
			activeColour = Chip.RED;
	}

	public Chip check4() {
		// see if there are 4 disks in a row: horizontal, vertical or diagonal
		// horizontal rows
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns - 3; col++) {
				Chip curr = theArray[row][col];
				if (curr != Chip.BLANK && curr == theArray[row][col + 1] && curr == theArray[row][col + 2]
						&& curr == theArray[row][col + 3]) {

					return theArray[row][col];
				}
			}
		}

		// vertical columns
		for (int col = 0; col < columns; col++) {
			for (int row = 0; row < rows - 3; row++) {
				Chip curr = theArray[row][col];
				if (curr != Chip.BLANK && curr == theArray[row + 1][col] && curr == theArray[row + 2][col]
						&& curr == theArray[row + 3][col]) {
					return theArray[row][col];
				}
			}
		}
		// diagonal lower left to upper right
		for (int row = 0; row < rows - 3; row++) {
			for (int col = 0; col < columns - 3; col++) {
				Chip curr = theArray[row][col];
				if (curr != Chip.BLANK && curr == theArray[row + 1][col + 1] && curr == theArray[row + 2][col + 2]
						&& curr == theArray[row + 3][col + 3]) {
					return theArray[row][col];
				}
			}
		}
		// diagonal upper left to lower right
		for (int row = rows - 1; row >= 3; row--) {
			for (int col = 0; col < columns - 3; col++) {
				Chip curr = theArray[row][col];
				if (curr != Chip.BLANK && curr == theArray[row - 1][col + 1] && curr == theArray[row - 2][col + 2]
						&& curr == theArray[row - 3][col + 3]) {
					return theArray[row][col];
				}
			}
		}
		return null;
	}

	public void initialize() {
		theArray = new Chip[rows][columns];
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < columns; col++)
				theArray[row][col] = Chip.BLANK;
		// gameStart = false;
	}
}
