package javasmmr.zoowsome.models.animals;

public class Lizard extends Reptile {

	public Lizard(int nrLegs, String name, boolean laysEggs) {
		setNrOfLegs(nrLegs);
		setName(name);
		setLaysEggs(laysEggs);
	}

	public Lizard() {
		this(4, "Lizard", true);
	}
}
