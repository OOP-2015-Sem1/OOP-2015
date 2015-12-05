package account;

public class UpdateScore {
	
	private AccountRepository accounts = AccountRepository.getInstance();
	private Player players[] = Player.getInstance();

	public void replace(int newscore) {

		

		players[accounts.getAccountNr()].setScore(newscore);
		System.out.println(players[1].getScore());
		//here the score is updated to 1913 because i won, still when it gets to serialize() it is 1893
		accounts.serialize();
	}
	
}