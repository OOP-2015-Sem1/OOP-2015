package javasmmr.zoosome.models.animals;

/**
 * 
 * @author Alexandru This is the parent class for the whole package. It is
 *         abstract ,so it may have abstract methods.
 */
abstract public class Animal implements Killer {
	private int nrOfLegs;
	private String name;
	// represents the number of hours per week an animal require attention from
	// one or more employees.
	// Its value ranges from 0.1 to 0.8.
	private final double maintenanceCost;
	// This field shows how dangerous an animal could be in percents.[0,1]
	private final double dangerPerc;
	// Field that shows if our specific animal is taken care of.
	// It is set to false by default.
	private boolean takenCareOf;

	// Constructor with parameters.
	public Animal(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf) {
		this.setNrOfLegs(nrOfLegs);
		this.setName(name);
		this.setTakenCareOf(takenCareOf);
		this.maintenanceCost = maintenanceCost;
		this.dangerPerc = dangerPerc;
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

	public double getMaintenanceCost() {
		return this.maintenanceCost;
	}

	public double getDangerPerc() {
		return this.dangerPerc;
	}

	public boolean isTakenCareOf() {
		return this.takenCareOf;
	}

	public void setTakenCareOf(boolean takenCareOf) {
		this.takenCareOf = takenCareOf;
	}
//Twist 1
	public double getPredisposition() {
		return 0.0;
	}
}
