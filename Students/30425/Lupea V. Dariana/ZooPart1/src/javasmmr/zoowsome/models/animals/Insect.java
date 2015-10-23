package javasmmr.zoowsome.models.animals;

abstract public class Insect extends Animal {
	private boolean canFly;
	private boolean isDangerous;

	public boolean isCanFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public boolean isDangerous() {
		return isDangerous;
	}

	public void setDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}

	public Insect() {
		setNrOfLegs(nrOfLegs);
		setName(name);
		setCanFly(canFly);
		setDangerous(isDangerous);
	}

	public Insect(int nrOfLegs, String name, boolean canFly, boolean isDangerous) {
		setNrOfLegs(nrOfLegs);
		setName(name);
		setCanFly(canFly);
		setDangerous(isDangerous);

	}

}
