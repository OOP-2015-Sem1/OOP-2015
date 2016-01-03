package game;

import javax.swing.JOptionPane;

public class Bet {
	static int bet;

	public static int newBet(int yourMoney) {
		String betString;
		if (yourMoney <= 0) {
			JOptionPane.showMessageDialog(null, "You are out of money");
			System.exit(0);
		}

		do {
			betString = JOptionPane.showInputDialog("You have " + yourMoney + ". How much you want to bet?");

			if (betString == null)
				System.exit(0);
			try {
				bet = Integer.parseInt(betString);
			} catch (Exception e) {
			}

			if ((bet < 1 || bet > yourMoney))
				JOptionPane.showMessageDialog(null, "Your answer must be an integer number between 1 and " + yourMoney);

		} while (bet < 1 || bet > yourMoney);
		return bet;
	}

	public static int currentMoney(boolean win, boolean lose, int yourMoney, int bet) {
		if (win == true && lose == false) {
			yourMoney = yourMoney + bet;
		} else if (win == false && lose == true) {
			yourMoney = yourMoney - bet;
		}
		return yourMoney;
	}

	public static String message(boolean win, boolean lose, String text) {
		if (win == true && lose == false) {
			text = "You won " + bet;
		} else if (win == false && lose == true) {
			text = "You lost " + bet;
		}
		return text;

	}

	public static int refreshBet(boolean win, boolean lose) {
		if (win == true && lose == false) {
			bet = 0;
		} else if (win == false && lose == true) {
			bet = 0;
		}
		return bet;
	}
}
