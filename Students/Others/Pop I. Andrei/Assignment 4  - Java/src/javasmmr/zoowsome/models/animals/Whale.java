package javasmmr.zoowsome.models.animals;

public class Whale extends Aquatic {

	public Whale(int nrLegs, String name, int depth, WaterEnum water, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setAvgSwimDepth(depth);
		setWaterType(water);
	}

	public Whale() {
		this(0, "Whale", 200, WaterEnum.saltWater, 0.1, 0);
	}

}
