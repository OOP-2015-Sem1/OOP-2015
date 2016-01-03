package battleship.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;

import battleship.logic.Player;

public class PlayerDetailPanel extends JPanel {

	private Player player;

	public PlayerDetailPanel(String title, boolean mainPlayer, PlayerDetailPanel opponent) {

		player = new Player(mainPlayer, opponent);

		Dimension size = getPreferredSize();
		size.setSize(500, 400);
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder(title));
		setBackground( new Color(0,0,255));
		setLayout(new GridBagLayout());
		GridBagConstraints gridC = new GridBagConstraints();

		populateBoard(gridC);
	}

	private void populateBoard(GridBagConstraints gridC) {
		gridC.weightx = 0.16;
		gridC.weighty = 0.16;
		gridC.gridx = 0;
		gridC.gridy = 0;

		int buttonIndex = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				if (i == 0) {
					if (j == 0) {
						gridC.gridx = 0;
						gridC.gridy = 0;
						add(new JLabel(" "), gridC);
					} else {
						gridC.gridx = 1;
						gridC.gridy = (j + 1);
						add(new JLabel((String.valueOf(j))), gridC);
					}
				} else {
					if (j == 0) {
						gridC.gridx = 0;
						gridC.gridy = 0;
						add(new JLabel(" "), gridC);
					} else {
						gridC.gridx = (j + 1);
						gridC.gridy = 1;
						add(new JLabel((String.valueOf(j))), gridC);
						for (int k = 0; k < 5; k++) {
							gridC.gridx = (j + 1);
							gridC.gridy = (k + 2);
							add(player.getBoard().getBoardB().get(buttonIndex).getButton(), gridC);

							buttonIndex++;
						}
					}
				}
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
