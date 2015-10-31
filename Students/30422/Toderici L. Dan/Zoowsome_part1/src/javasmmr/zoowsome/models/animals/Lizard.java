package javasmmr.zoowsome.models.animals;

public class Lizard extends Reptile
{
	
	public Lizard()
	{
		this.setName("Timo");
		this.setNrOfLegs(4);
		this.setLaysEggsStatus(true);
	}
	
	public Lizard(String name, int nrOfLegs, boolean laysEggs)
	{
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setLaysEggsStatus(laysEggs);
	}

}
