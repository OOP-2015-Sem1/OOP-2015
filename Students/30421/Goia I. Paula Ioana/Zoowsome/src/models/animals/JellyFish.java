package models.animals;

public class JellyFish extends Aquatic{
	
	public JellyFish(){
		this.setName("JellyFish");
		this.setNrOfLegs(0);
		this.setAvgSwimDepth(200);
		this.setWaterType(waterType.saltWater);
	}

}
