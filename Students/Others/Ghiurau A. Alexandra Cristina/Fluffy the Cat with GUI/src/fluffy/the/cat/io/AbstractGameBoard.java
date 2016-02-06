package fluffy.the.cat.io;


import fluffly.the.cat.game.BoardConfiguration;
import fluffly.the.cat.game.Constants;


public abstract class AbstractGameBoard implements BoardPrinter{

	public abstract void print(BoardConfiguration boardConfiguration);
	
	protected boolean withinRangeOfFluffy(int i, int j,
			BoardConfiguration boardConfiguration) {
		int fluffyX = boardConfiguration.getFluffy().getPosition().x;
		int fluffyY = boardConfiguration.getFluffy().getPosition().y;

		double dist = Math.sqrt(Math.pow(fluffyX - i, 2)
				+ Math.pow(fluffyY - j, 2));

		return dist < Constants.MAX_VIEW_DISTANCE;
	}
	
}
