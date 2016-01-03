package javasmmr.zoowsome.models.animals;

public class Penguin extends Bird {

	public Penguin(int nrLegs, String name, boolean migrates, int avgFlightAltitude, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setMigrates(migrates);
		setAvgFlightAltitude(avgFlightAltitude);
	}

	public Penguin() {
		this(2, "Penguin", false, 0, 3.5, 0.4);
	}

}
