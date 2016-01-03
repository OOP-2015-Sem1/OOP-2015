package models;
import repositories.FluffyFileReader;

/**
 * This is the back-end for our game
 * @author TimbusBlanaToderici Crew
 *
 */
public class Game {
	private char[][] gameMatrix;
	private char[][] cluesMatrix; 
	private Fluffy fluffy;
	private int steps = 0;
	
	public Game()
	{
		fluffy = new Fluffy(0, 0);
		createMatrix();
		
	}
	
	private void createMatrix() {
		int maxRows = FluffyFileReader.getRows();
		int maxCols = FluffyFileReader.getCols();

		cluesMatrix = new char[maxRows][maxCols];
		gameMatrix =  new char[maxRows][maxCols];


		FluffyFileReader.readBoard();
		gameMatrix = FluffyFileReader.getGameBoard();
		for (int i = 0; i < maxRows; i++)
			for (int j = 0; j < maxCols; j++) {				
				if (gameMatrix[i][j] == 'F') {
					fluffy.setFluffyX(i);
					fluffy.setFluffyY(j);
				}
			
			}

		cluesMatrix = FluffyFileReader.getCluesBoard();

	}
	

	
	public int getNbOfSteps()
	{
		return steps;
	}
	
	public char[][] getGameMatrix()
	{
		return gameMatrix;
	}
	
	public char[][] getCluesMatrix()
	{
		return cluesMatrix;
	}
	
	public Fluffy getFluffy()
	{
		return fluffy;
	
	}
	

}
