package javasmmr.zoowsome.models.animals;

public class Boa extends Reptile {

	public Boa(int nrLegs, String name, boolean laysEggs) {
		setNrOfLegs(nrLegs);
		setName(name);
		setLaysEggs(laysEggs);
	}

	public Boa() {
		this(0, "Boa The Snake", false);
	}

}
