package Game;

public interface GameInterface {
	public boolean isPossibleX(int[][] game, int y, int number);
	public boolean isPossibleY(int[][] game, int x, int number);
	public int getBlockIndex(int i);
	public boolean isPossibleBlock(int[][] game, int x, int y, int number);

}
