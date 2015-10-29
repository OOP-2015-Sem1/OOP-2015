package javasmmr.zoowsome.models.animals;

public class Stork extends Bird {

	public Stork(Double maintenance,double dangerPerc) {
		super(maintenance,dangerPerc);
		setName("Stork");
		setNrOfLegs(2);
		setMigrates(true);
		setAvgFlightAltitude(343);

	}

}
