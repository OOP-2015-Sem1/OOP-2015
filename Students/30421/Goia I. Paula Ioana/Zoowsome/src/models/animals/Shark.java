package models.animals;

public class Shark extends Aquatic {
	
	public Shark(){
		this.setName("Shark");
		this.setNrOfLegs(0);
		this.setAvgSwimDepth(1200);
		this.setWaterType(waterType.saltWater);
	}

}
