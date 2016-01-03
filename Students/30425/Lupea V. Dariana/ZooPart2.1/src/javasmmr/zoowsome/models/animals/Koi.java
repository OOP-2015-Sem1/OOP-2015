package javasmmr.zoowsome.models.animals;

public class Koi extends Aquatic {

	public Koi(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setName("KoiFish");
		setNrOfLegs(0);
		setAvgSwimDepth(500);
		setWater(waterType.sea);
	}

	public Koi(String name, int nrOfLegs, int AvgDepth, waterType water, double maintCost, double dangerP) {
		super(nrOfLegs, name, AvgDepth, water, maintCost, dangerP);
	}

}
