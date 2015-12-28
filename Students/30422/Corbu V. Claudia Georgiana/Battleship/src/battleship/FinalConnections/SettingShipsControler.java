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
	public boolean placeNewShipButtonIsActive=true;

	public SettingShipsControler() {
		battleshipFrame = new BattleshipFrame();
		myShipMatrix = new ShipMatrix();
		battleshipFrame.addActionListenerToPlaceNewShipButton(new PlaceNewShipListener());
		battleshipFrame.addActionListenerToMyButtons(new MouseListenerForMyButtons());
		mypanel = battleshipFrame.getMyPanel();
		length = 1;
		 horizontalPlacement = true;

	}
	public void setNewShipButtonToFalse(){
		placeNewShipButtonIsActive=false;
	}

	public void addListenerToDonePlaceingShipsButton(MouseListener mouseListener) {
		battleshipFrame.addActionListenerToDonePlaceingShipsButton(mouseListener);
	}

	private class PlaceNewShipListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			if (placeNewShipButtonIsActive) {
				length = SettingsDialogs.queryForShipSize();
				horizontalPlacement = SettingsDialogs.queryForHorizontalPlacementOfShip();
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {		}
		@Override
		public void mousePressed(MouseEvent e) {	}
		@Override
		public void mouseReleased(MouseEvent e) {		}
	}

	private class MouseListenerForMyButtons implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			JButton[][] b = mypanel.getButtons();
			Point point = new Point();
			ship = new Ship(length, horizontalPlacement);
			if (placeNewShipButtonIsActive) {
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (b[i][j] == e.getSource()) {
							point.setLocation(i, j);
						}
					}
				}
				if (myShipMatrix.isPlacementPermitted(ship, point)) {
					myShipMatrix.placeShipInMatrix(ship, point);
					mypanel.updateBoardWithShips(myShipMatrix.returnShipMatrix());
				} else
					SettingsDialogs.notifyIllegalPlacement();
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {		}
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
	public JButton getDonePlacingShipsButton(){
		return battleshipFrame.getDonePlacingShipsButton();
	}

	public void addListenerToOponentButtons(MouseListener mouseListener){
		battleshipFrame.getOponentPanel().addActionListenerToButtons(mouseListener );
	}
	public void addListenerToDonePlacingShips(MouseListener mouseListener){
		battleshipFrame.addActionListenerToDonePlaceingShipsButton(mouseListener);
	}
	/*
	 * private class DonePlacingShipsListener implements MouseListener{
	 * 
	 * @Override public void mouseClicked(MouseEvent e) {
	 * placeNewShipButtonIsActive=false;
	 * battleshipFrame.addActionListenerToOponentButtons(new
	 * HitMouseLsitener()); }
	 * 
	 * @Override public void mouseEntered(MouseEvent e) {}
	 * 
	 * @Override public void mouseExited(MouseEvent e) {}
	 * 
	 * @Override public void mousePressed(MouseEvent e) {}
	 * 
	 * @Override public void mouseReleased(MouseEvent e) {} } private class
	 * HitMouseLsitener implements MouseListener{
	 * 
	 * @Override public void mouseClicked(MouseEvent e) { JButton[][] b=
	 * oponentPanel.getButtons(); if (!placeNewShipButtonIsActive){ for ( int i
	 * = 0; i < 10; i++) { for (int j = 0; j < 10; j++) { if( b[i][j] ==
	 * e.getSource()){ b[i][j].setBackground(new Color(0, 100, 150));}}} } }
	 * 
	 * @Override public void mouseEntered(MouseEvent e) {}
	 * 
	 * @Override public void mouseExited(MouseEvent e) {}
	 * 
	 * @Override public void mousePressed(MouseEvent e) {}
	 * 
	 * @Override public void mouseReleased(MouseEvent e) {} }
	 */
}
