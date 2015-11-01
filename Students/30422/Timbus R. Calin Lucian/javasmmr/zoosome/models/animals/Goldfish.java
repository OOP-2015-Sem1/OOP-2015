package javasmmr.zoosome.models.animals;

public class Goldfish extends Aquatic {

	public Goldfish(WaterType waterType, int nrOfLegs, String name, int avgSwimDepth) {
		this.nrOfLegs = nrOfLegs;
		this.name = name;
		this.avgSwimDepth = avgSwimDepth;
		setTypeOfWater(waterType);
	}

	public Goldfish() {
		this.setTypeOfWater(WaterType.saltWater);
		this.name = "Goldy";
		this.avgSwimDepth = 20;
		this.nrOfLegs = 0;
	}
}
