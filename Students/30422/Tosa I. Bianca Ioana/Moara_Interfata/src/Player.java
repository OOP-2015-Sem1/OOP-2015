
public class Player {
	public int playerMoves;
	public boolean playerOneTurn;
	public int playerPieces;
	public int oldNumberOfMorris;

	public void setPlayerTurn(boolean playerOneTurn) {
		this.playerOneTurn = playerOneTurn;
	}

	public boolean getPlayerTurn() {
		return this.playerOneTurn;
	}

	public void setPlayerMoves(int moves) {
		this.playerMoves = moves;
	}

	public int getPlayerMoves() {
		return this.playerMoves;
	}

	public void setPlayerPieces(int pieces) {
		this.playerPieces = pieces;
	}

	public int getPlayerPieces() {
		return this.playerPieces;
	}

	public void setOldNumberOfMorris(int morris) {
		this.oldNumberOfMorris = morris;
	}

	public int getOldNumberOfMorris() {
		return this.oldNumberOfMorris;
	}

	public void incrementMoves() {
		this.playerMoves++;
	}

	public void decrementPieces() {
		this.playerPieces--;
	}

}
