package javasmmr.zoowsome.models.animals;

public abstract class Insect extends Animal {

	public Insect(double maintenance, double damage) {
		super(maintenance, damage);
		// TODO Auto-generated constructor stub
	}

	private boolean canFly;
	private boolean isDangerous;

	public boolean getCanFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public boolean getIsDangerous() {
		return isDangerous;
	}

	public void setIsDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}

}
