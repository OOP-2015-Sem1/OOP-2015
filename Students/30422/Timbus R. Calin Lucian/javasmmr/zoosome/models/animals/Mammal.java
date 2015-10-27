package javasmmr.zoosome.models.animals;

public abstract class Mammal extends Animal {
	public float normalBodyTemp;
	public float percHairBody;
	
	public abstract void setNormalBodyTemperature(float normalBodyTemp);
	public abstract float getPercHairBody();
	public abstract void setPercHairdBody(float percHairBody);
	public abstract float getNormalBodyTemp();

}
