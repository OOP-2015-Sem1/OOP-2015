package Game_Characters;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

import Constants.TileType;
import Panels.BoardPanel;

public class Fruit {
	private Random random;
	private LinkedList<Point> snake;
	private BoardPanel board;
	public void spawnFruit() {

		int index = random.nextInt(BoardPanel.COL * BoardPanel.ROW - snake.size());

		int freeFound = -1;
		for (int x = 0; x < BoardPanel.COL; x++) {
			for (int y = 0; y < BoardPanel.ROW; y++) {
				TileType type = board.getTile(x, y);
				if (type == null || type == TileType.Apple) {
					if (++freeFound == index) {
						board.setTile(x, y, TileType.Apple);
						break;
					}
				}
			}
		}
	}
}
