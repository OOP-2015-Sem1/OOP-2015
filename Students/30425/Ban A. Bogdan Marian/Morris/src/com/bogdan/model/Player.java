package com.bogdan.model;

public class Player {
	private String playerName;
	private int availablePieces;
	private int totalPieces = 9;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getAvailablePieces() {
		return availablePieces;
	}

	public void increasePieces() {
		availablePieces++;
	}

	public void decreasePieces() {
		totalPieces--;
	}

	public boolean hasLost() {
		return totalPieces == 2;
	}
}
