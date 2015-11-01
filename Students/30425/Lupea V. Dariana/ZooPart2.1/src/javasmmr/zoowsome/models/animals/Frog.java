package javasmmr.zoowsome.models.animals;

public class Frog extends Aquatic {

	public Frog(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setName("Froggg");
		setNrOfLegs(4);
		setAvgSwimDepth(50);
		setWater(waterType.lake);
	}

	public Frog(String name, int nrOfLegs, int AvgDepth, waterType water, double maintCost, double dangerP) {
		super(nrOfLegs, name, AvgDepth, water, maintCost, dangerP);
	}

}
