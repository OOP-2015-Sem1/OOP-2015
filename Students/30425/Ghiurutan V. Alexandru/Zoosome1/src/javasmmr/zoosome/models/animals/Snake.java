package javasmmr.zoosome.models.animals;

public class Snake extends Reptile {
	public Snake() {
		this(0, "Snake", true);
	}

	public Snake(int nrOfLegs, String name, boolean laysEggs) {
		super(nrOfLegs, name, laysEggs);
	}

}
