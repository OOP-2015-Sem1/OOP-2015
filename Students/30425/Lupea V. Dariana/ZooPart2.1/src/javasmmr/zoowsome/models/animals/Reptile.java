package javasmmr.zoowsome.models.animals;

abstract public class Reptile extends Animal {

	private boolean laysEggs;

	public boolean isLaysEggs() {
		return laysEggs;
	}

	public void setLaysEggs(boolean eggs) {
		this.laysEggs = eggs;
	}

	public Reptile(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(nrOfLegs);
		setName(name);
		setLaysEggs(laysEggs);
	}

	public Reptile(int nrOfLegs, String name, boolean someEggs, double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(nrOfLegs);
		setName(name);
		setLaysEggs(someEggs);
	}
}
