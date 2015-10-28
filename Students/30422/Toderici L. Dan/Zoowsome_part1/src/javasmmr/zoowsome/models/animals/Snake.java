package javasmmr.zoowsome.models.animals;

public class Snake extends Reptile
{
	public Snake()
	{
		this.setName("Marcel");
		this.setNrOfLegs(0);
		this.setLaysEggsStatus(true);
	}
	
	public Snake(String name, int nrOfLegs, boolean laysEggsStatus)
	{
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setLaysEggsStatus(laysEggsStatus);
	}
 
}
