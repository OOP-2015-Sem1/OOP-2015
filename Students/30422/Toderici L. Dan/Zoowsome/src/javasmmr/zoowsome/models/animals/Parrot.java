package javasmmr.zoowsome.models.animals;

public class Parrot extends Bird 
{
	public Parrot()
	{
		this.setName("Coco");
		this.setNrOfLegs(2);
		this.setAvgFlightAltitude(20);
		this.setMigratesStatus(false);
	}
	
	public Parrot(String name, int nrOfLegs, float avgFlightAltitude , boolean migrationStatus)
	{
		this.setName(name);
		this.setNrOfLegs(2);
		this.setAvgFlightAltitude(20);
		this.setMigratesStatus(false);
	}
}
