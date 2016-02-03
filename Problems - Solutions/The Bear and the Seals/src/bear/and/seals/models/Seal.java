package bear.and.seals.models;

import java.awt.Point;
import java.util.Random;

public class Seal extends Entity implements Mover, Swimmer, Stepper {

	private boolean isUnderwater;
	private int stepsUntilMandatoryBreath;

	public Seal(Point point) {
		super(point);
		this.stepsUntilMandatoryBreath = 5;
		this.isUnderwater = true;
	}

	@Override
	public boolean swim() {
		if (stepsUntilMandatoryBreath == 0) {
			return false;
		} else {
			return new Random().nextBoolean();
		}
	}

	@Override
	public void nextStep() {
		this.stepsUntilMandatoryBreath--;
	}

	@Override
	public void move(Point position) {
		setPosition(position);
	}

	@Override
	public char getBoardRepresentation() {
		return isUnderwater() ? 's' : 'S';
	}

	public boolean isUnderwater() {
		return isUnderwater;
	}

	public void setUnderwater(boolean isUnderwater) {
		this.isUnderwater = isUnderwater;
	}

	public int getStepsUntilMandatoryBreath() {
		return stepsUntilMandatoryBreath;
	}

	public boolean isDying() {
		return (isUnderwater && (stepsUntilMandatoryBreath == 0));
	}

}
