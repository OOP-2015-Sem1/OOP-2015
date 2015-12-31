package minesweeperAI;

public class AiTiles {
	public boolean opened;
	public boolean flagged;
	public	boolean maybe;
	public	int[] maybeId = new int[8];
	public	int value;
	
	public	int flaggedAround;
	public	int closedAround;

	public	int tileX;
	public	int tileY;
	
	public	int id = ( 30 * tileY ) + tileX;
	
	
}
