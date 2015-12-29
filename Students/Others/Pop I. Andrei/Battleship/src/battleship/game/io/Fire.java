package battleship.game.io;

import java.awt.Point;
import java.util.ArrayList;

import battleship.models.Ship;

public class Fire {
	
	public void markIfDestroyed(Ship myShip) {
		if(myShip.getLife() == 0) {
			myShip.setDestroyed(true);
		}
	}
	
	public boolean shipIsOnLocation(Ship myShip,Point hitLocation) {
		Point shipLocation = myShip.getLocation();
		int i, j;
		
		if(myShip.getOrientation().equals("horizontal")) {
			i = shipLocation.x;
			for(j = shipLocation.y; j < shipLocation.y + myShip.getSize(); j++) {
				if(i == hitLocation.x && j == hitLocation.y)
					return true;
			}
		}
		else {
			j = shipLocation.y;
			for(i = shipLocation.x; i < shipLocation.x + myShip.getSize(); i++) {
				if(i == hitLocation.x && j == hitLocation.y) {
					return true;
				}
			}
		}
		return false;
	}	
	
	public Ship identifyTheHitShip(Point hitLocation, ArrayList<Ship> computerShips) {
		Ship myShip;
		int life;
		
		for(int i = 0; i < computerShips.size(); i++) {
			myShip = computerShips.get(i);
			if(shipIsOnLocation(myShip, hitLocation)) {
				life = myShip.getLife();
				myShip.setLife(life - 1);
				markIfDestroyed(myShip);
				return myShip;
			}
		}
		
		return null;
	}
	
}
