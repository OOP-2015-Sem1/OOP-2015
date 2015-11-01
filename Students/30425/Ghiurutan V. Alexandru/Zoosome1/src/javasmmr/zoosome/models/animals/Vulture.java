package javasmmr.zoosome.models.animals;

public class Vulture extends Bird {
	public Vulture() {
		this(2, "Vulture", 0.4, 0.3, false, false, 11000);
	}

	public Vulture(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean migrates, int avgFlightAltitude) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, migrates, avgFlightAltitude);
	}

	// Interface method implementation.
	// It returns true if the entity interacting with our animal gets killed.
	@Override
	public boolean kill() {
		double percent = Math.random();
		return (percent < this.getDangerPerc());

	}
}
