package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BlackJackFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public JLabel showmsg = new JLabel();
	private JPanel buttonPanel = new JPanel();
	public JPanel cardPanel = new JPanel();
	public JPanel playerPanel = new JPanel();
	public JPanel dealerPanel = new JPanel();
	private Button hit = new Button("Hit");
	private Button stand = new Button("Stand");
	private Button newGame = new Button("New Game");

	public BlackJackFrame() {
		Color buttonPanelColor = new Color(6, 121, 6);
		Color buttonColor = new Color(147, 169, 6);
		buttonPanel.setBackground(buttonPanelColor);
		Color tableColor = new Color(6, 154, 6);
		add(buttonPanel, BorderLayout.SOUTH);
		hit.setBackground(buttonColor);
		stand.setBackground(buttonColor);
		newGame.setBackground(buttonColor);
		buttonPanel.add(hit);
		buttonPanel.add(stand);
		buttonPanel.add(newGame);
		dealerPanel.setBackground(tableColor);
		playerPanel.setBackground(tableColor);
		cardPanel.setBackground(tableColor);
		cardPanel.setLayout(new GridLayout(2, 0));
		add(cardPanel, BorderLayout.CENTER);
		add(showmsg, BorderLayout.NORTH);
		JOptionPane.showMessageDialog(null, "Wellcome to the Game of BlackJack");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1366, 700);
		setVisible(true);
	}

	public final void setHitButtonActionListener(final ActionListener a) {
		hit.addActionListener(a);

	}

	public final void setStandButtonActionListener(final ActionListener a) {
		stand.addActionListener(a);
	}

	public final void setNewGameButtonActionListener(final ActionListener a) {
		newGame.addActionListener(a);
	}
	

}
