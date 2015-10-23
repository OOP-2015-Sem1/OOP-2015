package javasmmr.zoosome.models.animals;

abstract public class Bird extends Animal {
	private boolean migrates;
	private int avgFlightAltitude;

	public Bird(int nrOfLegs, String name, boolean migrates, int avgFlightAltitude) {
		super(nrOfLegs, name);
		this.setMigrates(migrates);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}

	public boolean isMigrating() {
		return this.migrates;
	}

	public int getAvgFlightAltitude() {
		return this.avgFlightAltitude;
	}

	public void setMigrates(boolean migrates) {
		this.migrates = migrates;
	}

	public void setAvgFlightAltitude(int avgFlightAltitude) {
		this.avgFlightAltitude = avgFlightAltitude;
	}
}
