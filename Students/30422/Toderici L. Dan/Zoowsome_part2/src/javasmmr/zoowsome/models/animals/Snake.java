package javasmmr.zoowsome.models.animals;

public class Snake extends Reptile
{
	public Snake()
	{
		super (3.0,0.3);
		this.setName("Marcel");
		this.setNrOfLegs(0);
		this.setLaysEggsStatus(true);
	}
	
	public Snake(String name, int nrOfLegs, boolean laysEggsStatus)
	{
		super (3.0,0.3);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setLaysEggsStatus(laysEggsStatus);
	}
 
}
