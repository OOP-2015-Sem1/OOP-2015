package battleship.FinalConnections;

import static battleship.background.code.Constants.PORT;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;

import battleship.background.code.Constants;
import battleship.background.code.ShipMatrix;
import battleship.userInterface.BoardPanel;
import battleship.userInterface.SettingsDialogs;

public class Player2 {
	public static Socket socket;
	public SettingShipsControler shipsControl;
	public ObjectInputStream objInStream;
	public ObjectOutputStream objOutStream;
	public ShipMatrix oponentShipMatrix;
	public Point point;
	JButton[][] oponentButoons;
	public BoardPanel myPanel;
	public boolean DonePlacingShipsButtonWasPressed=true;

	public static void main(String[] args) {
		Player2 player2 = new Player2();
		player2.play();
	}

	public void play() {
		shipsControl = new SettingShipsControler();
		shipsControl.addListenerToDonePlaceingShipsButton(new ListenerToDoneSettingShips());
		myPanel= shipsControl.getMyPanel();
		point =new Point();
		oponentButoons= shipsControl.getOponentPanel().getButtons();
		try {
			objInStream = new ObjectInputStream(socket.getInputStream());
			point=(Point) objInStream.readObject();
			if (point.x==11){
				SettingsDialogs.notifyLoss();
				System.exit(0);
				
			}else{
				myPanel.updateBoardWithHits(point.x, point.y);
			}
			
		} catch (IOException e) {
			System.out.println("Something went wrong here1");
		} catch (ClassNotFoundException e) {
			System.out.println("Something went wrong here2");}
	}

	public class ListenerToDoneSettingShips implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (DonePlacingShipsButtonWasPressed)
			try {
				shipsControl.setNewShipButtonToFalse();
				socket = new Socket(Constants.IP,PORT);
				System.out.println("Connexiune reusita");

				objInStream = new ObjectInputStream(socket.getInputStream());
				oponentShipMatrix=(ShipMatrix) objInStream.readObject();
				
				objOutStream= new ObjectOutputStream(socket.getOutputStream());
				objOutStream.writeObject(shipsControl.getMyShipMatrix());
				
				shipsControl.addListenerToOponentButtons(new ListenerToOponentPanel());
				shipsControl.getDonePlacingShipsButton().setEnabled(false);
				shipsControl.getDonePlacingShipsButton().setEnabled(false);
					
			} catch (UnknownHostException e1) {
				
			} catch (IOException e1) {
				
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			DonePlacingShipsButtonWasPressed=false;
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
	public class ListenerToOponentPanel implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (oponentButoons[i][j] == e.getSource()) {
						point.setLocation(i, j);
						if (oponentShipMatrix.checkIfTrue(i, j)) {
							oponentButoons[i][j].setBackground(Color.RED);
						} else {
							oponentButoons[i][j].setBackground(new Color(0, 100, 150));
						}oponentShipMatrix.updateShipMatrixWithHits(i, j);
					}
				}
			}
			if (oponentShipMatrix.checkForWin()) {
				
				try {
					objOutStream = new ObjectOutputStream(socket.getOutputStream());
					objOutStream.writeObject(new Point(11, 11));
					SettingsDialogs.notifyWin();
					System.exit(0);
				} catch (IOException e1) {
				}				
			} else {
				try {
				objOutStream = new ObjectOutputStream(socket.getOutputStream());
				objOutStream.writeObject(point);
			} catch (IOException e1) {
			}

			}
			}
			
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
}

}
