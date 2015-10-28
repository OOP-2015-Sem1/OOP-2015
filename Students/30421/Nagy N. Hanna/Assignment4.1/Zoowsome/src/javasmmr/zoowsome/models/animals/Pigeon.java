package javasmmr.zoowsome.models.animals;

public class Pigeon extends Bird{

	
	public Pigeon(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Pigeon");
		setNrOfLegs(2);
		setMigrates(false);
		setAvgFlightAltitude(123);

	}
}
