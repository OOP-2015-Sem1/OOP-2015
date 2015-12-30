package battleship.game;


import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;

import battleship.game.io.BoardFrame;
import battleship.game.io.Fire;
import battleship.game.io.ReadFromFile;
import battleship.models.Ship;

public class BoardConfiguration extends Fire{
	
	private final int MAX_ROW = 10;
	private final int MAX_COL = 10;
	private char myBoard[][] = new char[MAX_ROW][MAX_COL];
	private char computerBoard[][] = new char[MAX_ROW][MAX_COL];
	private final BoardFrame boardFrame;
	private JButton[][] myBoardCells;
	private JButton[][] computerBoardCells;
	private ArrayList <Ship> myShips = new ArrayList<Ship>(); //  the ships will always be stored sorted by dimension: smallest -> biggest
	private ArrayList <Ship> computerShips = new ArrayList<Ship>();
	private Ship[] tempShips = new Ship[5];
	private final Game myGame;
	private final RandomGenerator generator;
	
	public BoardConfiguration(Game myGame) {
		
		this.myGame = myGame;
		ReadFromFile boardReader = new ReadFromFile("battle.txt");
		boardReader.readContent(computerBoard);
		initializeMyBoard();
		boardFrame = new BoardFrame(MAX_ROW, MAX_COL, this, myGame);
		myBoardCells = boardFrame.getMyBoardCells();
		computerBoardCells = boardFrame.getComputerBoardCells();
		generator = new RandomGenerator(MAX_ROW, MAX_COL, boardFrame);
		initializeMyShips();
	}
	
	
	public boolean hitSomeShip(Point location) {
		
		if(myBoard[location.x][location.y] == '~')
			return false;
		else
			return true;
	}
	
	public void activateComputer() {
		Point location = generator.generateHit();
		
		if(hitSomeShip(location)){
			myBoardCells[location.x][location.y].setBackground(Color.RED);
			identifyTheHitShip(location, myShips);
		}
		else
			myBoardCells[location.x][location.y].setBackground(Color.GRAY);
	}
	
	public void initializeMyBoard() {
		for(int i = 0; i < MAX_ROW; i++) 
			for(int j = 0; j < MAX_COL; j++) {
				myBoard[i][j] = '~';
			}
	}
	
	public void disableComputerBoard() {
		for(int i = 0; i < computerBoardCells.length; i++)
			for(int j = 0; j< computerBoardCells[0].length; j++) {
				computerBoardCells[i][j].setEnabled(false);
			}
	}
	
	public void enableComputerBoard() {
		for(int i = 0; i < computerBoardCells.length; i++)
			for(int j = 0; j< computerBoardCells[0].length; j++) {
				computerBoardCells[i][j].setEnabled(true);
			}
	}
	
	public void clearComputerBoard() { 
		for(int i = 0; i < myBoardCells.length; i++)
			for(int j = 0; j < myBoardCells[0].length; j++) {
				myBoardCells[i][j].setBackground(Color.BLACK);
			}
	}
	
	public void createMyNewShip(int shipIndex, int shipSize, String shipName) {
		Ship myShip = new Ship();
		myShip.setOrientation("horizontal");
		myShip.setName(shipName);
		myShip.setSize(shipSize);
		myShip.setLife(shipSize);
		myShips.add(shipIndex, myShip);
	}
	
	public void addComputerShip(Ship thisShip) {
		computerShips.add(thisShip);
	}
	
	public void saveComputerShipOnLocation(Point location, char[][] saveMatrix) {
		int size = saveMatrix[location.x][location.y] - '0';
		Ship computerShip = new Ship();
		computerShip.setLocation(location);
		computerShip.setSize(size);
		computerShip.setLife(size);
		
		int i = location.x;
		int j = location.y;
		
		if(i + 1 < saveMatrix.length && saveMatrix[i+1][j] == saveMatrix[i][j]) {
			computerShip.setOrientation("vertical");
		}
		else {
			computerShip.setOrientation("horizontal");
		}
		
		addComputerShip(computerShip);
	}
	
	public void removeShipFromMatrix(char[][] saveMatrix, Point location) {
		
		int x = location.x, y = location.y, i, j;
		int size = saveMatrix[x][y] - '0';
		
		if(x + 1 < saveMatrix.length && saveMatrix[x + 1][y] == saveMatrix[x][y]) { // the ship is placed vertically
			j = y;
			for(i = x; i < x + size; i++){
				saveMatrix[i][j] = '~';
			}
		}
		else {
			i = x;
			for(j = y; j < y + size; j++) {
				saveMatrix[i][j] = '~';
			}
		}
		
	}
	
	public char[][] makeACopyOfComputerBoard(){
		int size = computerBoard.length;
		char[][] saveMatrix = new char[size][size];
		
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++) 
				saveMatrix[i][j] = computerBoard[i][j];
		return saveMatrix;
	}
	
	public void saveComputerShips() {
		//char[][] saveMatrix = computerBoard.clone(); // why doesn't it work like this ????? 
		char[][] saveMatrix = makeACopyOfComputerBoard(); // the ships are removed from the saveMatrix after they're saved
		
		for(int i = 0; i < MAX_ROW; i++)
			for(int j = 0; j < MAX_COL; j++) {
				if(saveMatrix[i][j] != '~') {
					System.out.println(computerBoard[i][j]);
					saveComputerShipOnLocation(new Point(i, j), saveMatrix);
					removeShipFromMatrix(saveMatrix, new Point(i, j));
					printTheBoard(saveMatrix);
				}
			}
	}
	
	public void initializeMyShips() {
		
		createMyNewShip(0, 2, "Destroyer");
		createMyNewShip(1, 3, "Cruieser");
		createMyNewShip(2, 3, "Cruieser");
		createMyNewShip(3, 4, "Battleship");
		createMyNewShip(4, 5, "Aircraft Career");
		saveComputerShips();
	}
	
	public void printTheBoard(char[][] board) {
		for(int i = 0; i<MAX_ROW; i++) {
			for(int j = 0; j<MAX_COL; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public BoardFrame getTheBoardFrame() {
		return boardFrame;
	}
	
	public char[][] getMyBoard() {
		return myBoard;
	}
	
	public char[][] getComputerBoard() {
		return computerBoard;
	}
	
	public ArrayList<Ship> getMyShips() {
		return myShips;
	}
	
	public ArrayList<Ship> getComputerShips() {
		return computerShips;
	}
	
	public void startGame() {
		printTheBoard(myBoard);
		System.out.println();
		printTheBoard(computerBoard);
		myGame.startTheGame();
		
	}
	
	public void checkIfAllShipsArePlaced() {
		boolean allPlaced = true;
		
		for(Ship theShip : myShips) {
			if(theShip.isPlacedOnBoard() == false) {
				allPlaced = false;
				break;
			}
		}
		
		if(allPlaced)
			startGame();
	}
	
	public boolean activePlayerDestroyedTheShips(String player) {
		boolean areDestroyed = true;
		
		ArrayList<Ship> ships;
		if(player.equals("me")){
			ships = myShips;
		}
		else
			ships = computerShips;
		
		for(Ship theShip : ships) {
			if(theShip.isDestroyed() == false) {
				areDestroyed = false;
				return areDestroyed;
			}
		}
		return areDestroyed;
	}
	
	public void saveTheShip(Ship theShip) { 
		theShip.saveTheShip(myBoard);
		theShip.setPlacedOnBoard(true);
		checkIfAllShipsArePlaced();
	}
}
