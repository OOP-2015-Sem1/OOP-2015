package javasmmr.zoosome.models.animals;

public class Shark extends Aquatic {

	public Shark(WaterType waterType, int nrOfLegs, String name) {
		this.nrOfLegs = nrOfLegs;
		this.name = name;
		setTypeOfWater(waterType);
	}

	public Shark() {
		this.setTypeOfWater(WaterType.saltWater);
		this.setNrOfLegs(0);
		this.name = "Sharky";
	}
}
