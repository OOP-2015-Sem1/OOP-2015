import java.util.Random;

public class Deck {

	private static Card[] Deck;
	private int nrOfCards;
	public static Card[][] dealCards;
	
	public void Shuffle() {
		Random randgen = new Random();
		Card temp;
		int j;
		for (int i = 0; i < nrOfCards; i++) {
			j = randgen.nextInt(this.nrOfCards);
			temp = this.Deck[i];
			this.Deck[i] = this.Deck[j];
			this.Deck[j] = temp;
		}
	}

	public void FullDeck() {
		this.nrOfCards = 52;
		this.Deck = new Card[this.nrOfCards];

		int CardCreatedIndex = 0;

		// for each suit
		for (int suit = 0; suit <= 3; suit++) {
			// for each number
			for (int CardHandNumber = 0; CardHandNumber < 13; CardHandNumber++) {
				this.Deck[CardCreatedIndex] = new Card(CardHandNumber);
				CardCreatedIndex++;
			}
		}
	}

	public String tostring() {
		String CardDeck = "";
		for (int j = 0; j <= 51; j++) {
			CardDeck += "\n" + j + "-" + Deck[j].toString();
		}
		return CardDeck;
	}

	public void printDeck(int numToPrint) {
		for (int c = 0; c <= numToPrint; c++) {
			System.out.printf("\n %d/52 %s", c + 1, this.Deck[c].tostring());
		}
	}

	public static Card[][] Deal() {
		Card[][] Deal = new Card[4][13];
		for (int row = 0; row <4 ; row++) {
			for (int col = 0; col < 13; col++) {
				Deal[row][col] = Deck[(row * col) + col];
			}}
		return Deal;
	}

}
