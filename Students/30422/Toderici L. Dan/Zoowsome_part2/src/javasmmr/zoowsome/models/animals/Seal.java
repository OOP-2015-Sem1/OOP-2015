package javasmmr.zoowsome.models.animals;

public class Seal extends Aquatic {
	public Seal()
	{
		super(6.5,0.25);
		this.setName("Marie");
		this.setNrOfLegs(2);
		this.setAvgSwimDepth(100);
		this.setTypeOfWater(TypesOfWater.freshWater);
	}
	public Seal(String name, int nrOfLegs, int avgSwimDepth,TypesOfWater waterType)
	{
		super(6.5,0.25);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setAvgSwimDepth(avgSwimDepth);
		this.setTypeOfWater(waterType);
	}
}
