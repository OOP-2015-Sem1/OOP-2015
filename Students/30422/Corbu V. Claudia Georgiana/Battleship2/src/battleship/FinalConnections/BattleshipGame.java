package battleship.FinalConnections;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;

import battleship.background.code.ShipMatrix;
import battleship.userInterface.BoardPanel;
import battleship.userInterface.SettingsDialogs;

public class BattleshipGame {
	SettingShipsControler shipsControl;
	ShipMatrix oponentShipMatrix, myShipMatrix, matrixOfHitsOnly;
	BoardPanel oponentPanel, myPanel;
	boolean ButtonsAreEnabled = true;
	JButton[][] oponentButtons;
	Point currentPoint, previousPoint, point, rPoint, auxPoint;
	String currentDirection;
	ArrayList<Point> positions = new ArrayList<Point>();

	public static void main(String[] args) {
		BattleshipGame game = new BattleshipGame();
		game.run();

	}

	public void run() {
		point=new Point(0,0);
		shipsControl = new SettingShipsControler();
		oponentShipMatrix = new ShipMatrix();
		oponentPanel = shipsControl.getOponentPanel();
		oponentPanel.EnableButtons(false);
		oponentButtons = shipsControl.getOponentPanel().getButtons();
		myPanel = shipsControl.getMyPanel();
		shipsControl.addListenerToPLAYButton(new ListenerForPlayButton());
		myShipMatrix = shipsControl.getMyShipMatrix();
		
		matrixOfHitsOnly = new ShipMatrix();

	}

	public class ListenerForPlayButton implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (shipsControl.getNrOfShip() == 6) {
				oponentShipMatrix.initializeMatrixWithShips();
				myPanel.EnableButtons(false);
				oponentPanel.EnableButtons(true);
				oponentPanel.addActionListenerToButtons(new ListenerForOponentsButtons());
			} else {
				SettingsDialogs.notifyShipsLeftToPlace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class ListenerForOponentsButtons implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			if (ButtonsAreEnabled) {
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (oponentButtons[i][j] == e.getSource()) {
							// oponentButtons[i][j].setEnabled(false); TRY LATER

							if (oponentShipMatrix.checkIfTrue(i, j)) {
								oponentButtons[i][j].setBackground(Color.RED);
								oponentShipMatrix.updateShipMatrixWithHits(i, j);

							} else {
								oponentButtons[i][j].setBackground(new Color(0, 100, 150));
							}
						}
					}
				}
				if (oponentShipMatrix.checkForWin()) {
					SettingsDialogs.notifyWin();

				}
				ButtonsAreEnabled = false;

				oponentCanHitMe();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void oponentCanHitMe() {
		while (!ButtonsAreEnabled ) {
			if (positions.size() == 0) {
				rPoint = generateValidNewPosition();
				matrixOfHitsOnly.updateHitsMatrixWithHits(rPoint.x, rPoint.y);
				if (myShipMatrix.checkIfTrue(rPoint.x, rPoint.y)) {
					actionForHitShip(rPoint);
					positions.add(rPoint);
					System.out.println("MyFirst good hit"+ rPoint.x+" "+rPoint.y+"  size "+positions.size());					
				} else {
					actionForHitWater(rPoint);
					System.out.println("missed that");
				}
			}
			else if (positions.size() == 1) {
				// hit up
				if (positions.get(0).x != 0
						&& !matrixOfHitsOnly.checkIfTrue(positions.get(0).x - 1, positions.get(0).y)) {
					if (myShipMatrix.checkIfTrue(positions.get(0).x - 1, positions.get(0).y)) {
						actionForHitShip(new Point(positions.get(0).x - 1, positions.get(0).y));
						positions.add(1,new Point(positions.get(0).x - 1, positions.get(0).y));
						System.out.println("hit 2 good");
						
					} else {
						actionForHitWater(new Point(positions.get(0).x - 1, positions.get(0).y));
						System.out.println("hit 2 baad");
					}
				}
				// hit down
				else if (positions.get(0).x != 9
						&& !matrixOfHitsOnly.checkIfTrue(positions.get(0).x + 1, positions.get(0).y)) {
					if (myShipMatrix.checkIfTrue(positions.get(0).x + 1, positions.get(0).y)) {
						positions.add(1,new Point(positions.get(0).x + 1, positions.get(0).y));
						actionForHitShip(new Point(positions.get(0).x + 1, positions.get(0).y));
						
					} else {
						actionForHitWater(new Point(positions.get(0).x + 1, positions.get(0).y));
					}
				}
				// hit right
				else if (positions.get(0).y != 9
						&& !matrixOfHitsOnly.checkIfTrue(positions.get(0).x, positions.get(0).y + 1)) {
					if (myShipMatrix.checkIfTrue(positions.get(0).x, positions.get(0).y + 1)) {
						positions.add(1,new Point(positions.get(0).x, positions.get(0).y + 1));
						actionForHitShip(new Point(positions.get(0).x, positions.get(0).y + 1));
						
					} else {
						actionForHitWater(new Point(positions.get(0).x, positions.get(0).y + 1));
					}
				}
				// hit left
				else if (positions.get(0).y != 0
						&& !matrixOfHitsOnly.checkIfTrue(positions.get(0).x, positions.get(0).y - 1)) {
					if (myShipMatrix.checkIfTrue(positions.get(0).x, positions.get(0).y - 1)) {
						positions.add(1,new Point(positions.get(0).x, positions.get(0).y - 1));
						actionForHitShip(new Point(positions.get(0).x, positions.get(0).y - 1));			
					} else {
						actionForHitWater(new Point(positions.get(0).x, positions.get(0).y - 1));
					}
				} else {
					positions.remove(0);
				}
				System.out.println("size is+"+positions.size());
			}
			else	
				if (positions.size() == 2) {
				// if the 2 hits are on the same line
				if (positions.get(0).x == positions.get(1).x) {
					if (positions.get(0).y > positions.get(1).y) {						
						changePositionOfPointsInPositions();
						System.out.println("after changing pos:"+positions.get(0).y+" "+positions.get(1).y);
					}
					// hit right
					if (positions.get(1).y == 9) {
						positions.add(2,point);
					} else {
						if (matrixOfHitsOnly.checkIfTrue(positions.get(1).x, positions.get(1).y + 1)) {
							positions.add(2,point);
						} else {
							if (myShipMatrix.checkIfTrue(positions.get(1).x, positions.get(1).y + 1)) {
								positions.set(1, new Point(positions.get(1).x, positions.get(1).y + 1));
								actionForHitShip(new Point(positions.get(1).x, positions.get(1).y + 1));
							} else {								
								actionForHitWater(new Point(positions.get(1).x, positions.get(1).y + 1));
								positions.add(2,new Point(positions.get(1).x, positions.get(1).y + 1));

							}
						}
					}
				}
				// if the 2 hits are on the same column - hit up
				else if (positions.get(0).y == positions.get(1).y) {
					if (positions.get(0).x < positions.get(1).x) {
						changePositionOfPointsInPositions();
					}
					if (positions.get(1).x == 0) {
						positions.add(2,point);
					} else {
						if (matrixOfHitsOnly.checkIfTrue(positions.get(1).x-1, positions.get(1).y)) {
							positions.add(2,point);
						} else {
							if (myShipMatrix.checkIfTrue(positions.get(1).x-1, positions.get(1).y )) {
								actionForHitShip(new Point(positions.get(1).x-1, positions.get(1).y));
								positions.set(1, new Point(positions.get(1).x-1, positions.get(1).y ));
								System.out.println("just hit that 3 times +size "+positions.size()+"coordonate"+(positions.get(1).x-1)+""+ positions.get(1).y );

							} else {
								positions.add(new Point(positions.get(1).x-1, positions.get(1).y ));
								actionForHitWater(new Point(positions.get(1).x-1, positions.get(1).y ));
								System.out.println(" DIDNOT hit that 3 times +size "+positions.size());
							}
						}
					}

				}
			}
			else if(positions.size()==3){
				// if the 2 hits are on the same line
				if (positions.get(0).x == positions.get(1).x) {
					//hit left
					if (positions.get(0).y == 0) {
						positions.add(3,point);
					} else {
						if (matrixOfHitsOnly.checkIfTrue(positions.get(0).x, positions.get(0).y - 1)) {
							positions.add(3,point);
						} else {
							if (myShipMatrix.checkIfTrue(positions.get(0).x, positions.get(0).y -1)) {
								positions.set(0, new Point(positions.get(0).x, positions.get(0).y - 1));
								actionForHitShip(new Point(positions.get(0).x, positions.get(0).y - 1));

							} else {
								positions.add(3,new Point(positions.get(0).x, positions.get(0).y - 1));
								actionForHitWater(new Point(positions.get(0).x, positions.get(0).y - 1));

							}
						}
					}
				}
				// if the 2 hits are on the same column -hit down
				else if (positions.get(0).y == positions.get(1).y) {
					if (positions.get(0).x == 9) {
						System.out.println(positions.get(0).x);
						positions.add(3,point);
						ButtonsAreEnabled=true;
					} else {
						if (matrixOfHitsOnly.checkIfTrue(positions.get(0).x+1, positions.get(0).y)) {
							positions.add(3,point);
							ButtonsAreEnabled=true;
						} else {
							if (myShipMatrix.checkIfTrue(positions.get(0).x+1, positions.get(0).y )) {
								positions.set(0, new Point(positions.get(0).x+1, positions.get(0).y ));
								System.out.println(positions.get(0).x);
								actionForHitShip(new Point(positions.get(0).x+1, positions.get(0).y));

							} else {
								positions.add(3,new Point(positions.get(0).x+1, positions.get(0).y ));
								actionForHitWater(new Point(positions.get(0).x+1, positions.get(0).y ));

							}
						}
					}
				}
			}
			else if (positions.size()==4){
				int i=3;
				System.out.println(positions.size());
				while (positions.size()!=0)
				System.out.println(positions.size());
				positions.remove(i);
				System.out.println(positions.size());
				i--;
				}
		}
	}

	public Point generateValidNewPosition() {
		point = matrixOfHitsOnly.generateRandomStartingPoint();
		while (matrixOfHitsOnly.checkIfTrue(point.x, point.y)) {
			point = matrixOfHitsOnly.generateRandomStartingPoint();
		}
		return point;
	}

	public void changePositionOfPointsInPositions() {
		auxPoint = positions.get(0);
		positions.add(0, positions.get(1));
		positions.add(1, auxPoint);
	}

	public void actionForHitShip(Point rPoint) {
		myPanel.getSpecificButton(rPoint.x, rPoint.y).setBackground(Color.RED);
		myShipMatrix.updateShipMatrixWithHits(rPoint.x, rPoint.y);
		matrixOfHitsOnly.updateHitsMatrixWithHits(rPoint.x, rPoint.y);
		// positions.add(rPoint);
		// positions.add(1, rPoint);
		if (myShipMatrix.checkForWin()) {
			SettingsDialogs.notifyLoss();
		}
		ButtonsAreEnabled = true;
	}

	public void actionForHitWater(Point rPoint) {
		myPanel.getSpecificButton(rPoint.x, rPoint.y).setBackground(new Color(0, 100, 150));
		matrixOfHitsOnly.updateHitsMatrixWithHits(rPoint.x, rPoint.y);
		// --
		// positions.add(2, rPoint);
		ButtonsAreEnabled = true;
	}

}
