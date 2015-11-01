package javasmmr.zoosome.models.animals;

public class Butterfly extends Insect {
	public Butterfly() {
		this(6, "Butterfly", 0.1, 0.0, false, true, false);
	}

	public Butterfly(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
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
