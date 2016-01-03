package battleship.logic;

import battleship.gui.PlayerDetailPanel;

public class Player {

	private boolean mainPlayer;

	private Board board;
	private Shoot shoot;

	public Player(boolean mainPalyer, PlayerDetailPanel opponent) {
		this.mainPlayer = mainPalyer;
		board = new Board(mainPalyer, opponent);
		shoot = new Shoot();
	}

	// getters and setters
	public boolean isMainPlayer() {
		return mainPlayer;
	}

	public void setMainPlayer(boolean mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Shoot getShoot() {
		return shoot;
	}

	public void setShoot(Shoot shoot) {
		this.shoot = shoot;
	}

}
