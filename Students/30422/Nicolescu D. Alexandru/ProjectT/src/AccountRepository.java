import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AccountRepository {

	private static final int NUMBER_OF_ACCOUNTS = 3;
	private Player players[] = new Player[NUMBER_OF_ACCOUNTS];
	private int accountNr;

	public void readData() {
		try (BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = "start";
			int nrOfPlayers = 0;
			while (nrOfPlayers != NUMBER_OF_ACCOUNTS) {
				players[nrOfPlayers] = new Player();

				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				players[nrOfPlayers].setUser(line);

				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				players[nrOfPlayers].setPassword(line);

				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				players[nrOfPlayers].setScore(Integer.parseInt(line));

				// System.out.println(players[nrOfPlayers].getPassword() + " " +
				// players[nrOfPlayers].getUser() + " "
				// + players[nrOfPlayers].getScore()+" "+nrOfPlayers);
				nrOfPlayers++;
			}

			// String everything = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean login(String user, String password) {
		boolean logedIn = false;
		//Serial(0);
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			if (user.equals(players[i].getUser()))
				if (players[i].getPassword().equals(password)) {
					logedIn = true;
					setAccountNr(i);
				}
		}
		return logedIn;
	}

	public static void Serial(int choice) {

		try {
			Player mb = new Player();

			if (choice == 0) {
				// write object to file
				FileOutputStream fos = new FileOutputStream("mybean.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(mb);
				oos.close();
			} else if (choice == 1) {
				// read object from file
				FileInputStream fis = new FileInputStream("mybean.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				Player result = (Player) ois.readObject();
				ois.close();
			}
			// System.out.println("One:" + result.getOne() + ", Two:" +
			// result.getTwo());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
