package login;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileSerialization {
	private static final int YES = 1;
	private static final int NO = 0;
	public Account account[];
	private static FileSerialization instance = null;
	private int numberOfAccounts = 6;
	private JTextField playerName = new JTextField(10);;
	private int firstPlayerAccountNr = -1;
	private int secondPlayerAccountNr = -1;

	public FileSerialization() {
		readAccount();
	}

	public void login() {
		for (int k = 0; k < numberOfAccounts; k++)
			System.out.println(account[k].getName());
		for (int i = 0; i < 2; i++) {
			int newAccount = enterData();

			if (newAccount == YES) {
				createAccount(playerName.getText());
				if (firstPlayerAccountNr == -1)
					firstPlayerAccountNr = numberOfAccounts - 1;
				else if (secondPlayerAccountNr == -1)
					secondPlayerAccountNr = numberOfAccounts - 1;
			} else if (newAccount == NO) {
				readAccount();
				for (int j = 0; j < numberOfAccounts; j++) {
					if (account[j].getName().equals(playerName.getText())) {
						if (firstPlayerAccountNr == -1)
							firstPlayerAccountNr = j;
						else if (secondPlayerAccountNr == -1)
							secondPlayerAccountNr = j;
						JOptionPane.showMessageDialog(null,
								"Player " + account[j].getName() + " has a current score of " + account[j].getScore());
					}
				}
			}
		}
	}

	public int enterData() {
		JPanel accountsPanel = new JPanel();
		int newAccount = JOptionPane.showConfirmDialog(null, accountsPanel, "Do you have an account?",
				JOptionPane.YES_NO_CANCEL_OPTION);
		accountsPanel.add(new JLabel("Player account :"));
		accountsPanel.add(playerName);
		JOptionPane.showConfirmDialog(null, accountsPanel, "Player account : ", JOptionPane.OK_CANCEL_OPTION);
		return newAccount;
	}

	public void createAccount(String playerName) {
		try {
			if (!playerName.equals("onlySave")) {
				account[numberOfAccounts] = new Account();
				account[numberOfAccounts].setName(playerName);
				account[numberOfAccounts].setScore(0);
				numberOfAccounts++;
				System.out.println("Current number of accounts is " + numberOfAccounts);
			}
			FileOutputStream fos = new FileOutputStream("account.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(account);
			oos.close();

		} catch (IOException i) {
			i.printStackTrace();
			return;
		}
	}

	public void readAccount() {
		try {
			FileInputStream fis = new FileInputStream("account.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			account = (Account[]) ois.readObject();
			ois.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getFirstPlayerAccountNr() {
		return firstPlayerAccountNr;
	}

	public int getSecondPlayerAccountNr() {
		return secondPlayerAccountNr;
	}

	public static FileSerialization getInstance() {
		if (instance == null) {
			instance = new FileSerialization();
		}

		return instance;
	}
}
