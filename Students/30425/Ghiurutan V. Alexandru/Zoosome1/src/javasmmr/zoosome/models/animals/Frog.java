package javasmmr.zoosome.models.animals;

public class Frog extends Aquatic {
	public Frog() {
		this(4, "Frog", 0.2, 0.2, false, 1, WaterType.FRESHWATER);
	}

	public Frog(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			int avgSwimDepth, WaterType waterType) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, avgSwimDepth, waterType);
	}

	// Interface method implementation.
	// It returns true if the entity interacting with our animal gets killed.
	@Override
	public boolean kill() {
		double percent = Math.random();
		return (percent < this.getDangerPerc());

	}
}
