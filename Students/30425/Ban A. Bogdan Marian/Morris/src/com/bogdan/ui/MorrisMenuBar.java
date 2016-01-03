package com.bogdan.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bogdan.model.PlayerType;

@SuppressWarnings("serial")
public class MorrisMenuBar extends JMenuBar {
	// private Board board;
	public MorrisMenuBar(Board board) {

		JMenu mainMenu = new JMenu("Main Menu");
		mainMenu.setMnemonic(KeyEvent.VK_M);
		this.add(mainMenu);

		JMenuItem newMenuItem = new JMenuItem("New Game", KeyEvent.VK_N);
		newMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JLabel jplayer1 = new JLabel("Player 1");
				JTextField player1 = new JTextField();
				player1.setText("Player1");
				JLabel jplayer2 = new JLabel("Player 2");
				JTextField player2 = new JTextField();
				player2.setText("Player2");
				Object[] ob = { jplayer1, player1, jplayer2, player2 };
				int result = JOptionPane.showConfirmDialog(null, ob, "Please write your names",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					board.getPlayerOne().setPlayerName(player1.getText());
					board.getPlayerTwo().setPlayerName(player2.getText());
				}
				board.setCurrentPlayer(PlayerType.Player1);
				board.updateStatusMessage("Game Started. First player's turn, " + board.getPlayerOne().getPlayerName());
			}
		});
		JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				board.dispose();
			}
		});
		mainMenu.add(newMenuItem);
		mainMenu.add(exit);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		this.add(helpMenu);

		JMenuItem aboutMe = new JMenuItem("About Me", KeyEvent.VK_N);
		aboutMe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MorrisMenuBar.this, "Ban Bogdan , second year , UTCN ", "Author",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem rules = new JMenuItem("Rules", KeyEvent.VK_R);
		rules.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MorrisMenuBar.this,
						"Game rules\n" + "\n"
								+ "The board consists of a grid with twenty-four intersections or points. Each player has nine pieces,\n"
								+ "or men, usually coloured black and white. Players try to form mills: three of their own men lined \n"
								+ "horizontally or vertically, allowing a player to remove an opponents man from the game. A player\n"
								+ "wins by reducing the opponent to two pieces (where he could no longer form mills and thus be\n"
								+ "unable to win), or by leaving him without a legal move.\n" + "\n"
								+ "The game proceeds in three phases:\n" + "1. placing men on vacant points\n"
								+ "2. moving men to adjacent points\n"
								+ "3. moving men to any vacant point when a player has been reduced to three men\n"
								+ "\n" + "Phase one: placing pieces\n"
								+ "The game begins with an empty board. The players determine who plays first, then take turns\n"
								+ "placing their men one per play on empty points. If a player is able to place three of his\n"
								+ "pieces in a straight line, vertically or horizontally, he has formed a mill and may remove\n"
								+ "one of his opponent's pieces from the board and the game. Any piece can be chosen for the\n"
								+ "removal, but a piece not in an opponent's mill must be selected, if possible.\n"
								+ "\n" + "Phase two: moving pieces\n"
								+ "Players continue to alternate moves, this time moving a man to an adjacent point.\n"
								+ "A piece may not jump another piece. Players continue to try to form mills and remove\n"
								+ "their opponent's pieces in the same manner as in phase one. A player may break a mill by\n"
								+ "moving one of his pieces out of an existing mill, then moving the piece back to form the\n"
								+ "same mill a second time (or any number of times), each time removing one of his opponent's\n"
								+ "men. When one player has been reduced to three men, phase three begins.\n" + "\n"
								+ "Phase three: flying\n"
								+ "When a player is reduced to three pieces, there is no longer a limitation of moving\n"
								+ "to only adjacent points: The players men may fly, hop, or jump from any point to any\n"
								+ "vacant point.",
						"Rules", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(aboutMe);
		helpMenu.add(rules);
	}
}
