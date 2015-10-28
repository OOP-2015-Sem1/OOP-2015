package javasmmr.zoowsome.models.animals;

public abstract class Bird extends Animal {

	private boolean migrates;
	private Integer avgFlightAltitude;

	public Bird(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
	}

	public void setMigrates(boolean migrates) {
		this.migrates = migrates;
	}

	public void setAvgFlightAltitude(Integer avg) {
		this.avgFlightAltitude = avg;
	}

	public boolean getMigrates() {
		return migrates;
	}

	public Integer getAvgFlightAltitude() {
		return avgFlightAltitude;
	}
}
