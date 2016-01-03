package model;

public class OutOfMapException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfMapException() {
		super();
	}

	public OutOfMapException(int xPos, int yPos) {
		super("Position out of map : (" + xPos + ", " + yPos + ")");
	}
}
