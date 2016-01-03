package model;

/**
 * 
 * @author Iulia : This class shares the common properties of every game element,
 *         such as the bomberman, the bricks from the wall, monsters, bombs,
 *         etc. Every element has a position with the coordinates (xPosition,
 *         yPosition). Because we treat our game board as a matrix, to implement
 *         things easily, we also need for every position some matrix
 *         coordinates (rowPosition, columnPosition)
 * 
 */
abstract public class GameElement {

	protected int xPosition;
	protected int yPosition;
	protected int rowPosition;
	protected int columnPosition;

	public GameElement(int xPosition, int yPosition) throws OutOfMapException {

		if (xPosition < -1 || xPosition >= Constants.BLOCK_COUNT_X * Constants.GAME_ELEMENT_SIZE_X || yPosition < -1
				|| yPosition >= Constants.GAME_ELEMENT_SIZE_Y * Constants.GAME_ELEMENT_SIZE_Y) {
			throw new OutOfMapException(xPosition, yPosition);
		}
		setxPosition(xPosition);
		setyPosition(yPosition);
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
		this.columnPosition = (xPosition) / Constants.GAME_ELEMENT_SIZE_X;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
		this.rowPosition = (yPosition) / Constants.GAME_ELEMENT_SIZE_Y;
	}

	public int getRowPosition() {
		return rowPosition;
	}

	public int getColumnPosition() {
		return columnPosition;
	}

}
