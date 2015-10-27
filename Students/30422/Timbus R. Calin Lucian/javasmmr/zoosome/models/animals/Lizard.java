package javasmmr.zoosome.models.animals;

public class Lizard extends Reptile {

	public Lizard(boolean laysEggs, int nrOfLegs, String name) {
		this.laysEggs = laysEggs;
		this.nrOfLegs = nrOfLegs;
		this.name = name;
	}

	public Lizard() {
		this(true, 6, "Joanna");
	}

	@Override
	public boolean isItLayingEggs() {
		return laysEggs;
	}

	@Override
	public void setLayingEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
	}
}
