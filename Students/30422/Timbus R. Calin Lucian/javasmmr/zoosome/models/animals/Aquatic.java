package javasmmr.zoosome.models.animals;

public abstract class Aquatic extends Animal{
	public int avgSwimDepth;
	public WaterType typeOfWater;
	
	public WaterType getTypeOfWater() {
		return typeOfWater;
	}
	public void setTypeOfWater(WaterType typeOfWater) {
		this.typeOfWater = typeOfWater;
	}
	

}
