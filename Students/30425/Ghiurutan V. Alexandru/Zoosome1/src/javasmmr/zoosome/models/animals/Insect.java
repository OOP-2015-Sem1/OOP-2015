package javasmmr.zoosome.models.animals;

abstract public class Insect extends Animal {
	private boolean canFly;
	private boolean isDangerous;

	public Insect(int nrOfLegs, String name, boolean canFly, boolean isDangerous) {
		super(nrOfLegs, name);
		this.setCanFly(canFly);
		this.setIsDangerous(isDangerous);
	}

	public boolean isFlying() {
		return this.canFly;
	}

	public boolean isDangerous() {
		return this.isDangerous;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public void setIsDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}
}
