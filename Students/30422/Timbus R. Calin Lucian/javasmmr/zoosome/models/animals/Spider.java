package javasmmr.zoosome.models.animals;

public class Spider extends Insect {

	public Spider(boolean canFly, boolean isDangerous, int nrOfLegs, String name) {
		this.canFly = canFly;
		this.isDangerous = isDangerous;
		this.nrOfLegs = nrOfLegs;
		this.name = name;
	}

	public Spider() {
		this(true, true, 8, "Spiky");
	}

	@Override
	public void setCanFly(boolean canFly) {
		this.canFly = canFly;

	}

	@Override
	public void setIsDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;

	}

	@Override
	public boolean canItFly() {

		return canFly;
	}

	@Override
	public boolean isItDangerous() {
		return isDangerous;
	}

}
