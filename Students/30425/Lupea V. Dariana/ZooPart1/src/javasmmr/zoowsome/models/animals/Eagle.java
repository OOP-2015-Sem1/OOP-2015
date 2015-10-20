package javasmmr.zoowsome.models.animals;

public class Eagle extends Bird {

	public Eagle() {
		setNrOfLegs(2);
		setName("EagleJ");
		setMigrates(true);
		setAvgFlightAltitude(100);
	}

	public Eagle(int nrOfLegs, String eagleName, int flightAltitude, boolean migrates) {
		super(nrOfLegs, eagleName, flightAltitude, migrates);

	}

}
