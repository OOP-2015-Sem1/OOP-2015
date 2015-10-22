package javasmmr.zoosome.models.animals;

public class Spider extends Insect {
	public Spider() {
		this(8, "Spider", false, true);
	}

	public Spider(int nrOfLegs, String name, boolean canFly, boolean isDangerous) {
		super(nrOfLegs, name, canFly, isDangerous);
	}

}
