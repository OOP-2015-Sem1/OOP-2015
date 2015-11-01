package javasmmr.zoosome.models.animals;

public class Penguin extends Bird {
	public Penguin() {
		this(2, "Penguin", 0.4, 0.2, false, false, 0);
	}

	public Penguin(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
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
