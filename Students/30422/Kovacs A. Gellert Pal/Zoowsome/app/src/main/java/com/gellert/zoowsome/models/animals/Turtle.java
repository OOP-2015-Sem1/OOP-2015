package com.gellert.zoowsome.models.animals;


import com.gellert.zoowsome.services.factories.Constants;

public class Turtle extends Reptile {
	public Turtle(String name, int nrOfLegs, boolean laysEggs, double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setLaysEggs(laysEggs);
	}
	
	public Turtle() {
		this.setName("T-Man");
		this.setNrOfLegs(4);
		this.setLaysEggs(true);
	}
}