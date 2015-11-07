package bear.and.seals.models;

import java.awt.Point;
import java.util.Random;

public class Bear extends Entity implements Mover, Stepper {

	private int stepsWaited;
	private int idleTimeAfterEating;

	public Bear(Point point) {
		super(point);
		this.stepsWaited = 0;
	}

	public boolean canEat() {
		return this.idleTimeAfterEating == 0;
	}

	public void eat() {
		this.idleTimeAfterEating = 3;
		this.stepsWaited = 0;
	}

	public boolean shouldMove() {
		// the number of steps the bear waits for a seal to appear at a hole is
		// between 2 and 6
		if (!canEat()) {
			return false;
		}
		if (stepsWaited < 2) {
			return false;
		}
		if (stepsWaited > 6) {
			return true;
		}
		return new Random().nextBoolean();
	}

	@Override
	public void nextStep() {
		if (this.idleTimeAfterEating > 0) {
			this.idleTimeAfterEating--;
		} else {
			this.stepsWaited++;
		}
	}

	@Override
	public void move(Point position) {
		setPosition(position);
	}

	@Override
	public char getBoardRepresentation() {
		return 'B';
	}

}
