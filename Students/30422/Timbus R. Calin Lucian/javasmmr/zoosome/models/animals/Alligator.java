package javasmmr.zoosome.models.animals;

public class Alligator extends Reptile {
	public Alligator(boolean laysEggs, int nrOfLegs, String name) {
		this.laysEggs = laysEggs;
		this.nrOfLegs = nrOfLegs;
		this.name = name;
	}

	public Alligator() {
		this(true, 6, "Crux");
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
