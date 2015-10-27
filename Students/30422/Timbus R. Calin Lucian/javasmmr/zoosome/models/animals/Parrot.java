package javasmmr.zoosome.models.animals;

public class Parrot extends Bird {
	public Parrot(String name, int avgFlightAltitude, boolean migrates, int nrOfLegs) {
		this.name = name;
		this.avgFlightAltitude = avgFlightAltitude;
		this.migrates = migrates;
		this.nrOfLegs = nrOfLegs;
	}

	public Parrot() {
		this("Parry", 10, false, 2);
	}

	@Override
	public void setAvgFlightAltitude(int avgFlightAltitude) {
		this.avgFlightAltitude = avgFlightAltitude;
	}

	@Override
	public void setMigrates(boolean migrates) {
		this.migrates = migrates;
	}

	@Override
	public int getAvgFlightAltitude() {
		return avgFlightAltitude;
	}

	@Override
	public boolean getMigrates() {
		return migrates;
	}

}
