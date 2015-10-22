package javasmmr.zoosome.models.animals;

abstract public class Reptile extends Animal {
	private boolean laysEggs;

	public Reptile(int nrOfLegs, String name, boolean laysEggs) {
		super(nrOfLegs, name);
		this.setLaysEggs(laysEggs);
	}

	public boolean isLayingEggs() {
		return this.laysEggs;
	}

	public void setLaysEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
	}
}
