package game;

public class Turn {

	private int playerInTurn;
	private int numberOfPlayers;
	private int direction;
	public static final int CLOCKWISE = 1;
	public static final int COUNTER_CLOCKWISE = -1;

	public Turn(int startingPlayer, int numberPlayers, int direction) {

		if (startingPlayer >= numberPlayers)
			throw new IllegalArgumentException("Starting player is out of the range of players !");

		if (direction != CLOCKWISE && direction != COUNTER_CLOCKWISE)
			throw new IllegalArgumentException("Direction is not right !");

		playerInTurn = startingPlayer;
		this.numberOfPlayers = numberPlayers;
		this.direction = direction;
	}

	public int showCurrentTurn() {
		return playerInTurn;
	}

	public int showPrevTurn() {
		return checkBoundaries(playerInTurn - direction);
	}

	public int showNextTurn() {
		return checkBoundaries(playerInTurn + direction);
	}

	public void nextTurn() {
		playerInTurn += direction;
		playerInTurn = checkBoundaries(playerInTurn);
	}

	public void changeDirection() {
		direction *= -1;
	}

	public int getDirection() {
		return direction;
	}

	public void retirePlayer() {
		if (numberOfPlayers == 1)
			return;
		numberOfPlayers--;
		checkBoundaries(playerInTurn);
	}

	public void addNewPlayer() {
		numberOfPlayers++;
	}

	private int checkBoundaries(int position) {
		if (position >= numberOfPlayers)
			return 0;
		else if (position < 0)
			return numberOfPlayers - 1;
		else
			return position;
	}
}