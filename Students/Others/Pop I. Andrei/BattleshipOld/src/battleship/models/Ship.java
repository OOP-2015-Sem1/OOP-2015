package battleship.models;

import java.util.*;

import javax.swing.JButton;

public class Ship extends ShipModel{
	
	public void saveTheShip(char[][] board) { // implemented the method here so I don't have to identify which ship I have to save
		int i, j;

		System.out.println(location.x + " " + location.y);
		
		if(orientation.equals("horizontal")) { 
			i = location.x;
			for(j = location.y; j < location.y + size; j++) {
				board[i][j] = Integer.toString(size).charAt(0);
			}
		}
		else {
			j = location.y;
			for(i = location.x; i < location.x + size; i++) {
				board[i][j] = Integer.toString(size).charAt(0);
			}
		}
		
	}

}
