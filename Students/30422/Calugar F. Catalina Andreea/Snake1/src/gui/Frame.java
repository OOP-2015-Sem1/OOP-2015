package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Frame {

	private JFrame jframe = new JFrame();;
	private JPanel snakePanel;
	private JPanel buttonPanel;
	private ControlsPanel ctrlPanel = new ControlsPanel();

	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem newG;
	private JButton buttonEasy;
	private JButton buttonMed;
	private JButton buttonHard;
	public JButton scoreLabel;

	public Frame() {

		Constants.canWeGoThroughWalls = GoThroughWalls.queryForGoingThroughWalls();

		menubar = new JMenuBar();
		jframe.setJMenuBar(menubar);

		file = new JMenu("File");
		menubar.add(file);

		newG = new JMenuItem("New GAME");
		file.add(newG);

		exit = new JMenuItem("Exit");
		file.add(exit);

		eventsMenu ex = new eventsMenu();
		exit.addActionListener(ex);

		evMenu enG = new evMenu();
		newG.addActionListener(enG);

		jframe.setFocusable(true);
		jframe.addKeyListener(new Controller());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setTitle("SNAKE");
		jframe.setResizable(false);

		init();

	}

	public void init() {
		jframe.setLayout(new FlowLayout(FlowLayout.TRAILING));

		snakePanel = new JPanel();
		buttonPanel = new JPanel();
		snakePanel.setLayout(new GridLayout(1, 1));

		Screen screen = new Screen();
		snakePanel.setFocusable(true);
		snakePanel.add(screen);

		buttonEasy = new JButton("EASY");
		buttonMed = new JButton("MEDIUM");
		buttonHard = new JButton("HARD");
		buttonEasy.setBackground(new Color(10, 100, 0));
		buttonMed.setBackground(new Color(10, 100, 0));
		buttonHard.setBackground(new Color(10, 100, 0));

		scoreLabel = new JButton("Score");
		scoreLabel.setEnabled(false);
		scoreLabel.setBackground(Color.WHITE);

		event eDificulty1 = new event();
		buttonEasy.addActionListener(eDificulty1);

		event2 eDificulty2 = new event2();
		buttonMed.addActionListener(eDificulty2);

		event3 eDificulty3 = new event3();
		buttonHard.addActionListener(eDificulty3);

		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new GridLayout(5, 1));
		buttonPanel.setSize(new Dimension(100, 100));

		buttonPanel.add(buttonEasy);
		buttonPanel.add(buttonMed);
		buttonPanel.add(buttonHard);
		buttonPanel.add(scoreLabel);

		ctrlPanel.setScore(Screen.score);
		buttonPanel.add(ctrlPanel);

		jframe.add(snakePanel);
		jframe.add(buttonPanel);
		jframe.pack();

		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);

	}

	public JButton[][] getControlButtons() {
		return this.ctrlPanel.getControlButtons();
	}

	public void addActionListenerToButtons(ActionListener actionListener) {
		this.addActionListenerToButtons(actionListener);
	}

	public class evMenu implements ActionListener {
		public void actionPerformed(ActionEvent enG) {

			jframe.dispose();

			new Frame();

		}
	}

	public class eventsMenu implements ActionListener {

		public void actionPerformed(ActionEvent ex) {
			System.exit(0);
		}

		public void addActionListenerToButtons(ActionListener actionListener) {
			ctrlPanel.addActionListenerToButtons(actionListener);
		}
	}

	public class event implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty1) {

			Screen.speed = 9000000;
		}
	}

	public class event2 implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty3) {
			Screen.speed = 6000000;
		}
	}

	public class event3 implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty3) {
			Screen.speed = 2000000;
		}
	}

}
