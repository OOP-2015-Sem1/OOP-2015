package javasmmr.zoosome.models.animals;

public class Whale extends Aquatic {
	public Whale(WaterType waterType, int nrOfLegs, String name, int avgSwimDepth) {
		this.nrOfLegs = nrOfLegs;
		this.name = name;
		this.avgSwimDepth = avgSwimDepth;
		setTypeOfWater(waterType);
	}

	public Whale() {
		this.setTypeOfWater(WaterType.saltWater);
		this.name = "Ionut";
		this.avgSwimDepth = 30;
		this.nrOfLegs = 0;
	}
}
