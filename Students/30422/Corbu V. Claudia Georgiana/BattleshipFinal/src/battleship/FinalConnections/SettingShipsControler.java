package battleship.FinalConnections;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import battleship.background.code.Ship;
import battleship.background.code.ShipMatrix;
import battleship.userInterface.BattleshipFrame;
import battleship.userInterface.BoardPanel;
import battleship.userInterface.SettingsDialogs;

public class SettingShipsControler {

	BattleshipFrame battleshipFrame;
	ShipMatrix myShipMatrix;
	Ship ship;
	boolean horizontalPlacement;
	int length ;
	BoardPanel mypanel;
	private int nrOfShips=1;
	private JButton[][] buttons;

	public SettingShipsControler() {
		battleshipFrame = new BattleshipFrame();
		myShipMatrix = new ShipMatrix();
		battleshipFrame.addActionListenerToMyButtons(new MouseListenerForMyButtons());
		mypanel = battleshipFrame.getMyPanel();
		horizontalPlacement= battleshipFrame.isHorizontallyButtonSelected();
		ship= new Ship(5,horizontalPlacement);
		 buttons = mypanel.getButtons();

	}
	

	public void addListenerToPLAYButton(MouseListener mouseListener) {
		battleshipFrame.addActionListenerToPLAYButton(mouseListener);
	}


	private class MouseListenerForMyButtons implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Point point = new Point();
			if (getNrOfShip() <= 5) {
				horizontalPlacement = battleshipFrame.isHorizontallyButtonSelected();
				//ship=new Ship(6 - nrOfShips, horizontalPlacement);
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (buttons[i][j] == e.getSource()) {
							point.setLocation(i, j);
						}
					}
				}
				if (myShipMatrix.isPlacementPermitted(ship, point)) {
					myShipMatrix.placeShipInMatrix(ship, point);
					mypanel.updateBoardWithShips(myShipMatrix.returnShipMatrix());
					nrOfShips = nrOfShips + 1;
					ship = new Ship(6 - nrOfShips, horizontalPlacement);
					
				} else
					SettingsDialogs.notifyIllegalPlacement();
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Point p = new Point();
			if (getNrOfShip() <= 5) {
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (buttons[i][j] == e.getSource()) {
							p.setLocation(i,j);						
						}
					}
				}

				if (battleshipFrame.isHorizontallyButtonSelected()) {
					ship=new Ship(6 - nrOfShips, battleshipFrame.isHorizontallyButtonSelected());
					if (myShipMatrix.isPlacementPermitted(ship, p)){
						
						for (int k = p.y; k <= ship.getLengthOfShip() + p.y - 1; k++) {
							buttons[p.x][k].setBackground(Color.GRAY);}
						}
				} else {
					ship=new Ship(6 - nrOfShips, battleshipFrame.isHorizontallyButtonSelected());
					if (myShipMatrix.isPlacementPermitted(ship, p))
						for (int k = p.x; k <= ship.getLengthOfShip() + p.x - 1; k++) {
							buttons[k][p.y].setBackground(Color.GRAY);
						}

				}
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			Point p = new Point();
			if (getNrOfShip() <= 5) {
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (buttons[i][j] == e.getSource()) {
							p.setLocation(i,j);							
						}
					}
				}
				if (myShipMatrix.isPlacementPermitted(ship, p)) {
					if (battleshipFrame.isHorizontallyButtonSelected()) {
						for (int k = p.y; k <= ship.getLengthOfShip()+p.y-1; k++) {
							buttons[p.x][k].setBackground(Color.LIGHT_GRAY);
						}
					} else {
						for (int k = p.x; k <= ship.getLengthOfShip()+p.x-1; k++) {
							buttons[k][p.y].setBackground(Color.LIGHT_GRAY);
						}
					}
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {	}
		@Override
		public void mouseReleased(MouseEvent e) {		}
	}

	public BoardPanel getOponentPanel() {
		return battleshipFrame.getOponentPanel();
	}

	public BoardPanel getMyPanel() {
		return battleshipFrame.getMyPanel();
	}

	public ShipMatrix getMyShipMatrix() {
		return myShipMatrix;
	}
	public JButton getPlayButton(){
		return battleshipFrame.getPlayButton();
	}

	public void addListenerToOponentButtons(MouseListener mouseListener){
		battleshipFrame.getOponentPanel().addActionListenerToButtons(mouseListener );
	}
	public int getNrOfShip(){
		return this.nrOfShips;
	}
}
