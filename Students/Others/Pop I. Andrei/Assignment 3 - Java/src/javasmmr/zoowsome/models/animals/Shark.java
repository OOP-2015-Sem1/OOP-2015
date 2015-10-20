package javasmmr.zoowsome.models.animals;

public class Shark extends Aquatic{
	
	public Shark(int nrLegs, String name, int depth, WaterEnum water) {
		setNrOfLegs(nrLegs);
		setName(name);
		setAvgSwimDepth(depth);
		setWaterType(water);
	}
	
	public Shark() {
		this(0, "Shark", 2000, WaterEnum.saltWater);
	}

}
