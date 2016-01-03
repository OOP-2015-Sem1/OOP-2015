
public class GameRunner {
	public static Card[] CardsPlayed = new Card[4];
	public static int TrickWonPlayer;
	public static int CardsPlayedIndex = 0;
	public static int Turn;

	private static Card[][] cardsDealed = Deck.Deal();
	static Player[] Players = new Player[4];

	public static void DealCards(int Turn) {
		cardsDealed = Deck.Deal();
		for (int i = 0; i < 4; i++) {
			Players[i] = new Player(cardsDealed[(i + Turn) % 4]);
		}
	}

	private static Card MinValCard(int nrPlayer) {
		int min = 14;
		int position = 0;
		if (CardsPlayed[0] == null) {
			for (int i = 0; i < Players[nrPlayer].handlength; i++) {
				if (Players[nrPlayer].hand[i].tovalue() < min) {
					min = Players[nrPlayer].hand[i].tovalue();
					position = i;
				}
			}
		} else {
			for (int i = 0; i < Players[nrPlayer].handlength; i++) {
				if ((Players[nrPlayer].hand[i].Suit == CardsPlayed[0].Suit)
						&& (Players[nrPlayer].hand[i].tovalue() < min)) {
					min = Players[nrPlayer].hand[i].tovalue();
					position = i;
				}
			}
		}
		return Players[nrPlayer].hand[position];

	}

	private static int MinValCardPosition(int nrPlayer) {
		int min = 14;
		int position = 0;
		if (CardsPlayed[0] == null) {
			for (int i = 0; i < Players[nrPlayer].handlength; i++) {
				if (Players[nrPlayer].hand[i].tovalue() < min) {
					min = Players[nrPlayer].hand[i].tovalue();
					position = i;
				}
			}
		} else {
			for (int i = 0; i < Players[nrPlayer].handlength; i++) {
				if ((Players[nrPlayer].hand[i].Suit == CardsPlayed[0].Suit)
						&& (Players[nrPlayer].hand[i].tovalue() < min)) {
					min = Players[nrPlayer].hand[i].tovalue();
					position = i;
				}
			}
		}
		return position;
	}

	private static Card MaxValCard(int nrPlayer) {
		int max = 0;
		int position = 0;
		for (int i = 0; i < Players[nrPlayer].handlength; i++) {
			if (Players[nrPlayer].hand[i].tovalue() > max) {
				max = Players[nrPlayer].hand[i].tovalue();
				position = i;
			}
		}
		return Players[nrPlayer].hand[position];
	}

	private static int MaxValCardPosition(int nrPlayer) {
		int max = 0;
		int position = 0;
		for (int i = 0; i < Players[nrPlayer].handlength; i++) {
			if (Players[nrPlayer].hand[i].tovalue() > max) {
				max = Players[nrPlayer].hand[i].tovalue();
				position = i;
			}
		}
		return position;
	}

	private static Card[] RemoveCardMinimum(int nrPlayer) {
		int position = MinValCardPosition(nrPlayer);
		for (int i = position; i < ((Players[nrPlayer].handlength) - 1); i++) {
			Players[nrPlayer].hand[i] = Players[nrPlayer].hand[i + 1];
		}
		(Players[nrPlayer].handlength)--;
		return Players[nrPlayer].hand;
	}

	private static Card[] RemoveCardMaximum(int nrPlayer) {
		int position = MaxValCardPosition(nrPlayer);
		for (int i = position; i < ((Players[nrPlayer].handlength) - 1); i++) {
			Players[nrPlayer].hand[i] = Players[nrPlayer].hand[i + 1];
		}
		(Players[nrPlayer].handlength)--;
		return Players[nrPlayer].hand;
	}

	public static boolean CheckForSuit(int nrPlayer) {
		boolean Check = false;
		for (int i = 0; i < Players[nrPlayer].handlength; i++) {
			if (Players[nrPlayer].hand[i].Suit == CardsPlayed[0].Suit)
				Check = true;
		}
		return Check;
	}

	private static void PutCardDown(int nrPlayer) {

		if (CardsPlayed[0] == null) {
			CardsPlayed[0] = MinValCard(nrPlayer);
			CardsPlayedIndex++;
		} else {
			if (CheckForSuit(nrPlayer) == true) {
				CardsPlayed[CardsPlayedIndex] = MinValCard(nrPlayer);
				CardsPlayedIndex++;
				RemoveCardMinimum(nrPlayer);
			} else {
				CardsPlayed[CardsPlayedIndex] = MaxValCard(nrPlayer);
				RemoveCardMaximum(nrPlayer);
				CardsPlayedIndex++;
			}
		}

	}

	private static int MaxValCardPlayedBy() {
		int max = 0;
		int player = 0;
		for (int i = 0; i < 4; i++) {
			if (CardsPlayed[i].tovalue() > max) {
				max = CardsPlayed[i].tovalue();
				player = i;
			}
		}
		return player;
	}

	private static void ScoreAdding() {
		for (int i = 0; i < 4; i++) {
			if ((CardsPlayed[i].Suit == "Hearts") && (CardsPlayed[i].Number == 11))
				Players[TrickWonPlayer].Score = +13;
			if (CardsPlayed[i].Suit == "Hearts")
				Players[TrickWonPlayer].Score++;
		}
	}

	private static void TrickEnded() {
		TrickWonPlayer = MaxValCardPlayedBy();
		ScoreAdding();
		for (int i = 0; i < 4; i++) {
			CardsPlayed[i] = null;
		}
		CardsPlayedIndex = 0;
	}

	static boolean GameEnded() {
		boolean ended = false;
		for (int i = 0; i < 4; i++) {
			if (Players[i].Score >= 100)
				ended = true;
		}
		return ended;
	}

	static int MinimumScorePlayer() {
		int minScore = 100;
		int winnerPlayer = 0;
		if (GameEnded() == true)
			for (int i = 0; i < 4; i++)
				if (Players[i].Score < minScore) {
					minScore = Players[i].Score;
					winnerPlayer = i;
				}
		return winnerPlayer;
	}

	public static void PlayGame() {

		int Turn = 0;
		DealCards(Turn);
		/*
		 * while (GameEnded() == false) {
		 * 
		 * /*for (int CardToPlay = 0; CardToPlay < 13; CardToPlay++) { boolean
		 * PlayerTurn = false;
		 * 
		 * for (int i = 0; i < 4; i++) { if (((MaxValCardPlayedBy() + i) % 4) ==
		 * 3) { PlayerTurn = true; } else { PutCardDown((MaxValCardPlayedBy() +
		 * i) % 4); } } } TrickEnded(); Turn++; } Turn = 0;
		 */
		new GameDisplay();
	}
}
