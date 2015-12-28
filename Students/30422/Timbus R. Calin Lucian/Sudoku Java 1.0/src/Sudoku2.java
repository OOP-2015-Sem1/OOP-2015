
public class Sudoku2 {

	protected Square2[][] myMatrix = new Square2[3][3];
	protected int[][] sudokuMatrix = new int[9][9];

	public Sudoku2() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				myMatrix[i][j] = new Square2();
			}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				myMatrix[i][j].permuteLinesNrOfTimes(i + 2 * j);// j
				myMatrix[i][j].permuteColumnsNrOfTimes(j + i + 2 * j);// i
			}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				int indl = 3 * i;
				int indc = 3 * j;
				for (int dl = 0; dl < 3; dl++)
					for (int dc = 0; dc < 3; dc++) {
						sudokuMatrix[indl + dl][indc + dc] = myMatrix[i][j].getElement(dl, dc);
					}
			}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				System.out.print(sudokuMatrix[i][j] + " ");
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		new Sudoku2();

	}
}
