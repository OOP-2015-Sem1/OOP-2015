package battleship.game;


import javax.swing.*;
import java.awt.*;
import battleship.game.io.BoardFrame;


public class RandomGenerator {
	
	private final int MAX_DIM;
	private JButton[][] playerBoardCells;
	private char[][] computerMatrix;
	private final int X = 0;
	private final int LEFT = 0;
	private boolean hitNear;
	private int hitAxis;
	private int hitDirection;
	private Point lastHitPoint;
	
	public RandomGenerator(int MAX_DIM, JButton[][] playerBoardCells) {
		this.MAX_DIM = MAX_DIM;
		this.playerBoardCells = playerBoardCells;
		computerMatrix = new char[MAX_DIM][MAX_DIM];
		fillInitialMatrix();
	}
	
	public void resetToDefault() {
		hitNear = false;
		hitAxis = X;
		hitDirection = LEFT;
	}
	
	public void setLastHitPoint(Point thePoint) {
		lastHitPoint = thePoint;
	}
	
	public void setHitNear(boolean hit) {
		hitNear = hit;
		hitAxis = X;
		hitDirection = LEFT;
	}
	
	public boolean getHitNear() {
		return hitNear;
	}
	
	public int getHitDirection() {
		return hitDirection;
	}
	
	public int getHitAxis() {
		return hitAxis;
	}
	
	public void changeHitAxis() {
		hitAxis = 1-hitAxis;
	}
	
	public void changeHitDirection() {
		hitDirection = 1 - hitDirection;
	}
	
	private Point generateRandomLocation() {
		int x = (int) (Math.random() * MAX_DIM);
		int y = (int) (Math.random() * MAX_DIM);
		return new Point(x, y);
	}
	
	private Point generateLocation() {
		
		if(hitNear == false) {
			return generateRandomLocation();
		}
		else {
			if(hitAxis == X) {
				if(hitDirection == LEFT) {
					if(lastHitPoint.y - 1 >= 0)
						return new Point(lastHitPoint.x, lastHitPoint.y - 1);
					else {
						changeHitDirection();
						return new Point(lastHitPoint.x, lastHitPoint.y + 1);
					}
				}
				else {
					if(lastHitPoint.x + 1 < MAX_DIM)
						return new Point(lastHitPoint.x, lastHitPoint.y + 1);
					else if(lastHitPoint.x - 1 >= 0){
						changeHitAxis();
						changeHitDirection();
						return new Point(lastHitPoint.x - 1, lastHitPoint.y);
					}
				}
			}
			else {
				if(hitDirection == LEFT) {
					if(lastHitPoint.x - 1 >= 0)
						return new Point(lastHitPoint.x - 1, lastHitPoint.y);
					else {
						changeHitDirection();
						return new Point(lastHitPoint.x + 1, lastHitPoint.y);
					}
				}
				else {
					if(lastHitPoint.x + 1 < MAX_DIM)
						return new Point(lastHitPoint.x + 1, lastHitPoint.y);
					else
						return generateRandomLocation();
				}
			}
		}
		
		return generateRandomLocation();
	}
	
	private boolean notValidHitLocation(Point hitLocation) {
		if(playerBoardCells[hitLocation.x][hitLocation.y].getBackground() == Color.RED || playerBoardCells[hitLocation.x][hitLocation.y].getBackground() == Color.GRAY) {
			return true;
		}
		return false;
	}
	
	public Point generateHit() {
		Point hitLocation = generateLocation();
		while(notValidHitLocation(hitLocation)) { 
			hitLocation = generateLocation();
			lastHitPoint = hitLocation;
		}
		return hitLocation;
	}
	
	private String generateOrientation() {
		int nr = (int) (Math.random() * 2);
		if(nr == 0)
			return "horizontal";
		else
			return "vertical";
	}
	
	private boolean validLocation(Point randomPoint, int size, String orientation) {
		
		int i, j;
		
		
		if(orientation.equals("horizontal")) {
			if(randomPoint.y + size >= MAX_DIM)
				return false;
			i = randomPoint.x;
			for(j = randomPoint.y; j < randomPoint.y + size; j++) {
				if(computerMatrix[i][j] != '~') 
					return false;
			}
		}
		else {
			if(randomPoint.x + size >= MAX_DIM)
				return false;
			j = randomPoint.y;
			for(i = randomPoint.x; i < randomPoint.x + size; i++) {
				if(computerMatrix[i][j] != '~')
					return false;
			}
		}
		
		return true;
	}
	
	private void fillInitialMatrix() {
		
		for(int i = 0; i < MAX_DIM; i++) 
			for(int j = 0; j < MAX_DIM; j++)
				computerMatrix[i][j] = '~';
	}
	
	private Point generateValidOrigin(int size, String orientation) {
		
		Point randomPoint = generateLocation();
		
		while(!validLocation(randomPoint, size, orientation)){
			randomPoint = generateLocation();
		}
		
		return randomPoint;
		
	}
	
	private void placeShipOf(int size, String orientation) {
		
		Point shipOrigin = generateValidOrigin(size, orientation);
		int i, j;
		
		if(orientation.equals("horizontal")) {
			i = shipOrigin.x;
			for(j = shipOrigin.y; j < shipOrigin.y + size; j++) {
				computerMatrix[i][j] = Integer.toString(size).charAt(0);
			}
		}
		else {
			j = shipOrigin.y;
			for(i = shipOrigin.x; i < shipOrigin.x + size; i++) {
				computerMatrix[i][j] = Integer.toString(size).charAt(0);
			}
		}
	}
	
	public char[][] generateRandomComputerConfiguration() {
		
		for(int shipSize = 2; shipSize < 6; shipSize+=1) {
			String orientation = generateOrientation();
			placeShipOf(shipSize, orientation);
			if(shipSize == 3) {
				orientation = generateOrientation();
				placeShipOf(shipSize, orientation);
			}
		}
		return computerMatrix;
	}
	
}
