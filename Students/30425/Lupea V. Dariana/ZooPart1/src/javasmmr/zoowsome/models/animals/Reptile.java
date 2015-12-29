package javasmmr.zoowsome.models.animals;

abstract public class Reptile extends Animal {
	

	private boolean laysEggs;

	public boolean isLaysEggs() {
		return laysEggs;
	}

	public void setLaysEggs(boolean eggs) {
		this.laysEggs = eggs;
	}

	public Reptile() {
		setNrOfLegs(nrOfLegs);
		setName(name);
		setLaysEggs(laysEggs);
	}

	public Reptile(int nrOfLegs, String name, boolean someEggs) {
		setNrOfLegs(nrOfLegs);
		setName(name);
		setLaysEggs(someEggs);
	}
}
