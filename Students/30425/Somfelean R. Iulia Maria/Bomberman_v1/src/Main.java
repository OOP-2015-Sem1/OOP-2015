import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ControlPlayer;
import controller.Controller;
import core.GameManagerImpl;
import graphics.GameDrawer;
import model.Constants;
import model.OutOfMapException;
import model.Wall;

public class Main {

	public static final String START = "START";
	public static final String EXIT = "EXIT";
	static int[][] position = { { 1, 1 }, { 7, 7 }, { 12, 10 }, { 5, 4 } };
	static int[] key = { 10, 37, 38, 39, 40 }; // the ASCII codes of the
												// keyboard
	static int nrOfPlayers = 1;
	static JFrame menuFrame;
	static JFrame playersFrame;

	public static void main(String[] args) {

		menuFrame = new JFrame("Dyna Blaster v1");
		menuFrame.setSize(new Dimension(400, 400));
		menuFrame.setLocationRelativeTo(null);
		JLabel startLabel = new JLabel("Bomberman v.1.0!");
		startLabel.getPreferredSize();
		startLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startLabel.setFont(new Font("Serif", Font.BOLD, 40));

		menuFrame.add(startLabel, BorderLayout.CENTER);

		class MyActionListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == START) {
					menuFrame.setVisible(false);

					JFrame frame = new JFrame("Bomberman v.1.0 - Playing");
					GameManagerImpl gmi = null;
					try {
						gmi = new GameManagerImpl(Constants.matrixFileName);
					} catch (OutOfMapException e1) {

					}
					GameDrawer map = new GameDrawer("Map4", gmi);
					// a matrix with the wall elements, just for checking if the
					// walls are generated right
					Wall[][] testboard = gmi.getBoard();
					for (int i = 0; i < testboard.length; i++) {
						for (int j = 0; j < testboard[i].length; j++) {
							System.out.println(i + " " + j + ": " + testboard[i][j].getxPosition() + " "
									+ testboard[i][j].getyPosition() + " " + testboard[i][j].getRowPosition() + " "
									+ testboard[i][j].getColumnPosition());
						}
					}

					Controller control = new Controller();
					control.setFocusable(true);
					control.requestFocusInWindow();
					ControlPlayer player = new ControlPlayer(map, gmi, key);
					control.addControlPlayer(player);
					player.start();
					map.add(control);

					frame.add(map);
					frame.setSize(660, 620);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				}

				if (e.getActionCommand() == EXIT) {
					System.exit(0);
				}
			}
		}

		ActionListener menuList = new MyActionListener();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 0));
		buttonPanel.setPreferredSize(new Dimension(100, 100));
		JButton startB = new JButton(START);
		JButton exitB = new JButton(EXIT);
		startB.addActionListener(menuList);
		exitB.addActionListener(menuList);
		buttonPanel.add(startB);
		buttonPanel.add(exitB);

		menuFrame.add(buttonPanel, BorderLayout.SOUTH);
		menuFrame.setVisible(true);

	}

}
