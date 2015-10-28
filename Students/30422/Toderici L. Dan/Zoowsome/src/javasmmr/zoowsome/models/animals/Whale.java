package javasmmr.zoowsome.models.animals;

public class Whale extends Aquatic 
{
	public Whale()
	{
		this.setName("BigBubba");
		this.setNrOfLegs(0);
		this.setAvgSwimDepth(250);
		this.setTypeOfWater(TypesOfWater.freshWater);
	}
	public Whale(String name, int nrOfLegs, int avgSwimDepth, TypesOfWater waterType)
	{
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setAvgSwimDepth(avgSwimDepth);
		this.setTypeOfWater(waterType);
	}


}
