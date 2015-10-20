package javasmmr.zoowsome.models.animals;

public class Flamingo extends Bird {

	public Flamingo() {
		setNrOfLegs(2);
		setName("Flamingo");
		setMigrates(false);
		setAvgFlightAltitude(80);
	}

	public Flamingo(int flamingoLegs, String flamingoName, int flightAltitude, boolean migrates) {
		super(flamingoLegs, flamingoName, flightAltitude, migrates);

	}
}
