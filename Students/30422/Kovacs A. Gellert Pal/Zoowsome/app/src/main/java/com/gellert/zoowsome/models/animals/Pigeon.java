package com.gellert.zoowsome.models.animals;

import com.gellert.zoowsome.services.factories.Constants;

public class Pigeon extends Bird {
	public Pigeon(String name, int nrOfLegs, boolean migrates, int avgFlightAltitude, double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setMigrates(migrates);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}
	
	public Pigeon() {
		this.setName("Piggy");
		this.setNrOfLegs(2);
		this.setMigrates(false);
		this.setAvgFlightAltitude(15);
	}
}