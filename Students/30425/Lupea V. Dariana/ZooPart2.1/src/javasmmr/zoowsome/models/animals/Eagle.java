package javasmmr.zoowsome.models.animals;

public class Eagle extends Bird {

	public Eagle(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(2);
		setName("EagleJ");
		setMigrates(true);
		setAvgFlightAltitude(100);
	}

	public Eagle(int nrOfLegs, String eagleName, int flightAltitude, boolean migrates, double maintCost,
			double dangerP) {
		super(nrOfLegs, eagleName, flightAltitude, migrates, maintCost, dangerP);

	}

}
