package javasmmr.zoosome.models.animals;

public class Dolphin extends Aquatic {
	// In this classes the construction of an object is done through a single
	// constructor.
	public Dolphin() {
		this(0, "Dolphin", 0.5, 0.2, false, 50, WaterType.SALTWATER);
	}

	public Dolphin(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
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
