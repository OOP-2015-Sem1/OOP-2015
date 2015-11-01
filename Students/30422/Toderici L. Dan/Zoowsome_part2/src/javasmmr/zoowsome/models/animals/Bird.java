package javasmmr.zoowsome.models.animals;

public class Bird extends Animal
{
	private boolean migrates;
	private int avgFlightAltitude;
	
	public Bird(double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
	}
	
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
