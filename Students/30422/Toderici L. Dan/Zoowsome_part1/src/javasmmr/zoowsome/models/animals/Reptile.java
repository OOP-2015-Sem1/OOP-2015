package javasmmr.zoowsome.models.animals;

public class Reptile extends Animal 
{
	private boolean laysEggs;
	
	public void setLaysEggsStatus (boolean status) 
	{
		this.laysEggs = status;
	}
	public boolean getMigratesStatus()
	{
		return laysEggs;
	}
	
}
