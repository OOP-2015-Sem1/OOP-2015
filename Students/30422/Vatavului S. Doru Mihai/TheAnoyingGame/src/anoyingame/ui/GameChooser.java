package anoyingame.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameChooser extends JFrame {

	private JPanel blankPanel = new JPanel();
	private JButton colorGameButton = new JButton("The Annoying Color Game");
	private JButton memoryGameButton = new JButton("The Even More Annoying Memory Game");
	private JLabel imageLabel;
	private ImageIcon gameLogo;

	public GameChooser() {

		gameLogo = new ImageIcon(getClass().getResource("GameLogo.png"));
		imageLabel = new JLabel(gameLogo);
		imageLabel.setBackground(Color.WHITE);

		this.setLayout(new GridLayout(2, 1));
		this.setBackground(Color.WHITE);
		this.add(imageLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 1));

		addBlankPanel(buttonPanel);

		colorButtonToFitTheme(colorGameButton);
		buttonPanel.add(colorGameButton);

		colorButtonToFitTheme(memoryGameButton);
		buttonPanel.add(memoryGameButton);

		addBlankPanel(buttonPanel);
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

	public JButton getButton1() {
		return colorGameButton;
	}

	public JButton getButton2() {
		return memoryGameButton;
	}
}
