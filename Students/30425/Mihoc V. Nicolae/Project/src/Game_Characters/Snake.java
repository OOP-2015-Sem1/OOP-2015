package Game_Characters;

import java.awt.Point;
import java.util.LinkedList;

import Constants.Direction;
import Constants.TileType;
import Panels.BoardPanel;

public class Snake {
	private boolean canGoThroughWalls;
	private LinkedList<Direction> directions;
	private LinkedList<Point> snake;
	private BoardPanel board;
	private static final int MIN_SNAKE_LENGTH = 3;
	

	
	public TileType updateSnake() {
		
		Direction direction = directions.peekFirst();
		Point head = new Point(snake.peekFirst());
		switch (direction) {
		case UP:
			head.y--;
			break;

		case DOWN:
			head.y++;
			break;

		case LEFT:
			head.x--;
			break;

		case RIGHT:
			head.x++;
			break;
		}
		if (canGoThroughWalls == false) {
			if (head.x < 0 || head.x >= BoardPanel.ROW || head.y < 0 || head.y >= BoardPanel.COL) {
				return TileType.SnakeBody;
			}
		} else {
			if (head.x < 0) {
				head.x = BoardPanel.ROW - 1;
			}
			if (head.y < 0) {
				head.y = BoardPanel.COL - 1;
			}
			if (head.x > BoardPanel.ROW - 1) {
				head.x = 0;
			}
			if (head.y > BoardPanel.COL - 1) {
				head.y = 0;
			}
		}

		TileType old = board.getTile(head.x, head.y);
		if (old != TileType.Apple && snake.size() > MIN_SNAKE_LENGTH) {
			Point tail = snake.removeLast();
			board.setTile(tail, null);
			old = board.getTile(head.x, head.y);
		}

		if (old != TileType.SnakeBody) {
			board.setTile(snake.peekFirst(), TileType.SnakeBody);
			snake.push(head);
			board.setTile(head, TileType.SnakeHead);
			if (directions.size() > 1) {
				directions.poll();
			}
		}

		return old;
	}
	public Direction getDirection() {
		return directions.peek();
	}
}
