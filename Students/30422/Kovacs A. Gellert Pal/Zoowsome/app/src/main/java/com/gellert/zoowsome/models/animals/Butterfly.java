package com.gellert.zoowsome.models.animals;

import com.gellert.zoowsome.services.factories.Constants;

public class Butterfly extends Insect {
	public Butterfly(String name, int nrOfLegs, boolean canFly, boolean isDangerous, double maintenanceCost,
			double dangerPerc) {
		super(maintenanceCost, dangerPerc);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setCanFly(canFly);
		this.setDangerous(isDangerous);
	}

	public Butterfly() {
		this.setName("Mr Butter");
		this.setNrOfLegs(6);
		this.setCanFly(true);
		this.setDangerous(false);
	}
}
