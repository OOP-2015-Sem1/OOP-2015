package fluffy.the.cat.io;

import static fluffly.the.cat.game.BoardConfiguration.MAX_VIEW_DISTANCE;

import fluffly.the.cat.game.BoardConfiguration;

public abstract class AbstractBoardPrinter implements BoardPrinter {
	
	protected boolean withinRangeOfFluffy(int i, int j,
			BoardConfiguration boardConfiguration) {
		int fluffyX = boardConfiguration.getFluffy().getPosition().x;
		int fluffyY = boardConfiguration.getFluffy().getPosition().y;

		double dist = Math.sqrt(Math.pow(fluffyX - i, 2)
				+ Math.pow(fluffyY - j, 2));

		return dist < MAX_VIEW_DISTANCE;
	}
}
