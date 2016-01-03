package model;

public class Wall extends GameElement {

	private boolean breakable; // if the wall can is affected or not by the
								// explosion of a bomb
	private boolean hasBonus; // if after it is crashed, contains or not a
								// power-up
	private BonusType bonusType; // type of power-up
	private boolean destroyedWall;

	public Wall(int xPosition, int yPosition, boolean breakable, boolean hasBonus, BonusType bonusType)
			throws OutOfMapException {
		super(xPosition, yPosition);
		this.breakable = breakable;
		this.hasBonus = hasBonus;
		this.bonusType = bonusType;
		this.destroyedWall = false; // initially, not destroyed
	}

	public boolean isBreakable() {
		return breakable;
	}

	public void setBreakable(boolean breakable) {
		this.breakable = breakable;
	}

	public boolean isHasBonus() {
		return hasBonus;
	}

	public void setHasBonus(boolean hasBonus) {
		this.hasBonus = hasBonus;
	}

	public BonusType getBonusType() {
		return bonusType;
	}

	public void setBonusType(BonusType bonusType) {
		this.bonusType = bonusType;
	}

	public boolean isDestroyedWall() {
		return destroyedWall;
	}

	public void setDestroyedWall(boolean destroyedWall) {
		this.destroyedWall = destroyedWall;
	}

}
