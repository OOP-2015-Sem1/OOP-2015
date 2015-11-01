package javasmmr.zoosome.models.animals;

public class Turtle extends Reptile {
	public Turtle() {
		this(4, "Turtle", 0.3, 0.1, false, true);
	}

	public Turtle(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean laysEggs) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, laysEggs);
	}

	// Interface method implementation.
	// It returns true if the entity interacting with our animal gets killed.
	@Override
	public boolean kill() {
		double percent = Math.random();
		return (percent < this.getDangerPerc());

	}
}
