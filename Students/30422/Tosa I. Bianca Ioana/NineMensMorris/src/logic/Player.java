package logic;

public class Player {
	public int playerMoves;
	public boolean active;
	public int playerPieces;
	public int oldNumberOfMorris;
	public int tag;

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getTag() {
		return this.tag;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return this.active;
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
