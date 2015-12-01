import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FluffyFileReader {
	private static char[][] board;
	private static final int maxRows = 10;
	private static final int maxCols = 40;

	public static void readBoard() {
		char[][] temporary = new char[maxRows][maxCols];
		board = new char[maxRows][maxCols];
		try {
			String line;
			FileReader fileReader = new FileReader("FluffyWorld.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int rowC = 0;
			while ((line = bufferedReader.readLine()) != null) {
				int colC = 0;
				for (char c : line.toCharArray()) {
					temporary[rowC][colC++] = c;
				}
				rowC++;
			}
			bufferedReader.close();
			for (int i = 0; i < maxRows; i++)
				for (int j = 0; j < maxCols; j++) {
					board[i][j] = temporary[i][j];
				}
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + "FluffyWorld" + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + "FluffyWorld" + "'");
		}
	}

	public static char[][] getBoard() {
		return board;
	}

	public static int getCols() {
		return maxCols;
	}

	public static int getRows() {
		return maxRows;
	}

	public static void main(String[] args) {
		readBoard();
		char[][] temporary = getBoard();
		for (int i = 0; i < maxRows; i++)
			for (int j = 0; j < maxCols; j++) {
				System.out.println(temporary[i][j]);
			}
	}

}
