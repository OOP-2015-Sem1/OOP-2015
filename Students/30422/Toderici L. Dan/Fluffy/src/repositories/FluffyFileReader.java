package repositories;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FluffyFileReader {
	private static char[][] gameBoard;
	private static char[][] cluesBoard;
	private static final int maxRows = 10;
	private static final int maxCols = 40;
	private static Map<Character, String> icons = new HashMap<Character, String>();

	public static void readBoard() {
		char[][] temporary = new char[maxRows][maxCols];
		gameBoard = new char[maxRows][maxCols];
		cluesBoard = new char[maxRows][maxCols];
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
					gameBoard[i][j] = temporary[i][j];
					if(temporary[i][j] == 'H')
					{
						cluesBoard[i][j] = temporary[i][j];
					}
					else
					{
						cluesBoard[i][j] = ' ';
					}
				}
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + "FluffyWorld" + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + "FluffyWorld" + "'");
		}
		
		addIcons();
	}
	
	public static Map<Character, String> getIcons()
	{
		return icons;
	}
	
	public static void addIcons()
	{
			icons.put(' ',"lane.png");
			icons.put('F',"cat.png");
			icons.put('*',"wall.png");
			icons.put('H',"wall2.png");
			icons.put('W',"hat.png");
	}


	public static char[][] getGameBoard() 
	{
		return gameBoard;
	}
	
	public static char[][] getCluesBoard()
	{
		return cluesBoard;
	}

	public static int getCols() {
		return maxCols;
	}

	public static int getRows() {
		return maxRows;
	}

	public static void main(String[] args) {

	}

}
