package account;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class AccountRepository {

	public static final int NUMBER_OF_ACCOUNTS = 3;
	private static final boolean SAVE_ACCOUNTS =  false;
	private static volatile AccountRepository accounts = null;
	private Player players[] = Player.getInstance();
	private int accountNr;

	public void readData() {
		if (SAVE_ACCOUNTS) {

				serialize();
				Scanner keyboard = new Scanner(System.in);
				System.out.println("Enter new account name:");
				players[NUMBER_OF_ACCOUNTS].setUser(keyboard.nextLine());
				System.out.println("Enter new account password:");
				players[NUMBER_OF_ACCOUNTS].setPassword(keyboard.nextLine());
				players[NUMBER_OF_ACCOUNTS].setScore(10);
			
		}else {
			try
	         {
	            FileInputStream fileIn = new FileInputStream("accounts.ser");
	            ObjectInputStream in = new ObjectInputStream(fileIn);
	            players = (Player[]) in.readObject();
	            in.close();
	            fileIn.close();
	            System.out.println(players[1].getScore());
	            //the score starts as 1893
	        }catch(IOException i)
	        {
	            i.printStackTrace();
	            return;
	        }catch(ClassNotFoundException c)
	        {
	            System.out.println("Error");
	            c.printStackTrace();
	            return;
	        }
		}
	}

	public void serialize() {
		try {
			System.out.println(players[1].getScore());
			//here the score is again 1893 even if it was modified before
			FileOutputStream fileOut = new FileOutputStream("accounts.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(players);
			out.close();
			fileOut.close();
			
		} catch (IOException i)
		{
			i.printStackTrace();
		}
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
