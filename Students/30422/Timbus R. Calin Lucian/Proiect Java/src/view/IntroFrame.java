package view;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.TimerClass;

public class IntroFrame extends JFrame {

	private static final long serialVersionUID = 1914323995971073350L;
	private static JLabel l1;
	private static JTextField tf;
	private static JButton startButton;
	private static JPanel jpl;
	private static JLabel timeLabel;
	public IntroFrame() {
		super("This is the intro frame");
		jpl = new JPanel();
		TimerClass a = new TimerClass();
		jpl.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		l1 = new JLabel("Enter your name: ");

		/// Creating the menuBar alongside with the options button///
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu options = new JMenu("Options");
		menuBar.add(options);
		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HelpFrame();
			}
		});
		options.add(help);
		///// First column//////
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;

		gbc.gridx = 0;
		gbc.gridy = 0;
		jpl.add(l1, gbc);

		// Second column//
		tf = new JTextField(20);
		gbc.weightx = 2;
		gbc.weighty = 2;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		jpl.add(tf, gbc);

		/// Second line && First Column///
		timeLabel = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 1;
		jpl.add(timeLabel, gbc);

		JButton startButton = new JButton("Start button");
		jpl.setBackground(Color.ORANGE);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(getParent(), "Do you still want to continue?", "Question",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					String s = tf.getText();
					if (s.compareTo("") == 0) {
						JOptionPane.showMessageDialog(startButton,
								"You may still want to introduce your name" + " before you continue");
					} else {
						new MainGame();
					}
				}
			}
		});
		///// Second column && Second Line/////
		gbc.gridx = 1;
		gbc.gridy = 1;
		jpl.add(startButton, gbc);
		this.add(jpl);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new IntroFrame();
			}
		});
	}
}
