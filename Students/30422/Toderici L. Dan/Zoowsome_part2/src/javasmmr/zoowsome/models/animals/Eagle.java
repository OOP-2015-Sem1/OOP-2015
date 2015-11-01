package javasmmr.zoowsome.models.animals;

public class Eagle extends Bird 
{
	
	public Eagle()
	{
		super(3.5,0.4);
		this.setName("Hunter");
		this.setNrOfLegs(2);
		this.setMigratesStatus(false);
		this.setAvgFlightAltitude(150);
	}
	
	public Eagle(String name, int nrOfLegs, boolean migrationStatus, int avgFlightAltitude)
	{
		super(3.5,0.4);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setMigratesStatus(migrationStatus);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}
	
}
