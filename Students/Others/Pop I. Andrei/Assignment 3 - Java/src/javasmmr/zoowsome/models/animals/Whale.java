package javasmmr.zoowsome.models.animals;

public class Whale extends Aquatic{
	
	public Whale(int nrLegs, String name, int depth, WaterEnum water) {
		setNrOfLegs(nrLegs);
		setName(name);
		setAvgSwimDepth(depth);
		setWaterType(water);
	}
	
	public Whale() {
		this(0, "Whale", 200, WaterEnum.saltWater);
	}

}
