package battleship.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import battleship.gui.PlayerDetailPanel;
import battleship.logic.Ship;

public class BoardPosition {

	private static int nrShips = 0;
	private int positionValue;
	private int positionOnBoard;
	private JButton button;
	private boolean mainPlayer;
	private PlayerDetailPanel opponent;

	public BoardPosition(int initialValue, boolean mPlayer, final Ship ships, final int posOnBoard,
			final boolean gameStart, PlayerDetailPanel opp) {
		mainPlayer = mPlayer;
		positionValue = initialValue;
		positionOnBoard = posOnBoard;
		opponent = opp;
		button = new JButton();
		setButtonTitle();
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mainPlayer && nrShips < 3) {
					positionValue = 2;
					setButtonTitle();
					ships.initMainPlayerType(nrShips, posOnBoard);
					nrShips++;
				}
				if (!mainPlayer && nrShips == 3) {
					for (int i = 0; i < ships.getShips().length; i++) {
						if (posOnBoard == ships.getShips()[i]) {
							positionValue = 1;
							ships.incrementKilledShips();
							break;
						} else {
							positionValue = 0;
						}
					}
					setButtonTitle();
					if (ships.getKilledShips() == 3) {
						JOptionPane.showMessageDialog(null, "You win");
						// JFrame jFrame = new MainFrame("You win");
					}
					if (opponent != null) {
						int posShooty = opponent.getPlayer().getShoot().shootIt();
						for (BoardPosition b : opponent.getPlayer().getBoard().getBoardB()) {
							if (posShooty == b.getPositionOnBoard()) {
								if (b.getPositionValue() == 2) {
									b.setPositionValue(1);
									opponent.getPlayer().getBoard().getShips().incrementKilledShips();
								} else {
									b.setPositionValue(0);
								}
								b.setButtonTitle();
								if (opponent.getPlayer().getBoard().getShips().getKilledShips() == 3) {
									JOptionPane.showMessageDialog(null, "Player 2 wins");
									// JFrame jFrame = new MainFrame("Player 2
									// wins");
								}
								break;
							}
						}
					}
				}
			}
		});
	}
	
	
	public void setButtonTitle() {
		if (positionValue == -1) {
			button.setIcon(new ImageIcon("D:\\Multimedia\\UTCN\\proiect oop\\water.gif"));
		} else if (positionValue == 0) {
			button.setIcon(new ImageIcon("D:\\Multimedia\\UTCN\\proiect oop\\splash.gif"));
			//button.setText("*");
		} else if (positionValue == 1) {
			button.setIcon(new ImageIcon("D:\\Multimedia\\UTCN\\proiect oop\\fire.gif"));
			//button.setText("X");
		} else if (positionValue == 2) {
			button.setIcon(new ImageIcon("D:\\Multimedia\\UTCN\\proiect oop\\battleshipv.gif"));
		}
	}

	public int getPositionValue() {
		return positionValue;
	}

	public void setPositionValue(int positionValue) {
		this.positionValue = positionValue;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public int getPositionOnBoard() {
		return positionOnBoard;
	}

	public void setPositionOnBoard(int positionOnBoard) {
		this.positionOnBoard = positionOnBoard;
	}

	public PlayerDetailPanel getOpponent() {
		return opponent;
	}

	public void setOpponent(PlayerDetailPanel opponent) {
		this.opponent = opponent;
	}

}
