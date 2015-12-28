package battleship.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import battleship.game.io.HandleShipHighlight;
import battleship.models.Ship;

public class ShipsPanel extends JPanel implements MouseListener {

	private final int CELL_EDGE = 50;
	private JPanel ship2, ship3a, ship3b, ship4, ship5;
	ArrayList<Ship> ships;
	private HandleShipHighlight shipHighlight;

	public ShipsPanel(ArrayList<Ship> ships, HandleShipHighlight shipHighlight) {
		this.ships = ships;
		this.shipHighlight = shipHighlight;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		configurePanel();
	}

	public JPanel buildShipPanel(int width, int height) {
		JPanel ship = new JPanel();
		ship.setMaximumSize(new Dimension(width, height));
		ship.setSize(new Dimension(width, height));
		ship.setBackground(Color.RED);
		return ship;
	}

	public void configurePanel() {
		ship2 = buildShipPanel(CELL_EDGE * 2, CELL_EDGE);
		ship3a = buildShipPanel(CELL_EDGE * 3, CELL_EDGE);
		ship3b = buildShipPanel(CELL_EDGE * 3, CELL_EDGE);
		ship4 = buildShipPanel(CELL_EDGE * 4, CELL_EDGE);
		ship5 = buildShipPanel(CELL_EDGE * 5, CELL_EDGE);
		ship2.addMouseListener(this);
		ship3a.addMouseListener(this);
		ship3b.addMouseListener(this);
		ship4.addMouseListener(this);
		ship5.addMouseListener(this);

		add(ship2);
		add(Box.createRigidArea(new Dimension(150, 10)));
		add(ship3a);
		add(Box.createRigidArea(new Dimension(150, 10)));
		add(ship3b);
		add(Box.createRigidArea(new Dimension(150, 10)));
		add(ship4);
		add(Box.createRigidArea(new Dimension(150, 10)));
		add(ship5);
	}
	
	public boolean shipNotOnBoard(Ship theShip) {
		if(theShip.isPlacedOnBoard() == false)
			return true;
		else
			return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ship2 && shipNotOnBoard(ships.get(0)) && (shipHighlight.isHighlightActive() == false)) {
			shipHighlight.setHiglightActive(true);
			shipHighlight.setHighlightedShip(ships.get(0));
			ship2.setVisible(false);
			
		} else if (e.getSource() == ship3a && shipNotOnBoard(ships.get(1)) && (shipHighlight.isHighlightActive() == false)) {
			shipHighlight.setHiglightActive(true);
			shipHighlight.setHighlightedShip(ships.get(1));
			ship3a.setVisible(false);
		
		} else if (e.getSource() == ship3b && shipNotOnBoard(ships.get(2)) && (shipHighlight.isHighlightActive() == false)) {
			shipHighlight.setHiglightActive(true);
			shipHighlight.setHighlightedShip(ships.get(2));
			ship3b.setVisible(false);
			
		} else if (e.getSource() == ship4 && shipNotOnBoard(ships.get(3)) && (shipHighlight.isHighlightActive() == false)) {
			shipHighlight.setHiglightActive(true);
			shipHighlight.setHighlightedShip(ships.get(3));
			ship4.setVisible(false);
			
		} else if (e.getSource() == ship5 && shipNotOnBoard(ships.get(4)) && (shipHighlight.isHighlightActive() == false)) {
			shipHighlight.setHiglightActive(true);
			shipHighlight.setHighlightedShip(ships.get(4));
			ship5.setVisible(false);
		}
		shipHighlight.resetShipOrientation();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("Mouse entered");

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//System.out.println("Mouse exited");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseClicked(e);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
