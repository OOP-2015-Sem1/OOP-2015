package gui;

import static entities.Constants.BOARD_SIZE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import entities.Constants;
import funct.KeyController;

public class GameFrame {

	public JFrame jframe = new JFrame();;
	private JPanel snakePanel;
	private JPanel buttonPanel;

	private JButton buttonEasy;
	private JButton buttonMed;
	private JButton buttonHard;
	public JPanel beautyPanel1;
	public JPanel beautyPanel2;
	public JPanel beautyPanel3;

	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem newGame;

	private Screen screen;

	public static JButton showScoreButton;

	EventEasyBut easyDificulty = new EventEasyBut();
	EventMedBut medDificulty = new EventMedBut();
	EventHardBut hardDificulty = new EventHardBut();
	EventsMenu evExitGame = new EventsMenu();
	EvMenu evNewGame = new EvMenu();

	public GameFrame() {

		Constants.canWeGoThroughWalls = GoThroughWalls.queryForGoingThroughWalls();
		BOARD_SIZE = BoardSizeDialogBox.queryForBoardSize();

		menuBar = new JMenuBar();
		jframe.setJMenuBar(menuBar);

		file = new JMenu("File");
		menuBar.add(file);

		newGame = new JMenuItem("New GAME");
		file.add(newGame);

		exit = new JMenuItem("Exit");
		file.add(exit);

		exit.addActionListener(evExitGame);
		newGame.addActionListener(evNewGame);

		jframe.setFocusable(true);
		jframe.addKeyListener(new KeyController());

		initializeFrameItems();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setTitle("SNAKE");
		jframe.setResizable(false);

	}

	public void initializeFrameItems() {
		jframe.setLayout(new BorderLayout());

		snakePanel = new JPanel();
		buttonPanel = new JPanel();
		snakePanel.setLayout(new GridLayout(1, 1));

		screen = new Screen();
		snakePanel.setFocusable(true);
		screen.setPreferredSize(new Dimension(Constants.DIMENSION, Constants.DIMENSION));
		snakePanel.add(screen);

		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new GridLayout(1, 4));
		buttonPanel.setPreferredSize(new Dimension(Constants.DIMENSION, 50));

		buttonEasy = new JButton("EASY");
		buttonMed = new JButton("MEDIUM");
		buttonHard = new JButton("HARD");
		buttonEasy.setBackground(Color.GREEN);
		buttonMed.setBackground(Color.GREEN);
		buttonHard.setBackground(Color.GREEN);

		buttonEasy.addActionListener(easyDificulty);
		buttonMed.addActionListener(medDificulty);
		buttonHard.addActionListener(hardDificulty);

		showScoreButton = new JButton("Score: 0");
		showScoreButton.setEnabled(false);
		showScoreButton.setBackground(Color.WHITE);

		buttonPanel.add(showScoreButton);
		buttonPanel.add(buttonEasy);
		buttonPanel.add(buttonMed);
		buttonPanel.add(buttonHard);

		beautyPanel1 = new JPanel();
		beautyPanel2 = new JPanel();
		beautyPanel3 = new JPanel();
		beautyPanel1.setPreferredSize(new Dimension(10, Constants.DIMENSION));
		beautyPanel2.setPreferredSize(new Dimension(10, Constants.DIMENSION));
		beautyPanel3.setPreferredSize(new Dimension(Constants.DIMENSION, 10));
		beautyPanel1.setBackground(Color.GREEN);
		beautyPanel2.setBackground(Color.GREEN);
		beautyPanel3.setBackground(Color.GREEN);

		jframe.add(snakePanel, BorderLayout.CENTER);
		jframe.add(beautyPanel1, BorderLayout.EAST);
		jframe.add(beautyPanel2, BorderLayout.WEST);
		jframe.add(beautyPanel3, BorderLayout.SOUTH);
		jframe.add(buttonPanel, BorderLayout.NORTH);
		jframe.pack();

		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);

	}

	public class EvMenu implements ActionListener {
		public void actionPerformed(ActionEvent enG) {
			jframe.setVisible(false);
			jframe.dispose();
			new GameFrame();
		}
	}

	public class EventsMenu implements ActionListener {
		public void actionPerformed(ActionEvent ex) {

			System.exit(0);
		}
	}

	public class EventEasyBut implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty1) {

			Screen.speed = 10000000;
			jframe.requestFocus();
		}
	}

	public class EventMedBut implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty3) {

			Screen.speed = 6000000;
			jframe.requestFocus();
		}
	}

	public class EventHardBut implements ActionListener {
		public void actionPerformed(ActionEvent hardDifficulty) {

			Screen.speed = 3000000;
			jframe.requestFocus();
		}
	}

}