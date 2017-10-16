package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameButtons extends JPanel implements ActionListener {

	private static final long serialVersionUID = 3832597063314668825L;

	private JButton newButton;
	private JButton exitButton;
	private JButton solButton;
	private JButton solButton2;

	public GameButtons() {
		super(new BorderLayout());

		JPanel buttonPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		add(buttonPan, BorderLayout.NORTH);

		newButton = new JButton("New Puzzle");
		newButton.addActionListener(this);
		buttonPan.add(newButton);
		exitButton = new JButton("Exit Game");
		exitButton.addActionListener(this);
		buttonPan.add(exitButton);

		solButton = new JButton("Puzzle#1 Solution");
		solButton.addActionListener(this);
		buttonPan.add(solButton);
		solButton2 = new JButton("Puzzle#2 Solution");
		solButton2.addActionListener(this);
		buttonPan.add(solButton2);

	}

	public JButton getNewButton() {
		return this.newButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == exitButton) {
			System.exit(0);
		} else if (e.getSource() == solButton) {
			GameButtons bear = new GameButtons();
			bear.DisplayImage("Images/bear/bear_original.jpg");
		} else if (e.getSource() == solButton2) {
			GameButtons deer = new GameButtons();
			deer.DisplayImage("Images/deer/image.jpg");
		}

	}

	public void DisplayImage(String path) {

		JFrame frame = new JFrame("Puzzle Solution");
		JPanel panel = new JPanel();
		JLabel label = new JLabel();

		label.setIcon(new ImageIcon(path));
		panel.add(label);
		frame.add(panel);

		frame.pack();
		frame.setVisible(true);
		frame.setSize(500, 1200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	}

}
