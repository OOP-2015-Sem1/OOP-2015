package javasmmr.zoosome.models.animals;

abstract public class Animal {
	private int nrOfLegs;
	private String name;

	public Animal() {

	}

	public Animal(int nrOfLegs, String name) {
		this.setNrOfLegs(nrOfLegs);
		this.setName(name);
	}

	public int getNrOfLegs() {
		return this.nrOfLegs;
	}

	public void setNrOfLegs(int nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
