package javasmmr.zoosome.models.animals;

public abstract class Animal {
	public int nrOfLegs;
	public String name;

	public void setNrOfLegs(int nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	public int getNrOfLegs() {
		return nrOfLegs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
