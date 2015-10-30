package javasmmr.zoowsome.models.animals;

abstract public class Animal implements Killer {

	int nrOfLegs;
	String name;
	final double maintenanceCost;
	final double dangerPerc;
	boolean takenCareOf;

	public int getNrOfLegs() {
		return nrOfLegs;
	}

	public void setNrOfLegs(int nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMaintenanceCost() {
		return maintenanceCost;
	}

	public double getDangerPerc() {
		return dangerPerc;
	}

	public boolean getTakenCareOf() {
		return takenCareOf;
	}

	public void setTakenCareOf(boolean careT) {
		this.takenCareOf = careT;
	}

	public Animal(double maintenanceCost, double dangerPerc) {
		this.maintenanceCost = maintenanceCost;
		this.dangerPerc = dangerPerc;
	}

	public boolean kill() {
		double random = Math.random(); // generate random number between 0 and 1
		if (random < dangerPerc) {
			return true;
		} else {
			return false;
		}
	}

}

interface killer {
	public boolean kill();
}
