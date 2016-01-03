package javasmmr.zoowsome.models.animals;

abstract public class Bird extends Animal {

	private boolean migrates;
	private int avgFlightAltitude;

	public boolean isMigrates() {
		return migrates;
	}

	public void setMigrates(boolean migrates) {
		this.migrates = migrates;
	}

	public int getAvgFlightAltitude() {
		return avgFlightAltitude;
	}

	public void setAvgFlightAltitude(int avgFlightAltitude) {
		this.avgFlightAltitude = avgFlightAltitude;
	}

	public Bird(double maintCost, double dangerP)
	{
		super(maintCost, dangerP);
		setNrOfLegs(nrOfLegs);
		setName(name);
		setMigrates(migrates);
		setAvgFlightAltitude(avgFlightAltitude);
	}

	public Bird(int nrLegs, String name, int avgFlightAltitude, boolean migrates, double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(nrLegs);
		setName(name);
		setMigrates(migrates);
		setAvgFlightAltitude(avgFlightAltitude);

	}
}
