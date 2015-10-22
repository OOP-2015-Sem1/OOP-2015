package models.animals;

public abstract class Aquatic extends Animal {

	private int avgSwimDepth;
	private waterType waterType;
	
	
	
	public int getAvgSwimDepth() {
		return avgSwimDepth;
	}
	public void setAvgSwimDepth(int avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	}
	public waterType getWaterType() {
		return waterType;
	}
	public void setWaterType(waterType waterType) {
		this.waterType = waterType;
	}
}
