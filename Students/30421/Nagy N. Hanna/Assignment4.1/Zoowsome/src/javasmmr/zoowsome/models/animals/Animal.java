package javasmmr.zoowsome.models.animals;

import javasmmr.zoowsome.models.Killer;

public abstract class Animal implements Killer {

	private boolean takenCareOf = false;

	private Integer nrOfLegs;
	private String name;
	// hold how many hours per week will this animal require attention from
	// one (or more) employees of the zoo
	private final Double maintenanceCost;
	private final double dangerPerc;

	public boolean isTakenCareOf() {
		return takenCareOf;
	}

	public void setTakenCareOf(boolean takenCareOf) {
		this.takenCareOf = takenCareOf;
	}

	public boolean kill() {
		double rand = (double) Math.random() * 1;
		if (rand < this.dangerPerc) {
			return true;
		}
		return false;
	}

	public Animal(Double maintenanceCost, double dangerPerc) {
		this.maintenanceCost = maintenanceCost;
		this.dangerPerc = dangerPerc;
	}

	public Double getMaintenanceCost() {
		return maintenanceCost;
	}

	public Integer getNrOfLegs() {
		return nrOfLegs;
	}

	public String getName() {
		return name;
	}

	public void setNrOfLegs(Integer nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	public void setName(String name) {
		this.name = name;
	}

}
