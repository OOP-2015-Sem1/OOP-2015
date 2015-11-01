package javasmmr.zoosome.models.animals;

public abstract class Insect extends Animal{
	public boolean canFly;
	public boolean isDangerous;
	
	public abstract void setCanFly(boolean canFly);
	public abstract void setIsDangerous(boolean isDangerous);
	public abstract boolean canItFly();
	public abstract boolean isItDangerous();
}
