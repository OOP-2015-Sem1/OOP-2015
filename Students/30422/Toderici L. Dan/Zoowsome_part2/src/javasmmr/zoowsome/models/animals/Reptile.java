package javasmmr.zoowsome.models.animals;

public class Reptile extends Animal 
{
	private boolean laysEggs;
	
	public Reptile(double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
	}
	
	public void setLaysEggsStatus (boolean status) 
	{
		this.laysEggs = status;
	}
	public boolean getMigratesStatus()
	{
		return laysEggs;
	}
	
}
