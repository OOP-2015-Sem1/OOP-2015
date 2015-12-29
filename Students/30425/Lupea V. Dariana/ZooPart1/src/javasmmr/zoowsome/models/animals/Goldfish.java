package javasmmr.zoowsome.models.animals;

public class Goldfish extends Aquatic {

	public Goldfish() {
		setName("Goldfish");
		setNrOfLegs(0);
		setAvgSwimDepth(1000);
		setWater(waterType.ocean);
	}

	public Goldfish(String name, int nrOfLegs, int AvgDepth, waterType water) {
		super(nrOfLegs, name, AvgDepth, water);
	}

}
