package javasmmr.zoosome.models.animals;

public class Vulture extends Bird {
	public Vulture() {
		this(2, "Vulture", false, 11000);
	}

	public Vulture(int nrOfLegs, String name, boolean migrates, int avgFlightAltitude) {
		super(nrOfLegs, name, migrates, avgFlightAltitude);
	}

}
