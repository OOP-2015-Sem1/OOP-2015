package javasmmr.zoowsome.models.animals;

public class Parrot extends Bird {

	public Parrot(int nrLegs, String name, boolean migrates, int avgFlightAltitude, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setMigrates(migrates);
		setAvgFlightAltitude(avgFlightAltitude);
	}

	public Parrot() {
		this(2, "Parrot", false, 20, 8.0, 0);
	}

}
