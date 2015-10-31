package javasmmr.zoosome.models.animals;

public class Seal extends Aquatic {
	public Seal() {
		this(0, "Seal", 0.3, 0.2, false, 1500, WaterType.SALTWATER);
	}

	public Seal(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
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
