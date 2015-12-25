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

public class GameFrame {

	JFrame jframe = new JFrame();;
	private static JPanel snakePanel;
	private JPanel buttonPanel;
	private ControlsPanel ctrlPanel = new ControlsPanel();

	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem newG;
	private JButton buttonEasy;
	private JButton buttonMed;
	private JButton buttonHard;
	GameFrame g;

	public GameFrame() {

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
		jframe.addKeyListener(new KeyController());

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

		Snake screen = new Snake();
		snakePanel.setFocusable(true);
		snakePanel.add(screen);

		buttonEasy = new JButton("EASY");
		buttonMed = new JButton("MEDIUM");
		buttonHard = new JButton("HARD");
		buttonEasy.setBackground(new Color(10, 100, 0));
		buttonMed.setBackground(new Color(10, 100, 0));
		buttonHard.setBackground(new Color(10, 100, 0));

		EventEasyBut easyDificulty = new EventEasyBut();
		buttonEasy.addActionListener(easyDificulty);

		EventMedBut medDificulty = new EventMedBut();
		buttonMed.addActionListener(medDificulty);

		EventHardBut hardDificulty = new EventHardBut();
		buttonHard.addActionListener(hardDificulty);

		ctrlPanel.addActionListenerToButtons(new SnakeMouseControllsClickListener());

		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new GridLayout(5, 1));
		buttonPanel.setSize(new Dimension(100, 100));

		buttonPanel.add(buttonEasy);
		buttonPanel.add(buttonMed);
		buttonPanel.add(buttonHard);
		buttonPanel.add(Snake.scr);

		buttonPanel.add(ctrlPanel);

		jframe.add(snakePanel);
		jframe.add(buttonPanel);
		jframe.pack();

		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);

	}

	public class evMenu implements ActionListener {
		public void actionPerformed(ActionEvent enG) {

			jframe.setVisible(false);
			jframe.dispose();
			new GameFrame();

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

	public class EventEasyBut implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty1) {

			Snake.speed = 1000000;
			jframe.requestFocus();

		}
	}

	public class EventMedBut implements ActionListener {
		public void actionPerformed(ActionEvent eDificulty3) {

			Snake.speed = 600000;
			jframe.requestFocus();
		}
	}

	public class EventHardBut implements ActionListener {
		public void actionPerformed(ActionEvent hardDifficulty) {

			Snake.speed = 300000;
			jframe.requestFocus();
		}
	}

	public JButton[][] getCtrlButton() {
		return this.ctrlPanel.getCtrlButton();
	}

	public void addActionListenerToButtons(ActionListener actionListener) {
		ctrlPanel.addActionListenerToButtons(actionListener);
	}

}
