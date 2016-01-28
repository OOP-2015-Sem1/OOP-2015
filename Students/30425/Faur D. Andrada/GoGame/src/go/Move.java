package go;

import java.awt.*;
import java.util.List;

import javax.swing.JOptionPane;

//import javax.swing.JOptionPane;

public class Move {
	Stone lastMove;

	Move(Stone stone, Point point) {
		if (stone.equals(Stone.vacant))
			throw new RuntimeException("bad move");
		this.stone = stone;
		this.point = point;
	}

	Move(Stone stone, int x, int y) {
		this(stone, new Point(x, y));
	}

	void make(MutableBoard board) {
		board.setAt(point.x, point.y, stone);
		lastMove = stone;
		List<List<Block>> blockLists = Block.findBlocks(board);
		List<Block> capturedBlocks = Block.capturedStones(blockLists);
		if (capturedBlocks != null && capturedBlocks.size() > 0) {
			Block fromThisMove = null;
			for (Block block : capturedBlocks)
				if (block.points.contains(point) && block.points.size() == 1) {
					fromThisMove = block;
					break;
				}
			if (fromThisMove != null)
				capturedBlocks.remove(fromThisMove);
			for (Block block : capturedBlocks) {
				captured = capturedBlocks;
				for (Point point2 : block.points)
					board.setAt(point2.x, point2.y, Stone.vacant);
			}
		}
	}

	void unmake(MutableBoard board) {
		if (captured != null)
			for (Block block : captured) {
				Stone who = block.color();
				for (Point point : block.points)
					board.setAt(point.x, point.y, who);
			}
		board.setAt(point.x, point.y, Stone.vacant);
	}

	private boolean passedPreviously;

	void pass(MutableBoard board) {
		if (passedPreviously) {
			JOptionPane.showMessageDialog(null, "Game over.");

		}
		lastMove = Stone.vacant;
		passedPreviously = true;
	}

	final Point point;
	final Stone stone;
	List<Block> captured;

}
