package javasmmr.zoowsome.models.animals;

abstract public class Aquatic extends Animal {

	private int avgSwimDepth;
	private waterType water;

	public enum waterType {
		river, ocean, sea, lake
	}

	public int getAvgSwimDepth() {
		return avgSwimDepth;
	}

	public void setAvgSwimDepth(int avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	}

	public waterType getWater() {
		return water;
	}

	public void setWater(waterType water) {
		this.water = water;
	}

	public Aquatic(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(nrOfLegs);
		setName(name);
		setAvgSwimDepth(avgSwimDepth);
		setWater(water);
	}

	public Aquatic(int nrOfLegs, String name, int swimDepth, waterType type, double maintCost, double dangerP) {
		super(maintCost, dangerP); // super must be called first
		setNrOfLegs(nrOfLegs);
		setName(name);
		setAvgSwimDepth(avgSwimDepth);
		setWater(water);

	}
}
