package javasmmr.zoowsome.models.animals;

public class Lizard extends Reptile
{
	
	public Lizard()
	{
		super(4.5,0.35);
		this.setName("Timo");
		this.setNrOfLegs(4);
		this.setLaysEggsStatus(true);
	}
	
	public Lizard(String name, int nrOfLegs, boolean laysEggs)
	{
		super(4.5,0.35);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setLaysEggsStatus(laysEggs);
	}

}
