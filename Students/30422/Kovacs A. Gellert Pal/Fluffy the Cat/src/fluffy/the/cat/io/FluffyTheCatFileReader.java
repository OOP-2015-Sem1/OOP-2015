package fluffy.the.cat.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FluffyTheCatFileReader {
	private final String fileName;

	public FluffyTheCatFileReader(String fileName) {
		this.fileName = fileName;
	}

	public void readBoard(char[][] board) {

		try {
			String line;
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int rowC = 0;
			while ((line = bufferedReader.readLine()) != null) {
				int colC = 0;
				for (char c : line.toCharArray()) {
					board[rowC][colC++] = c;
				}
				rowC++;
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}
}
