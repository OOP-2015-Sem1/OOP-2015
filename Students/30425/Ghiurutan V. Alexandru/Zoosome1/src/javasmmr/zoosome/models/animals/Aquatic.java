package javasmmr.zoosome.models.animals;

abstract public class Aquatic extends Animal {
	private int avgSwimDepth;
	private WaterType waterType;

	public Aquatic(int nrOfLegs, String name, int avgSwimDepth, WaterType waterType) {
		super(nrOfLegs, name);
		this.setAvgSwimDepth(avgSwimDepth);
		this.setWaterType(waterType);
	}

	public int getAvgSwimDepth() {
		return this.avgSwimDepth;
	}

	public WaterType getWaterType() {
		return this.waterType;
	}

	public void setWaterType(WaterType waterType) {
		this.waterType = waterType;
	}

	public void setAvgSwimDepth(int avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	}
}
