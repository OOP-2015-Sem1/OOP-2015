package javasmmr.zoowsome.models.animals;

public class Woodpecker extends Bird 
{
	public Woodpecker()
	{
		this.setName("Steve");
		this.setNrOfLegs(2);
		this.setMigratesStatus(true);
		this.setAvgFlightAltitude(70);
	}
	
	public Woodpecker(String name, int nrOfLegs, boolean migrationStatus, int avgFlightAltitude)
	{
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setMigratesStatus(migrationStatus);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}
}
