package anoyingame.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JFrame {

	private JPanel blankPanel = new JPanel();
	private JButton easyMode = new JButton("EASY");
	private JButton mediumMode = new JButton("MEDIUM");
	private JButton hardMode = new JButton("HARD");
	private JLabel headerLabel;

	private int difficulty;

	public Menu(ImageIcon header) {
		this.setLayout(new GridLayout(2, 1));

		headerLabel = new JLabel(header);
		headerLabel.setBackground(Color.WHITE);

		this.setBackground(Color.WHITE);
		this.add(headerLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 1));

		addBlankPanel(buttonPanel);

		colorButtonToFitTheme(easyMode);
		colorButtonToFitTheme(mediumMode);
		colorButtonToFitTheme(hardMode);

		buttonPanel.add(easyMode);
		buttonPanel.add(mediumMode);
		buttonPanel.add(hardMode);

		addBlankPanel(buttonPanel);

		this.add(buttonPanel);
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public void addBlankPanel(JPanel mainPanel) {
		blankPanel = new JPanel();
		blankPanel.setBackground(Color.WHITE);
		mainPanel.add(blankPanel);
	}

	public void colorButtonToFitTheme(JButton button) {
		button.setBackground(new Color(0, 178, 192));
		button.setForeground(new Color(251, 233, 163));
		button.setFont(button.getFont().deriveFont(Font.BOLD));
	}

	public JButton getButton(String level) {
		if (level.equals("easy"))
			return easyMode;
		else if (level.equals("hard"))
			return hardMode;
		else
			return mediumMode;
	}

	public int getMode() {
		return difficulty;
	}
}
