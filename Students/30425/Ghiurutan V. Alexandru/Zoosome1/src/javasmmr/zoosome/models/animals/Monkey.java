package javasmmr.zoosome.models.animals;

public class Monkey extends Mammal {
	public Monkey() {
		this(4, "Monkey", 0.5, 0.3, false, 39.0F, 95.0F);
	}

	public Monkey(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			float normalBodyTemp, float pereBodyHair) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, normalBodyTemp, pereBodyHair);
	}

	// Interface method implementation.
	// It returns true if the entity interacting with our animal gets killed.
	@Override
	public boolean kill() {
		double percent = Math.random();
		return (percent < this.getDangerPerc());

	}
}
