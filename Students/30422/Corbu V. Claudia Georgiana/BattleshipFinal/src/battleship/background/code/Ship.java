package battleship.background.code;


public class Ship {
	private int lengthOfShip;
	private boolean placedHorizontally;


	public Ship(int lengthOfShip, boolean placedHorizontally) {
		this.lengthOfShip = lengthOfShip;
		this.placedHorizontally = placedHorizontally;
	}

	public int getLengthOfShip() {
		return lengthOfShip;
	}

	public boolean isPlacedHorizotally() {
		return placedHorizontally;
	}
	
}
