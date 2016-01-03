import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Card {

	String Suit;

	int Number;
	
	public JLabel CardPicture;
	
	
	private static int s = 0;

	public Card(int aNumber) {
		if (s < 13) {
			this.Suit = "Clubs";
			this.Number = aNumber;
		} else if (s < 26) {
			this.Suit = "Diamonds";
			this.Number = aNumber;
		} else if (s < 39) {
			this.Suit = "Hearts";
			this.Number = aNumber;
		} else {
			this.Suit = "Spades";
			this.Number = aNumber;
		}
		s++;
		ImageIcon picture = new ImageIcon("cardimage/"+Integer.toString(Number)+Suit+".png");
		CardPicture = new JLabel(picture);
	}

	public int GetNumber() {
		return Number;
		

	}
	
	
	public String tostring() {
		String numstr = "error";

		if (this.Number == 1) {
			numstr = "Two";
		}

		if (this.Number == 2) {
			numstr = "Three";
		}

		if (this.Number == 3) {
			numstr = "Four";
		}

		if (this.Number == 4) {
			numstr = "Five";
		}

		if (this.Number == 5) {
			numstr = "Six";
		}

		if (this.Number == 6) {
			numstr = "Seven";
		}

		if (this.Number == 7) {
			numstr = "Eight";
		}

		if (this.Number == 8) {
			numstr = "Nine";
		}

		if (this.Number == 9) {
			numstr = "Ten";
		}

		if (this.Number == 10) {
			numstr = "Jack";
		}

		if (this.Number == 11) {
			numstr = "Queen";
		}

		if (this.Number == 12) {
			numstr = "King";
		}

		if (this.Number == 0) {
			numstr = "Ace";
		}
		return numstr + " of " + Suit;
	}

	public int tovalue() {

		int CardValue = 0;
		if (this.Number == 0) {
			CardValue = 13;
		}
		if (this.Number == 1) {
			CardValue = 2;
		}
		if (this.Number == 2) {
			CardValue = 3;
		}
		if (this.Number == 3) {
			CardValue = 4;
		}
		if (this.Number == 4) {
			CardValue = 5;
		}
		if (this.Number == 5) {
			CardValue = 6;
		}
		if (this.Number == 6) {
			CardValue = 7;
		}
		if (this.Number == 7) {
			CardValue = 8;
		}
		if (this.Number == 8) {
			CardValue = 9;
		}
		if (this.Number == 9) {
			CardValue = 10;
		}
		if (this.Number == 10) {
			CardValue = 11;
		}
		if (this.Number ==11) {
			CardValue = 12;
		}
		return CardValue;
		}
}