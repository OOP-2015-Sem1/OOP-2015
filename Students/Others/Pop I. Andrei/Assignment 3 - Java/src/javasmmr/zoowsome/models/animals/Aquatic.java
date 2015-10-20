package javasmmr.zoowsome.models.animals;

public abstract class Aquatic extends Animal {
	
	private int avgSwimDepth;
	private WaterEnum waterType;
	
	public int getAvgSwimDepth() {
		return avgSwimDepth;
	}
	public void setAvgSwimDepth(int avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	}
	public WaterEnum getWaterType() {
		return waterType;
	}
	public void setWaterType(WaterEnum waterType) {
		this.waterType = waterType;
	} 
}
