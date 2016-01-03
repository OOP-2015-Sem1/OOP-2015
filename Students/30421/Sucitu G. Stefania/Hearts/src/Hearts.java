
public class Hearts {

	public static void main(String[] args) {
		
			Deck playingDeck = new Deck();
			playingDeck.FullDeck();
			playingDeck.Shuffle();
			GameRunner.PlayGame();
	}

}
