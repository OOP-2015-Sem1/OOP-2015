package javasmmr.zoowsome.models.animals;

public class Dolphin extends Aquatic
{
	 public Dolphin()
	 {
		 super(2.5,0.1);
		 this.setName("Tim");
		 this.setNrOfLegs(0);
		 this.setAvgSwimDepth(150);
		 this.setTypeOfWater(TypesOfWater.saltWater);
	 }
	 
	 public Dolphin(String name, int nrOfLegs, int avgSwimDepth,TypesOfWater waterType)
	 {
		 super(2.5,0.1);
		 this.setName(name);
		 this.setNrOfLegs(nrOfLegs);
		 this.setAvgSwimDepth(avgSwimDepth);
		 this.setTypeOfWater(waterType);
	 }
	
}
