package javasmmr.zoosome.models.animals;

public abstract class Bird extends Animal {
	public int avgFlightAltitude;
	public boolean migrates;
	
	public abstract void setAvgFlightAltitude(int avgFlightAltitude);
	public abstract void setMigrates(boolean migrates);
	public abstract int getAvgFlightAltitude();
	public abstract boolean getMigrates();
}
