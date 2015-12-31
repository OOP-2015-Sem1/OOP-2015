package minesweeperGame;

import minesweeperAI.AiTiles;

public class Main {
	public static Tiles[][] field = new Tiles[GamePlay.fieldLenght][GamePlay.fieldDepth];
	public static AiTiles[][] aiField = new AiTiles[GamePlay.fieldLenght][GamePlay.fieldDepth];


	public static void main(String[] args) {
		int i, j;

		for (j = 0; j < GamePlay.fieldDepth; j++) {
			for (i = 0; i < GamePlay.fieldLenght; i++) {
				field[i][j] = new Tiles(i, j);
			}
		}
		GamePlay.playGame();
	}
}
