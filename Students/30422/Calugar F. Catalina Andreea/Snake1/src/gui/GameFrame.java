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

	JFrame jframe = new JFrame();;
	private JPanel snakePanel;
	private JPanel buttonPanel;

	private JButton buttonEasy;
	private JButton buttonMed;
	private JButton buttonHard;
	public JPanel beautyPanel1;
	public JPanel beautyPanel2;
	public JPanel beautyPanel3;

	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem newG;
	
	private Screen screen;
	private ScoreButton scoreBut = new ScoreButton();

	
	EventEasyBut easyDificulty = new EventEasyBut();
	EventMedBut medDificulty = new EventMedBut();
	EventHardBut hardDificulty = new EventHardBut();
	EventsMenu ex = new EventsMenu();
	EvMenu enG = new EvMenu();

	public GameFrame() {

		Constants.canWeGoThroughWalls = GoThroughWalls.queryForGoingThroughWalls();
		BOARD_SIZE = BoardSizeDialogBox.queryForBoardSize();

		menubar = new JMenuBar();
		jframe.setJMenuBar(menubar);

		file = new JMenu("File");
		menubar.add(file);

		newG = new JMenuItem("New GAME");
		file.add(newG);

		exit = new JMenuItem("Exit");
		file.add(exit);

		exit.addActionListener(ex);
		newG.addActionListener(enG);

		jframe.setFocusable(true);
		jframe.addKeyListener(new KeyController());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setTitle("SNAKE");
		jframe.setResizable(false);

		init();
	}

	public void init() {
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

		buttonPanel.add(scoreBut);
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

		jframe.add(beautyPanel1, BorderLayout.EAST);
		jframe.add(beautyPanel2, BorderLayout.WEST);
		jframe.add(beautyPanel3, BorderLayout.SOUTH);
		jframe.add(snakePanel, BorderLayout.CENTER);
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

			Screen.speed = 10000;
			jframe.requestFocus();
		}
	}

	public class EventMedBut implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty3) {

			Screen.speed = 600000;
			jframe.requestFocus();
		}
	}

	public class EventHardBut implements ActionListener {
		public void actionPerformed(ActionEvent hardDifficulty) {

			Screen.speed = 300000;
			jframe.requestFocus();
		}
	}

}