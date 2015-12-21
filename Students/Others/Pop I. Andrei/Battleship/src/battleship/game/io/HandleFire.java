package battleship.game.io;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import battleship.game.BoardConfiguration;
import battleship.game.Game;
import battleship.models.Ship;

public class HandleFire extends Fire implements ActionListener {

	private JButton[][] computerBoardCells;
	private char[][] computerBoard;
	private ArrayList<Ship> computerShips;
	private final BoardConfiguration boardConfiguration;
	private final BoardFrame boardFrame;
	private final Game theGame;
	
	public HandleFire(BoardConfiguration playBoard, BoardFrame boardFrame, Game theGame) {
		this.boardConfiguration = playBoard;
		this.boardFrame = boardFrame;
		this.theGame = theGame;
		computerBoard = playBoard.getComputerBoard();
		computerBoardCells = boardFrame.getComputerBoardCells();
		computerShips = playBoard.getComputerShips();
	}
	
	
	private Point getTheCoordOfTheHitCell(Object theSource) {

		for (int i = 0; i < computerBoardCells.length; i++)
			for (int j = 0; j < computerBoardCells[i].length; j++) {
				if (computerBoardCells[i][j] == theSource)
					return new Point(i, j);
			}
		return null;
	}
	
	private boolean hitSomeShip(Point hitLocation) {
		
		if(computerBoard[hitLocation.x][hitLocation.y] == '~')
			return false;
		else {
			identifyTheHitShip(hitLocation, computerShips);
			return true;
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Point hitPoint = getTheCoordOfTheHitCell(event.getSource());
		boardConfiguration.disableComputerBoard();

		if (hitSomeShip(hitPoint)) {
			computerBoardCells[hitPoint.x][hitPoint.y].setBackground(Color.RED); // show the hit ships with red
		}
		else {
			computerBoardCells[hitPoint.x][hitPoint.y].setBackground(Color.GRAY); // show the hit ships with red
		}
		computerBoardCells[hitPoint.x][hitPoint.y].setEnabled(false);
		theGame.startTimer();
		
	}

}
