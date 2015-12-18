package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Frame {

	private JFrame jframe = new JFrame();;
	private JPanel panel1;
	private JPanel panel2;
	private ControlsPanel c = new ControlsPanel();

	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem newG;
	private JButton b1;
	private JButton b2;
	private JButton b3;

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

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel1.setLayout(new GridLayout(1, 1));

		Screen s = new Screen();
		panel1.setFocusable(true);
		panel1.add(s);

		b1 = new JButton("EASY");
		b2 = new JButton("MEDIUM");
		b3 = new JButton("HARD");
		b1.setBackground(new Color(10, 100, 0));
		b2.setBackground(new Color(10, 100, 0));
		b3.setBackground(new Color(10, 100, 0));

		event eDificulty1 = new event();
		b1.addActionListener(eDificulty1);

		event2 eDificulty2 = new event2();
		b2.addActionListener(eDificulty2);

		event3 eDificulty3 = new event3();
		b3.addActionListener(eDificulty3);

		panel2.setBackground(Color.LIGHT_GRAY);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.setSize(new Dimension(100, 100));
		c.setScore(Screen.score);
		panel2.add(b1);
		panel2.add(b2);
		panel2.add(b3);
		panel2.add(c);

		jframe.add(panel1);
		jframe.add(panel2);
		jframe.pack();

		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);

	}

	public JButton[][] getControlButtons() {
		return this.c.getControlButtons();
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
			c.addActionListenerToButtons(actionListener);
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
