package options;

public class Options {

	private int numPlayers;
	private int numCards;
	private int maxCards;
	private int difficultyLevel;
	private boolean existsZeroRule;
	private boolean existsWildWin;

	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	public static final int HARD = 2;

	public Options(int numPlayers, int numCards, int difficultyLevel, boolean existsZeroRule, boolean existsWildWin,
			int maxCards) {

		if (numPlayers < 2 || numPlayers > 6)
			throw new RuntimeException("Invalid new number of players !");
		if (numCards < 2 || numCards > 20)
			throw new RuntimeException("Invalid new number of cards !");
		if (difficultyLevel != EASY && difficultyLevel != MEDIUM && difficultyLevel != HARD)
			throw new RuntimeException("Invalid new difficulty level !");
		if (numPlayers * numCards > maxCards)
			throw new RuntimeException("Too many starting cards !");

		this.numPlayers = numPlayers;
		this.numCards = numCards;
		this.maxCards = maxCards;
		this.difficultyLevel = difficultyLevel;

		this.existsZeroRule = existsZeroRule;
		this.existsWildWin = existsWildWin;
	}

	public Options() {
		this(4, 7, MEDIUM, false, true, 112);
	}

	public int getNumberPlayers() {
		return numPlayers;
	}

	public int getNumberCards() {
		return numCards;
	}

	public int getMaxCards() {
		return maxCards;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public boolean withZeroRule() {
		return existsZeroRule;
	}

	public boolean withWildWin() {
		return existsWildWin;
	}

	public void setNumberPlayers(int number) {
		if (number < 2 || number > 6)
			throw new RuntimeException("Invalid new number of players !");

		this.numPlayers = number;
	}

	public void setNumberCards(int number) {
		if (number < 2 || number > 20)
			throw new RuntimeException("Invalid new number of cards !");

		this.numCards = number;
	}

	public void setDifficultyLevel(int level) {
		if (level != EASY && level != MEDIUM && level != HARD)
			throw new RuntimeException("Invalid new difficulty level !");

		this.difficultyLevel = level;
	}

	public void setZeroRule(boolean exists) {
		this.existsZeroRule = exists;
	}

	public void setWildWin(boolean exists) {
		this.existsWildWin = exists;
	}
}
