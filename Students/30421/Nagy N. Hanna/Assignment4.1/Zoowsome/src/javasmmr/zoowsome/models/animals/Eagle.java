package javasmmr.zoowsome.models.animals;

public class Eagle extends Bird {

	public Eagle(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Eagle");
		setNrOfLegs(2);
		setMigrates(true);
		setAvgFlightAltitude(234);

	}

}
