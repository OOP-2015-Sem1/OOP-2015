package javasmmr.zoowsome.models.animals;

public class Lizard extends Reptile {

	public Lizard(int nrLegs, String name, boolean laysEggs, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setLaysEggs(laysEggs);
	}

	public Lizard() {
		this(4, "Lizard", true, 5.0, 0.3);
	}
}
