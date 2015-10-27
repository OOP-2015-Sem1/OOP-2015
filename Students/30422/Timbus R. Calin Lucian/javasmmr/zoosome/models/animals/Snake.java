package javasmmr.zoosome.models.animals;

public class Snake extends Reptile {
	public Snake(boolean laysEggs, int nrOfLegs, String name) {
		this.laysEggs = laysEggs;
		this.nrOfLegs = nrOfLegs;
		this.name = name;
	}

	public Snake() {
		this(true, 0, "Dynamo");
	}

	@Override
	public boolean isItLayingEggs() {
		return laysEggs;
	}

	@Override
	public void setLayingEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
		
	}

	@Override
	public void setNrOfLegs(int nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	@Override
	public int getNrOfLegs() {
		return nrOfLegs;
	}
}
