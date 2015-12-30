package battleship.logic;

import java.util.ArrayList;
import java.util.List;

import battleship.gui.PlayerDetailPanel;
import battleship.model.BoardPosition;

public class Board {

	private List<BoardPosition> boardB = new ArrayList<BoardPosition>();
	private Ship ships;
	private Shoot shoot;
	private boolean ganeStart = false;
	boolean mainPlayer;

	private PlayerDetailPanel opponent;

	public Board(boolean mainPlayer, PlayerDetailPanel opp) {
		opponent = opp;
		this.mainPlayer = mainPlayer;
		ships = new Ship(mainPlayer);
		shoot = new Shoot();
		initBoardPositions(mainPlayer);
	}

	public void initBoardPositions(boolean mainPlayer) {
		BoardPosition position_0 = new BoardPosition(-1, mainPlayer, ships, 0, ganeStart, opponent);
		boardB.add(position_0);

		BoardPosition position_1 = new BoardPosition(-1, mainPlayer, ships, 1, ganeStart, opponent);
		boardB.add(position_1);

		BoardPosition position_2 = new BoardPosition(-1, mainPlayer, ships, 2, ganeStart, opponent);
		boardB.add(position_2);

		BoardPosition position_3 = new BoardPosition(-1, mainPlayer, ships, 3, ganeStart, opponent);
		boardB.add(position_3);

		BoardPosition position_4 = new BoardPosition(-1, mainPlayer, ships, 4, ganeStart, opponent);
		boardB.add(position_4);

		BoardPosition position_5 = new BoardPosition(-1, mainPlayer, ships, 5, ganeStart, opponent);
		boardB.add(position_5);

		BoardPosition position_6 = new BoardPosition(-1, mainPlayer, ships, 6, ganeStart, opponent);
		boardB.add(position_6);

		BoardPosition position_7 = new BoardPosition(-1, mainPlayer, ships, 7, ganeStart, opponent);
		boardB.add(position_7);

		BoardPosition position_8 = new BoardPosition(-1, mainPlayer, ships, 8, ganeStart, opponent);
		boardB.add(position_8);

		BoardPosition position_9 = new BoardPosition(-1, mainPlayer, ships, 9, ganeStart, opponent);
		boardB.add(position_9);

		BoardPosition position_10 = new BoardPosition(-1, mainPlayer, ships, 10, ganeStart, opponent);
		boardB.add(position_10);

		BoardPosition position_11 = new BoardPosition(-1, mainPlayer, ships, 11, ganeStart, opponent);
		boardB.add(position_11);

		BoardPosition position_12 = new BoardPosition(-1, mainPlayer, ships, 12, ganeStart, opponent);
		boardB.add(position_12);

		BoardPosition position_13 = new BoardPosition(-1, mainPlayer, ships, 13, ganeStart, opponent);
		boardB.add(position_13);

		BoardPosition position_14 = new BoardPosition(-1, mainPlayer, ships, 14, ganeStart, opponent);
		boardB.add(position_14);

		BoardPosition position_15 = new BoardPosition(-1, mainPlayer, ships, 15, ganeStart, opponent);
		boardB.add(position_15);

		BoardPosition position_16 = new BoardPosition(-1, mainPlayer, ships, 16, ganeStart, opponent);
		boardB.add(position_16);

		BoardPosition position_17 = new BoardPosition(-1, mainPlayer, ships, 17, ganeStart, opponent);
		boardB.add(position_17);

		BoardPosition position_18 = new BoardPosition(-1, mainPlayer, ships, 18, ganeStart, opponent);
		boardB.add(position_18);

		BoardPosition position_19 = new BoardPosition(-1, mainPlayer, ships, 19, ganeStart, opponent);
		boardB.add(position_19);

		BoardPosition position_20 = new BoardPosition(-1, mainPlayer, ships, 20, ganeStart, opponent);
		boardB.add(position_20);

		BoardPosition position_21 = new BoardPosition(-1, mainPlayer, ships, 21, ganeStart, opponent);
		boardB.add(position_21);

		BoardPosition position_22 = new BoardPosition(-1, mainPlayer, ships, 22, ganeStart, opponent);
		boardB.add(position_22);

		BoardPosition position_23 = new BoardPosition(-1, mainPlayer, ships, 23, ganeStart, opponent);
		boardB.add(position_23);

		BoardPosition position_24 = new BoardPosition(-1, mainPlayer, ships, 24, ganeStart, opponent);
		boardB.add(position_24);
	}

	// getters and setters
	public List<BoardPosition> getBoardB() {
		return boardB;
	}

	public void setBoardB(List<BoardPosition> boardB) {
		this.boardB = boardB;
	}

	public Shoot getShoot() {
		return shoot;
	}

	public void setShoot(Shoot shoot) {
		this.shoot = shoot;
	}

	public boolean isGaneStart() {
		return ganeStart;
	}

	public void setGaneStart(boolean ganeStart) {
		this.ganeStart = ganeStart;
	}

	public PlayerDetailPanel getOpponent() {
		return opponent;
	}

	public void setOpponent(PlayerDetailPanel opponent) {
		this.opponent = opponent;
	}

	public Ship getShips() {
		return ships;
	}

	public void setShips(Ship ships) {
		this.ships = ships;
	}

}
