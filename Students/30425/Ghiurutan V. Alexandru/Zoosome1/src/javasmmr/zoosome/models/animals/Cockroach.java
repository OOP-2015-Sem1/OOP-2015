package javasmmr.zoosome.models.animals;

public class Cockroach extends Insect {
	public Cockroach() {
		this(6, "Cockroach", true, true);
	}

	public Cockroach(int nrOfLegs, String name, boolean canFly, boolean isDangerous) {
		super(nrOfLegs, name, canFly, isDangerous);
	}
}
