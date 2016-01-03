package battleship.game.io;

import java.io.*;

public class ReadFromFile {
	
	private final String fileName;
	
	public ReadFromFile(String fileName) {
		this.fileName = fileName;
	}
	
	public void readContent(char[][] board) {
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader buffReader = new BufferedReader(fileReader);
			
			String line;
			
			int row = 0, col = 0;
			
			while((line = buffReader.readLine()) != null) {
				//System.out.println(line);
				col = 0;
				for(char c : line.toCharArray()){
					board[row][col] = c;
					col += 1;
				}
				row += 1;
			}
			
		}
		catch(FileNotFoundException  e) {
			System.out.println("The file: " + fileName + " could not be found");
		}
		catch(IOException e) {
			System.out.println("An error occured while reading");
		}
		
	}
	
}
