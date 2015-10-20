package javasmmr.zoowsome.models.animals;

public class Frog extends Aquatic {

	public Frog() {
		setName("Froggg");
		setNrOfLegs(4);
		setAvgSwimDepth(50);
		setWater(waterType.lake);
	}

	public Frog(String name, int nrOfLegs, int AvgDepth, waterType water) {
		super(nrOfLegs, name, AvgDepth, water);
	}

}
