package javasmmr.zoosome.models.animals;

public class Turtle extends Reptile {
	public Turtle() {
		this(4, "Turtle", true);
	}

	public Turtle(int nrOfLegs, String name, boolean laysEggs) {
		super(nrOfLegs, name, laysEggs);
	}
}
