package javasmmr.zoowsome.models.animals;

public class Bird extends Animal
{
	private boolean migrates;
	private int avgFlightAltitude;
	
	public void setMigratesStatus (boolean status) {
		this.migrates = status;
	}
	public boolean getMigratesStatus(){
		return migrates;
	}
	
	public void setAvgFlightAltitude (int avg) {
		this.avgFlightAltitude = avg;
	}
	public int getAvgFlightAltitude(){
		return avgFlightAltitude;
	}
}
