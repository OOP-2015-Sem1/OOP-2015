package javasmmr.zoowsome.models.animals;

public class Ostrich extends Bird {
	public Ostrich() {
		setNrOfLegs(2);
		setName("FunntOstrich");
		setMigrates(false);
		setAvgFlightAltitude(5);
	}

	public Ostrich(int nrOfLegs, String ostrichName, int flightAltitude, boolean migrates) {
		super(nrOfLegs, ostrichName, flightAltitude, migrates);

	}

}
