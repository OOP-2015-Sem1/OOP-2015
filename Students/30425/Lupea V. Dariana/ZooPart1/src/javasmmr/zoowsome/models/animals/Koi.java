package javasmmr.zoowsome.models.animals;

public class Koi extends Aquatic {

	public Koi() {
		setName("KoiFish");
		setNrOfLegs(0);
		setAvgSwimDepth(500);
		setWater(waterType.sea);
	}

	public Koi(String name, int nrOfLegs, int AvgDepth, waterType water) {
		super(nrOfLegs, name, AvgDepth, water);
	}

}
