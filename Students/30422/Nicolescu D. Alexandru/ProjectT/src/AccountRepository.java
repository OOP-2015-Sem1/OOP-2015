import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AccountRepository {

	private static int NUMBER_OF_ACCOUNTS = 2;
	private Player players[] = new Player[NUMBER_OF_ACCOUNTS];
	private int accountNr;

	public void readData() {
		try (BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line="start";
			int nrOfPlayers = 0;
			while (nrOfPlayers!=NUMBER_OF_ACCOUNTS) {
				players[nrOfPlayers] = new Player();

				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				players[nrOfPlayers].setUser(line);
				
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				players[nrOfPlayers].setPassword(Integer.parseInt(line));
				
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				players[nrOfPlayers].setScore(Integer.parseInt(line));
				
				
//				 System.out.println(players[nrOfPlayers].getPassword() + " " +
//				 players[nrOfPlayers].getUser() + " "
//				 + players[nrOfPlayers].getScore()+" "+nrOfPlayers);
				nrOfPlayers++;
			}
			
			for (int i = 0; i < 2; i++){
				System.out.println(players[i].getUser());
			}
			// String everything = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean login(String user, String password) {
		boolean logedIn = false;
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			if (user.equals(players[i].getUser()))
				if (players[i].getPassword() == password.hashCode()) {
					logedIn = true;
					setAccountNr(i);
					System.out.println(players[i].getUser());
				}
		}
		return logedIn;
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
}
