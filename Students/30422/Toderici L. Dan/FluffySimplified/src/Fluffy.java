
public class Fluffy {
	private int x;
	private int y;

	public Fluffy(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getFluffyX() {
		return x;
	}

	public int getFluffyY() {
		return y;
	}

	public void setFluffyX(int x) {
		this.x = x;
	}

	public void setFluffyY(int y) {
		this.y = y;
	}

	public static void main(String[] args) {
		new GameUI();
	}
}
