package battleship.game.io;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import battleship.game.BoardConfiguration;
import battleship.models.Ship;

public class HandleShipHighlight extends MouseAdapter{
	
	private String shipOrientation = "horizontal";
	private Ship ship;
	private JButton[][] boardCells;
	private boolean activeHighlight = false;
	private Point shipLocation;
	private BoardConfiguration theBoard;
	private final Color MARK_COLOR = Color.BLUE;
	
	//public HandleShipHighlight(JButton[][] boardCells) {
	public HandleShipHighlight(JButton[][] boardCells, BoardConfiguration theBoard){
		this.boardCells = boardCells;
		this.theBoard = theBoard;
	}
	
	public void resetShipOrientation() {
		shipOrientation = "horizontal";
	}
	
	public void  setHiglightActive(boolean activate){
		activeHighlight = activate;
	}
	
	public boolean isHighlightActive() {
		return activeHighlight;
	}
	
	public void setHighlightedShip(Ship myShip){
		this.ship = myShip;
	}
	
	public Point findLocationOfCursor(Object button) {
		Point location = new Point(-1, -1);

		for(int i = 0; i < boardCells.length; i++){
			for(int j = 0; j < boardCells[i].length; j++) {
				if(button.equals(boardCells[i][j])) {
					location.x = i;
					location.y = j;
					return location;
				}
			}
		}
		return location;
		
	}
	
	private boolean shipNotCoverOtherShip(String orientation) {
		
		if(orientation.equals("horizontal")) {
			for(int j = 0; j < ship.getSize(); j++) {
				if(boardCells[shipLocation.x][shipLocation.y + j].getBackground().equals(MARK_COLOR))
					return false;
			}
		}
		else {
			for(int i = 0; i < ship.getSize(); i++) 
				if(boardCells[shipLocation.x + i][shipLocation.y].getBackground().equals(MARK_COLOR))
					return false;
		}
			
		return true;
	}
	
	private boolean validLocation(Point location) {
		int shipSize = ship.getSize();
		
		if(shipOrientation.equals("horizontal")) {
			if(shipLocation.y + shipSize <= boardCells[0].length)
					return true;
		}
		else {
			if(shipLocation.x + shipSize <= boardCells.length) 
					return true;
		}
		return false;
	}
	
	private void highlightTheShip(MouseEvent e) {
		int shipSize = ship.getSize();
		shipLocation= findLocationOfCursor(e.getSource());
		
		if(shipOrientation.equals("horizontal")) {
			if(validLocation(shipLocation) && shipNotCoverOtherShip(shipOrientation)) {
				for(int j = 0; j < shipSize; j++) {
					boardCells[shipLocation.x][shipLocation.y + j].setBackground(Color.GRAY);
				}
			}
		}
		else { 
			if(validLocation(shipLocation) && shipNotCoverOtherShip(shipOrientation)) {
				for(int i = 0; i < shipSize; i++) 
					boardCells[shipLocation.x + i][shipLocation.y].setBackground(Color.GRAY);
			}
		}
	}
	
	public void unHighlightTheShip() {
		int shipSize = ship.getSize();
		
		if(shipOrientation.equals("horizontal")) {
			if(validLocation(shipLocation) && shipNotCoverOtherShip(shipOrientation)) {
				for(int j = 0; j < shipSize; j++) {
					boardCells[shipLocation.x][shipLocation.y + j].setBackground(Color.BLACK);
				}
			}
		}
		else
		{
			if(validLocation(shipLocation) && shipNotCoverOtherShip(shipOrientation)) {
				for(int i = 0; i < shipSize; i++) {
					boardCells[shipLocation.x + i][shipLocation.y].setBackground(Color.BLACK);
				}
			}
		}
	}
	
	public void markTheShipOnBoard() { // has similar functionality like highlight the ship
		int shipSize = ship.getSize();
		
		if(shipOrientation.equals("horizontal")) {
			if(validLocation(shipLocation)) {
				for(int j = 0; j < shipSize; j++) {
					boardCells[shipLocation.x][shipLocation.y + j].setBackground(MARK_COLOR);
				}
			}
		}
		else { 
			if(validLocation(shipLocation)) {
				for(int i = 0; i < shipSize; i++) 
					boardCells[shipLocation.x + i][shipLocation.y].setBackground(MARK_COLOR);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3 && validLocation(shipLocation)) {
			unHighlightTheShip();
			
			if(shipOrientation.equals("horizontal"))
				shipOrientation = "vertical";
			else
				shipOrientation = "horizontal";
			highlightTheShip(e);
		}
		else if(e.getButton() == MouseEvent.BUTTON1 && activeHighlight) {
			if(validLocation(shipLocation)) {
				ship.setLocation(shipLocation);
				ship.setOrientation(shipOrientation);
				markTheShipOnBoard();
				setHiglightActive(false);
				theBoard.saveTheShip(ship);
			} // aici pe else trebuie adaugat un Text Field care sa zica ca nu o pot plasa in afara boardului
		}
	}

	public void mouseEntered(MouseEvent e) {
		if(activeHighlight)
			highlightTheShip(e);
	}

	public void mouseExited(MouseEvent e) {
		if(activeHighlight)
			unHighlightTheShip();
	}
	
}
