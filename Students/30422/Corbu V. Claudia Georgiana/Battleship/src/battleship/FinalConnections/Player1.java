package battleship.FinalConnections;
import static battleship.background.code.Constants.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import battleship.background.code.ShipMatrix;
import battleship.userInterface.BoardPanel;
import battleship.userInterface.SettingsDialogs;

public class Player1 {
	private static ServerSocket serverSocket;
	public static Socket socket;
	public SettingShipsControler shipsControl;
	public ObjectInputStream objInStream;
	public ObjectOutputStream objOutStream;
	public ShipMatrix oponentShipMatrix;
	public Point point;
	JButton[][] oponentButoons;
	public BoardPanel myPanel;
	public boolean DonePlacingShipsButtonWasPressed=true;

	public static void main(String[] args)  {
		Player1 player1 = new Player1();
		player1.play();
	}

	public void play()  {
		
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
				System.out.println("I notified Loss");
				System.exit(0);
			}else{
				myPanel.updateBoardWithHits(point.x, point.y);
				System.out.println("updated my Board with hits");
			}
			
		} catch (IOException e) {
			System.out.println("Something went wrong here1");
		} catch (ClassNotFoundException e) {
			System.out.println("Something went wrong here2");
		}			
	}

	public class ListenerToDoneSettingShips implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			shipsControl.setNewShipButtonToFalse();
			if (DonePlacingShipsButtonWasPressed)
			try {
				
				serverSocket = new ServerSocket(PORT);
				System.out.println("Player1 up and ready for connection");
				socket= serverSocket.accept();
				System.out.println("Connection succesful with player 2");
				objOutStream= new ObjectOutputStream(socket.getOutputStream());
				objOutStream.writeObject(shipsControl.getMyShipMatrix());
				System.out.println("MyShipMatrix was sent");
				objInStream = new ObjectInputStream(socket.getInputStream());
				oponentShipMatrix=(ShipMatrix) objInStream.readObject();
				System.out.println("OponentShipMatrix was received");
				shipsControl.addListenerToOponentButtons(new ListenerToOponentPanel());
				
				
				
			} catch (IOException | ClassNotFoundException e1) {
				System.out.println("Port number is wrong");
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
				} catch (IOException e1) {
				}
				System.exit(0);
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
