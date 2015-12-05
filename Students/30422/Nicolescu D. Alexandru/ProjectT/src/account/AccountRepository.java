package account;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class AccountRepository {

	public static final int NUMBER_OF_ACCOUNTS = 4;
	private static final boolean ADD_ACCOUNT = false;
	private static volatile AccountRepository accounts = null;
	private Player players[] = Player.getInstance();
	private int accountNr;

	public void readData() {

		try {
			FileInputStream fileIn = new FileInputStream("accounts.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			players = (Player[]) in.readObject();
			in.close();
			fileIn.close();
			if (ADD_ACCOUNT) {

				Scanner keyboard = new Scanner(System.in);
				System.out.println("Enter new account name:");
				players[NUMBER_OF_ACCOUNTS].setUser(keyboard.nextLine());
				System.out.println("Enter new account password:");
				players[NUMBER_OF_ACCOUNTS].setPassword(keyboard.nextLine());
				players[NUMBER_OF_ACCOUNTS].setScore(10);
				serialize();
			}

		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c)
		{
			System.out.println("Error");
			c.printStackTrace();
			return;
		}

	}

	public void serialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream("accounts.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(players);
			out.close();
			fileOut.close();

		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void replace(int newscore) {

		if (accounts.getAccountNr() != 0)
			players[accounts.getAccountNr()].setScore(newscore);
		accounts.serialize();
	}

	public boolean login(String user, String password) {
		boolean logedIn = false;
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			if (user.equals(players[i].getUser()))
				if (players[i].getPassword().equals(password)) {
					logedIn = true;
					setAccountNr(i);
				}
		}
		return logedIn;
	}

	public static synchronized AccountRepository getInstance() {
		if (accounts == null) {
			accounts = new AccountRepository();
		}

		return accounts;
	}

	public int getAccountNr() {
		return accountNr;
	}

	public void setAccountNr(int accountNr) {
		this.accountNr = accountNr;
	}

	public int getAccountScore() {
		return players[getAccountNr()].getScore();
	}

	public String getAccountName() {
		return players[getAccountNr()].getUser();
	}
}
