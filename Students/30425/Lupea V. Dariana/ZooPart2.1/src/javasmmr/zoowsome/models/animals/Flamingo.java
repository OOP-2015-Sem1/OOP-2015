package javasmmr.zoowsome.models.animals;

public class Flamingo extends Bird {

	public Flamingo(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(2);
		setName("Flamingo");
		setMigrates(false);
		setAvgFlightAltitude(80);
	}

	public Flamingo(int flamingoLegs, String flamingoName, int flightAltitude, boolean migrates, double maintCost, double dangerP) {
		super(flamingoLegs, flamingoName, flightAltitude, migrates, maintCost, dangerP);

	}
}
