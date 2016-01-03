package javasmmr.zoowsome.models.animals;

public class Goldfish extends Aquatic {

	public Goldfish(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setName("Goldfish");
		setNrOfLegs(0);
		setAvgSwimDepth(1000);
		setWater(waterType.ocean);
	}

	public Goldfish(String name, int nrOfLegs, int AvgDepth, waterType water, double maintCost, double dangerP) {
		super(nrOfLegs, name, AvgDepth, water, maintCost, dangerP);
	}

}
