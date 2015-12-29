package battleship.game;


import javax.swing.*;
import java.awt.*;
import battleship.game.io.BoardFrame;


public class RandomGenerator {
	
	private final int MAX_ROW, MAX_COL;
	private BoardFrame boardFrame;
	private JButton[][] playerBoardCells;
	
	public RandomGenerator(int MAX_ROW, int MAX_COL, BoardFrame boardFrame) {
		this.MAX_ROW = MAX_ROW;
		this.MAX_COL = MAX_COL;
		this.boardFrame = boardFrame;
		playerBoardCells = boardFrame.getMyBoardCells();
	}
	
	private Point generateHitLocation() {
		
		int x = (int) (Math.random() * MAX_ROW);
		int y = (int) (Math.random() * MAX_ROW);
		return new Point(x, y);
	}
	
	private boolean notValidHitLocation(Point hitLocation) {
		if(playerBoardCells[hitLocation.x][hitLocation.y].getBackground()== Color.RED) {
			return true;
		}
		return false;
	}
	
	public Point generateHit() {
		Point hitLocation = generateHitLocation();
		while(notValidHitLocation(hitLocation))
			hitLocation = generateHitLocation();
		return hitLocation;
	}
	
}
