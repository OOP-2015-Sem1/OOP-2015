package javasmmr.zoowsome.models.animals;

public class Turtule extends Reptile 
{
	public Turtule()
	{
		this.setName("Magda");
	    this.setNrOfLegs(4);
	    this.setLaysEggsStatus(true);
	}
	public Turtule(String name, int nrOfLegs, boolean laysEggsStatus)
	{
		this.setName(name);
	    this.setNrOfLegs(nrOfLegs);
	    this.setLaysEggsStatus(laysEggsStatus);
	}
}
