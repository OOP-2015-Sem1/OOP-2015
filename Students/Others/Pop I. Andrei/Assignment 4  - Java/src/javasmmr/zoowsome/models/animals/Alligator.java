package javasmmr.zoowsome.models.animals;

public class Alligator extends Reptile {

	public Alligator(int nrLegs, String name, boolean laysEggs, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setLaysEggs(true);
	}

	public Alligator() {
		this(4, "Alligator", true, 0.3, 0.8);
	}
}
