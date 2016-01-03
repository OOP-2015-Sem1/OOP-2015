package model;

public class Bomberman extends GameElement {

	private int speed;
	private DirectionType direction;
	private int nrOfSteps;
	private int status, lastStatus; // the current status and the last status of
									// the bomberman
	private boolean alive;

	public Bomberman(int xPosition, int yPosition) throws OutOfMapException {
		super(xPosition, yPosition);
		this.speed = 1; // modify eventually, can have 3 speeds, between 1-3
		this.direction = DirectionType.DOWN; // initial direction
		this.nrOfSteps = 0; // nr of steps made in a certain direction
		this.setAlive(true);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public DirectionType getDirection() {
		return direction;
	}

	public void setDirection(DirectionType direction) {
		this.direction = direction;
	}

	public int getNrOfSteps() {
		return nrOfSteps;
	}

	public void setNrOfSteps(int nrOfSteps) {
		this.nrOfSteps = nrOfSteps;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void dropBomb() {

	}

	public void setLastStatus(int lastStatus) {
		this.lastStatus = lastStatus;
	}

	/*
	 * returns the last status of the bomberman
	 */
	public int getLastStatus() {
		return lastStatus;
	}

	public int setLastStatus() {
		return lastStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void startMovingUp() {
		lastStatus = status;
		status = Constants.STATUS_MOVING_UP;
		setDirection(DirectionType.UP);
		// player = new AnimationPlayer(map.getAnimation(GameMap.ANI_WALK_UP));
		// player.reset();
	}

	/*
	 * the animation for moving down is loaded into the player and the status of
	 * the bomberman is updated
	 */
	public void startMovingDown() {
		lastStatus = status;
		status = Constants.STATUS_MOVING_DOWN;
		setDirection(DirectionType.DOWN);
		// player = new
		// AnimationPlayer(map.getAnimation(GameMap.ANI_WALK_DOWN));
		// player.reset();
	}

	/*
	 * the animation for moving to left is loaded into the player and the status
	 * of the bomberman is updated
	 */
	public void startMovingLeft() {
		lastStatus = status;
		status = Constants.STATUS_MOVING_LEFT;
		setDirection(DirectionType.LEFT);
		// player = new
		// AnimationPlayer(map.getAnimation(GameMap.ANI_WALK_LEFT));
		// player.reset();
	}

	/*
	 * the animation for moving to right is loaded into the player and the
	 * status of the bomberman is updated
	 */
	public void startMovingRight() {
		lastStatus = status;
		status = Constants.STATUS_MOVING_RIGHT;
		setDirection(DirectionType.RIGHT);

		// player = new
		// AnimationPlayer(map.getAnimation(GameMap.ANI_WALK_RIGHT));
		// player.reset();
	}

	/*
	 * the bomberman is not moving
	 */
	public void stay() {
		lastStatus = status;
		status = Constants.STATUS_STANDING;
		// setDirection(DirectionType.DOWN);
	}

	/*
	 * @param st: the new status of the bomberman
	 */
	public void updateStatus(int st) {
		lastStatus = status;
		status = st;
	}

}
