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

import controllers.BlackJackController;
import controllers.MessageDialogs;

public class BlackJackFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public JLabel showmsg = new JLabel();
	public JLabel showmoney = new JLabel();
	private JPanel northPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	public JPanel cardPanel = new JPanel();
	public JPanel playerPanel = new JPanel();
	public JPanel dealerPanel = new JPanel();
	public JPanel scorePanel = new JPanel();
	public JLabel playerScore = new JLabel();
	public JLabel dealerScore = new JLabel();
	private Button hit = new Button("Hit");
	private Button stand = new Button("Stand");
	private Button newGame = new Button("New Game");
	private static String userName;
	private static String password;
	public Color tableColor = new Color(6, 154, 6);

	public BlackJackFrame() {
		Color buttonPanelColor = new Color(6, 121, 6);
		Color buttonColor = new Color(147, 169, 6);
		buttonPanel.setBackground(buttonPanelColor);
		northPanel.setLayout(new GridLayout(0, 2));
		add(buttonPanel, BorderLayout.SOUTH);
		add(setScorePanel(scorePanel), BorderLayout.WEST);
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
		northPanel.add(showmsg);
		northPanel.add(showmoney);
		add(northPanel, BorderLayout.NORTH);
		add(scorePanel, BorderLayout.WEST);
		MessageDialogs.createLoginWindow();
		JOptionPane.showMessageDialog(null, "Wellcome to the Game of BlackJack");
		BlackJackController.setMoney(MessageDialogs.queryForMoney());
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

	public final String getUserName() {
		return userName;
	}

	public final static void setUserName(String userName) {
		BlackJackFrame.userName = userName;
	}

	public final String getPassword() {
		return password;
	}

	public final static void setPassword(String password) {
		BlackJackFrame.password = password;
	}

	public JPanel setScorePanel(JPanel panel) {
		panel.setLayout(new GridLayout(10, 0));
		for (int i = 0; i <= 9; i++) {
			if (i == 2) {
				JPanel scorePan = new JPanel();
				scorePan.setBackground(tableColor);
				scorePan.add(dealerScore);
				panel.add(scorePan);
			} else if (i == 7) {
				JPanel scorePan = new JPanel();
				scorePan.setBackground(tableColor);
				scorePan.add(playerScore);
				panel.add(scorePan);
			} else {
				JPanel fillPanel = new JPanel();
				fillPanel.setBackground(tableColor);
				panel.add(fillPanel);
			}
		}
		return panel;
	}

}
