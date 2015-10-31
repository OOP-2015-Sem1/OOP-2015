package javasmmr.zoowsome.models.animals;

public class Whale extends Aquatic 
{
	public Whale()
	{
		super(7.0,0.5);
		this.setName("BigBubba");
		this.setNrOfLegs(0);
		this.setAvgSwimDepth(250);
		this.setTypeOfWater(TypesOfWater.freshWater);
	}
	public Whale(String name, int nrOfLegs, int avgSwimDepth, TypesOfWater waterType)
	{
		super(7.0,0.5);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setAvgSwimDepth(avgSwimDepth);
		this.setTypeOfWater(waterType);
	}


}
