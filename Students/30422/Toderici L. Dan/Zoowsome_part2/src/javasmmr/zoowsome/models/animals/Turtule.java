package javasmmr.zoowsome.models.animals;

public class Turtule extends Reptile 
{
	public Turtule()
	{
		super(2.0,0.1);
		this.setName("Magda");
	    this.setNrOfLegs(4);
	    this.setLaysEggsStatus(true);
	}
	public Turtule(String name, int nrOfLegs, boolean laysEggsStatus)
	{
		super(2.0,0.1);
		this.setName(name);
	    this.setNrOfLegs(nrOfLegs);
	    this.setLaysEggsStatus(laysEggsStatus);
	}
}
