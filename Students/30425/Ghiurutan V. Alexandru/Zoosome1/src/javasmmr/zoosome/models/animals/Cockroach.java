package javasmmr.zoosome.models.animals;

public class Cockroach extends Insect {
	public Cockroach() {
		this(6, "Cockroach", 0.1, 0.1, false, true, true);
	}

	public Cockroach(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean canFly, boolean isDangerous) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, canFly, isDangerous);
	}

	// Interface method implementation.
	// It returns true if the entity interacting with our animal gets killed.
	@Override
	public boolean kill() {
		double percent = Math.random() + getPredisposition();
		return (percent < this.getDangerPerc());

	}
}
