package battleship.models;

public class Test {
	
	private int shipHeight = 10;
	private  int shipWidth = 30;
	private boolean drawEnabled = false;
	private final int HORIZONTAL = 0;
	private int position;
	private final int xLeftLimit = 0, xRightLimit = 100; // the sizes of the play board
	private final int yUpLimit = 0, yDownLimit = 100;
	
	private boolean xIsBetweenLimits(int x) {
		if(position == HORIZONTAL) {
			if(x - shipWidth / 2 >= xLeftLimit && x + shipWidth/2 <= xRightLimit)
				return true;
		}
		else{
			if(x - shipHeight /2 >= xLeftLimit && x + shipHeight/2 <= xRightLimit)
				return true;
		}
		return false;
	}
	
	private boolean yIsBetweenLimits(int y) {
		if(position == HORIZONTAL) {
			if(y - shipHeight / 2 >= yUpLimit && y + shipHeight/2 <= yDownLimit)
				return true;
		}
		else {
			if(y - shipWidth / 2 >= yUpLimit && y + shipWidth/2 <= yDownLimit)
				return true;
		}
		return false;
	}
	
	
	private boolean coordOnBoard(int x, int y) {
		return xIsBetweenLimits(x) && yIsBetweenLimits(y);
	}
	
	
	public static void main(String[] args) {
		
		System.out.println();
		
		
	}
	

}
