package javasmmr.zoowsome.models.animals;

public class Penguin extends Bird {

	public Penguin(int nrLegs, String name, boolean migrates, int avgFlightAltitude) {
		setNrOfLegs(nrLegs);
		setName(name);
		setMigrates(migrates);
		setAvgFlightAltitude(avgFlightAltitude);
	}

	public Penguin() {
		this(2, "Penguin", false, 0);
	}

}
