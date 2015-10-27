package javasmmr.zoowsome.models.animals;

public class Piranha extends Aquatic {

	public Piranha(int nrLegs, String name, int depth, WaterEnum water) {
		setNrOfLegs(nrLegs);
		setName(name);
		setAvgSwimDepth(depth);
		setWaterType(water);
	}

	public Piranha() {
		this(0, "Piranha", 40, WaterEnum.freshWater);
	}

}
