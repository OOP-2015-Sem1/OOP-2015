package controllers;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.BlackJackFrame;

public class MessageDialogs {
	public static String[] buttonNames = { "Login", "Cancel" };

	public static int queryForNumberOfDecks() {
		String decks = JOptionPane.showInputDialog("Number of decks(1-8):");
		Integer decksNumber;
		try {
			decksNumber = Integer.parseInt(decks);
			if (decksNumber < 1 || decksNumber > 8)
				decksNumber = 1;
		} catch (NumberFormatException e) {
			decksNumber = 1;
		}
		return decksNumber;
	}

	public static int queryForMoney() {
		String money = JOptionPane.showInputDialog("Sum of money you want to enter the game:");
		Integer sumOfMoney;
		sumOfMoney = Integer.parseInt(money);
		return sumOfMoney;
	}

	public static int queryForBet() {
		String bet = JOptionPane.showInputDialog("Palce your bet here:");
		Integer betMoney;
		betMoney = Integer.parseInt(bet);
		if (betMoney > BlackJackController.getMoney()) {
			String bet1 = JOptionPane.showInputDialog(null,
					"You do not have enough money to sustain the bet! Your bet should be smaller than "
							+ BlackJackController.getMoney() + "$!");
			betMoney = Integer.parseInt(bet1);
		}
		return betMoney;
	}

	public static void optionPane(int player, int dealer) {
		Object[] options = { "Leave", "Continue" };
		int n = JOptionPane.showOptionDialog(null,
				"You have " + BlackJackController.getMoney() + "$ left! Would you like to leave the table or continue?",
				"BlackJack", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			System.exit(0);
		} else if (n == 1) {
			new MainGame();
			BlackJackController.setMoney(queryForMoney());
		}
	}

	public static void createLoginWindow() {
		JPanel loginPanel;
		JLabel usernameLabel = new JLabel("Username:", JLabel.LEFT);
		JTextField usernameText = new JTextField(10);
		JLabel passwordLabel = new JLabel("Password:", JLabel.LEFT);
		JTextField passwordText = new JTextField(10);
		loginPanel = new JPanel(false);
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(0, 1));
		namePanel.add(usernameLabel);
		namePanel.add(usernameText);
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new GridLayout(0, 1));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordText);
		loginPanel.add(namePanel);
		loginPanel.add(passwordPanel);
		int option = JOptionPane.showOptionDialog(null, loginPanel, "Login BlackJack ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, buttonNames, buttonNames[0]);
		if (((option == 0) && (usernameText.getText().equals("bolo")) && (passwordText.getText().equals("1234")))
				|| (((option == 0) && (usernameText.getText().equals("alex"))
						&& (passwordText.getText().equals("1234"))))) {
			new MainGame();
		} else {
			JOptionPane.showMessageDialog(null, "Wrong username or password!");
			createLoginWindow();
		}
		BlackJackFrame.setUserName(usernameText.getText());
		BlackJackFrame.setPassword(passwordText.getText());
	}
}
