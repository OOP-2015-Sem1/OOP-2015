package javasmmr.zoowsome.models.animals;

public class Dolphin extends Aquatic
{
	 public Dolphin()
	 {
		 this.setName("Tim");
		 this.setNrOfLegs(0);
		 this.setAvgSwimDepth(150);
		 this.setTypeOfWater(TypesOfWater.saltWater);
	 }
	 
	 public Dolphin(String name, int nrOfLegs, int avgSwimDepth,TypesOfWater waterType)
	 {
		 this.setName(name);
		 this.setNrOfLegs(nrOfLegs);
		 this.setAvgSwimDepth(avgSwimDepth);
		 this.setTypeOfWater(waterType);
	 }
	
}
