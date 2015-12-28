package view;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class IntroFrame extends JFrame {

	private static final long serialVersionUID = 1914323995971073350L;
	private JButton startButton;
	private JPanel jpl;

	public IntroFrame() {
		super("This is the intro frame");
		jpl = new JPanel();
		jpl.setLayout(new GridLayout(3, 3));

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
		JButton startButton = new JButton("Start button");
		jpl.setBackground(Color.ORANGE);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(getParent(), "Do you still want to proceed?", "Question",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					new GamePanelUI();
				}
			}
		});
		jpl.add(startButton);
		this.add(jpl);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new IntroFrame();
			}
		});
	}
}
