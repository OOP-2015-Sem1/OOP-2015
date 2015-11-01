package javasmmr.zoowsome.models.animals;

public class Ostrich extends Bird {
	public Ostrich(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(2);
		setName("FunntOstrich");
		setMigrates(false);
		setAvgFlightAltitude(5);
	}

	public Ostrich(int nrOfLegs, String ostrichName, int flightAltitude, boolean migrates, double maintCost, double dangerP) {
		super(nrOfLegs, ostrichName, flightAltitude, migrates, maintCost, dangerP);

	}

}
